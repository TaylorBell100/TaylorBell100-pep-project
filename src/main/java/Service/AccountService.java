package Service;

import java.util.List;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
/*
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }
*/
    // 1
    public Account addAccount(Account account) {
        if (account.getUsername().isEmpty()){
            return null;
        } else if (accountDAO.getAccountByUsername(account.getUsername())!=null){
            return null;
        } else if (account.getPassword().length() <4){
            return null;
        }
        return accountDAO.insertAccount(account);
    }

    public Account loginAccount(Account account){
        Account temp = accountDAO.getAccountByUsername(account.getUsername());
        if (temp==null){
            return null;
        }
        if (temp.getPassword().equals(account.getPassword())){
            return temp;
        }
        return null;
    }


}
