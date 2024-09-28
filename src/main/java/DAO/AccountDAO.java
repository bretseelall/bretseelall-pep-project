package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;


public class AccountDAO {
    
    public Account insertNewAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        
        try{
            String sql = "INSERT into Account(username, password) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, account.username);
            ps.setString(2, account.password);

            ps.executeUpdate();

            ResultSet pkeyResultSet = ps.getGeneratedKeys();

            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account getAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM Account WHERE username=? AND password=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, account.username);
            ps.setString(2, account.password);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account account2 = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                return account2;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
