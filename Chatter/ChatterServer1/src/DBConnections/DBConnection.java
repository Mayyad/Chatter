/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBConnections;
import java.sql.*;

/**
 *
 * @author ahmedsobhy
 */
public class DBConnection extends Thread{
    
    public Connection connection;
    String URL="jdbc:mysql://localhost:3306/Chatter";
    String user="magdy";
    String pass="iti";
    
    public DBConnection(){
        try{
           connection =DriverManager.getConnection(URL, user, pass);
           start();
        }catch(Exception e){

        }
    }
    
    @Override
    public void run(){
        
    }
    
    
    
}
