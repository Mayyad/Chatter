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
import serverOperation.ServerOperation;

/**
 *
 * @author ahmedsobhy
 */
public class ServerSocketHandler {

    ServerSocket ss;
    DBConnections.DBConnection dbConn;
    //serverOperation.ServerOperation operation ;
    serverOperation.ServerOperation operation;

    serverOperation.ServerOperation operationX;

    public ServerSocketHandler(int port) {
        try {
            ss = new ServerSocket(port);

        } catch (IOException ex) {
        }

        while (true) {
            try {
                Socket s = ss.accept();
                new ServerSocketStream(s);
            } catch (IOException ex) {

            }
        }
    }

    public class ServerSocketStream extends Thread {

        DataInputStream dis;
        PrintStream ps;

        public ServerSocketStream(Socket s) {
            try {
                dis = new DataInputStream(s.getInputStream());
                ps = new PrintStream(s.getOutputStream());
                
            } catch (IOException ex) {

            }

            start();

        }

        @Override
        public void run() {
            while (true) {
                try {
                    
                    String str = dis.readLine();
                    char ch = str.charAt(0);
                    System.out.println(ch);
                    
                    operation = new ServerOperation();
                    if (ch == '1') {
                        str = str.replaceFirst("1", "");

                    
                        boolean isMailHere = operation.register(str);

                        //boolean isMailHere;
                        //isMailHere = operation.register(str);
                        System.out.println(isMailHere);
                        if (isMailHere) {
                            //done registration
                            System.out.println("true1");
                        } else {
                            //email here .. please enter another email
                            ps.println("f");
                        }
                    } else if (ch == '2') {
                        System.out.println("message");
                        str = str.replaceFirst("2", "");

                        //from-to id's and message
                        operation.sendMessage("1", "2", str);
                        System.out.println(str);

                    } else if (ch == '3') {

                        System.out.println("login");

                        String[] parts = str.split("\\$");

                        String id = parts[0];
                        String email = parts[1];
                        String pass = parts[2];
                        System.out.println(id);
                        System.out.println(email);
                        System.out.println(pass);

                   
                        dbConn = new DBConnections.DBConnection();
                        Statement stmt = dbConn.connection.createStatement();
                        String query = "select * from users where email= ? and password = ? ";

                        PreparedStatement pstmt = dbConn.connection.prepareStatement(query);
                        pstmt.setString(1, email);
                        pstmt.setString(2, pass);
                        ResultSet rs = pstmt.executeQuery();
                        //System.out.println(rs.getString(1)+"\t"+rs.getString(2));

                        if (rs.next()) {
                             /*
                            String updateQuery = "update users set status=1 where email = ? ";
                            PreparedStatement pstmtUpdate=dbConn.connection.prepareStatement(updateQuery);
                            pstmtUpdate.setString(1, email);
                            stmt.executeUpdate(updateQuery);
                            */
                            System.out.println("user exists");
                            System.out.println(" \n congratulations !");
                            int myid = rs.getInt("user_id");
                            
                            operationX = new ServerOperation();
                            String myName = operationX.returnName(myid);
                            String contactList = operationX.contactList(myid);
                            String groupList = operationX.groupList(myid);
                            
                            System.out.println(myid);
                            System.out.println(groupList);
                            //System.out.println(contactList);
                            ps.println("1"+"$"+myName+"$"+contactList+"$"+groupList);
                           // handler.ps.println("3"+"$"+email+"$"+password);
                        }else{
                            System.out.println("Not Found");
                            ps.println("0");
                        }

                        //System.out.println(str);
                    } else {
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
        public void destroy() {
            try {
                dis.close();
                ps.close();
                ss.close();
            } catch (IOException ex) {
                System.out.println("server down");
            }
        }
    }

}
