package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.*;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){    // No-args constructor
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){   // Constructor for a DAO provided
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account){
        if((account.password.length() < 4) || account.username.isBlank()){
            return null;
        }
        return accountDAO.insertNewAccount(account);
    }
    
    public Account getAccount(Account account){
        return accountDAO.getAccount(account);
    }
}
