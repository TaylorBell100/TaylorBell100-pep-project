package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    
    public MessageDAO messageDAO;


    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message deleteMessageByID(int message_id) {

        return messageDAO.deleteMessageByID(message_id);
    }

    public Message getMessageByID(int message_id) {

        return messageDAO.getMessageByID(message_id);
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message addMessage(Message message) {
        
        AccountDAO accountDAO = new AccountDAO();
        if(accountDAO.getAccountByID(message.getPosted_by()) == null)
            return null;

        if(message.getMessage_text().equals(""))
            return null;

        if (message.getMessage_text().length() > 255)
            return null;

        return messageDAO.insertMessage(message);
    }

    public Message updateMessageByID(int message_id, String message) {
        
        Message temp = messageDAO.getMessageByID(message_id);
        if(temp == null)
            return null;

        if(message.equals(""))
            return null;

        if (message.length() > 255)
            return null;

        messageDAO.updateMessage(message_id, message, temp);
        temp.setMessage_text(message);
        return temp;
    }

    public List<Message> getAllMessagesFromUser(int account_id) {

        return messageDAO.getMessagesFromUser(account_id);
    }
}

