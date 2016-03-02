/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSocketHandler;

import DBConnections.DBConnection;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmedsobhy
 */
public class ServerSocketHandler {
    
    ServerSocket ss;
    DBConnections.DBConnection dbConn;
    
    public ServerSocketHandler(int port){
        try {
            ss=new ServerSocket(port);
            
        } catch (IOException ex) {
        }
        
        while(true){
            try {
                Socket s=ss.accept();
                new ServerSocketStream(s);
           }catch (IOException ex) {
                
            }
        }
    }
    
    public class ServerSocketStream extends Thread{
        DataInputStream dis ; 
        PrintStream ps ; 
        public ServerSocketStream(Socket s){
            try {
                dis = new DataInputStream(s.getInputStream());
                ps  = new PrintStream(s.getOutputStream());

            } catch (IOException ex) {
    
            }

            start();

        }

        @Override
        public void run(){
            while(true){
                try {
                    String str=dis.readLine();
                    char ch=str.charAt(0);
                    System.out.println(ch);
                    
                    if(ch=='1'){
                        System.out.println("register");                        
                        //put it into register function .. it's working now :) 
                        dbConn = new DBConnections.DBConnection();
                        Statement stm = dbConn.connection.createStatement();                        
                     
                        System.out.println(str);
                        str=str.replaceFirst("1", "");
                        System.out.println(str);
                        String query = "INSERT INTO users( name, password, email,gender,age) VALUES("+str+",12"+")";
                        
                        stm.execute(query);
                        System.out.println("done");
                        
                        System.out.println(str);    
                    }
                    else if(ch=='2'){
                        System.out.println("message");
                        
                        System.out.println(str); 
                            
                    }
                    else if(ch=='3'){
                        
                        System.out.println("login");
                        
                        String[] parts = str.split("\\$");
                        
                        String id   = parts[0];
                        String email= parts[1];
                        String pass = parts[2];
                        System.out.println(id);
                        System.out.println(email);
                        System.out.println(pass);
                        
                        
                        dbConn = new DBConnections.DBConnection();
                        Statement stmt = dbConn.connection.createStatement();
                        String query  = "select * from users where email= ? and password = ? ";
      
                        PreparedStatement  pstmt = dbConn.connection.prepareStatement(query);
                        pstmt.setString(1, email);
                        pstmt.setString(2, pass);
                        ResultSet rs=pstmt.executeQuery();
                        
                        if(rs.next()){
                            System.out.println("user exists");
                            System.out.println(" \n congratulations !");
                            ps.println("1");
                        }else{
                            System.out.println("Not Found");
                            ps.println("0");
                        }
                        
                        
                        
                        
                                              
                        //System.out.println(str);
                    }
                    else{
                        System.out.println("nothing");
                    }
                                      
                    
                    //handle the operations here 
                    //login - register - send message - send files
                    
                    
                } catch (IOException ex) {
                        ex.printStackTrace();
                } catch (SQLException ex) {
                        ex.printStackTrace();
                }
            }
        }

        @Override
        public void destroy(){
            try {
                dis.close();
                ps.close();
            } catch (IOException ex) {

            }
        }
    }
    
}