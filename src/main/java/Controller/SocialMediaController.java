package Controller;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService; 
    MessageService messageService;
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postInsertAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postAddMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIDHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);


        return app;
    }

    private void getAllMessagesFromUserHandler(Context ctx) {
        List<Message> temp = messageService.getAllMessagesFromUser(Integer.parseInt(ctx.pathParam("account_id")));
        if (temp == null){
            ctx.status(200);
        } else{
            ctx.json(temp);
        }
    }

    private void updateMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> json = ctx.bodyAsClass(Map.class);
        String updateText = (String) json.get("message_text");
        Message temp = messageService.updateMessageByID(Integer.parseInt(ctx.pathParam("message_id")),updateText);
        if (temp == null){
            ctx.status(400);
        } else{
            ctx.json(temp);
        }
    }

    private void deleteMessageByIDHandler(Context ctx) {
        Message temp = messageService.getMessageByID(Integer.parseInt(ctx.pathParam("message_id")));
        if (temp == null){
            ctx.status(200);
        } else{
            ctx.json(temp);
            temp = messageService.deleteMessageByID(Integer.parseInt(ctx.pathParam("message_id")));
        }
    }

    private void getMessageByIDHandler(Context ctx) {
        Message temp = messageService.getMessageByID(Integer.parseInt(ctx.pathParam("message_id")));
        if (temp == null){
            ctx.status(200);
        } else{
            ctx.json(temp);
        }
    }

    private void getAllMessagesHandler(Context ctx){
        ctx.json(messageService.getAllMessages());
    }

    private void postAddMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedMessage));
        }
    }

    private void postInsertAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedAccount));
        }
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);
        if(loginAccount==null){
            ctx.status(401);
        }else{
            ctx.json(mapper.writeValueAsString(loginAccount));
        }
    }


}