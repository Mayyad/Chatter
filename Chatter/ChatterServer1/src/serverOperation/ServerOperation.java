/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverOperation;
import DBConnections.DBConnection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ahmedsobhy
 */
public class ServerOperation {
    DBConnection dbConnection;
    Statement stm;
    
    public ServerOperation(){
        try {
            stm=dbConnection.connection.createStatement();
        } catch (SQLException ex) {
               
        }
    }
    
    public void login(){
        try {
            stm.execute("sql");
        } catch (SQLException ex) {

        }
    }
    
    //register insert data 
    public void register(){
        try {
            //check email validation then insert into database
            stm.execute("INSERT ");
        } catch (SQLException ex) {

        }
    }
    
    //validate email it's unique
    public void validateEmail(){
    
    }
    
    //send message from to 
    public void sendMessage(String from,String to,String msg){
        //insert into database 
        //send the message to the target
    }
    
    //list the all friends of the user
    public String friendList(){
        return "Friend list";
    }
    
    //contact list  --ayaad
   
}