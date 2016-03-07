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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import serverOperation.ServerOperation;

/**
 *
 * @author ahmedsobhy
 */
public class ServerSocketHandler extends Thread{

    public ServerSocket ss;
    DBConnections.DBConnection dbConn;
    //serverOperation.ServerOperation operation ;
     int global_id ;
    serverOperation.ServerOperation operation;

    serverOperation.ServerOperation operationX;
    ArrayList<String> emails;
    public static ArrayList<ServerSocketStream> clients=new ArrayList<ServerSocketStream>();
    
    public ServerSocketHandler(int port) {
        try {
            ss = new ServerSocket(port);
            start();
            emails = new ArrayList<String>();  
        } catch (IOException ex) {
        }
             
    }
    public void run(){
         while (true) {
            try {                
                Socket s = ss.accept();
                new ServerSocketStream(s);
            } catch (IOException ex) {

            }
        }    
        }
    
    public class ServerSocketStream extends Thread {

        public DataInputStream dis;
        public PrintStream ps;

        public ServerSocketStream(Socket s) {
            try {
                dis = new DataInputStream(s.getInputStream());
                ps = new PrintStream(s.getOutputStream());
                
            } catch (IOException ex) {

            }
            clients.add(this);
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
                        String[] parts = str.split("\\$");
                        String msg=parts[0];
                        String from=parts[1];
                        String to=parts[2];
                        System.out.println("to"+to);
                        
                        int i;
                        for(i=0;i<emails.size();i++){
                            System.out.println("emails at i"+emails.get(i));
                            if(to.equals(emails.get(i))){
                                System.out.println("equalZzzZ");
                                clients.get(i+1).ps.println(str);
                            }
                        }
                        //get(i).ps.println();
//                        for(ServerSocketStream ss:clients){
//                            ss.ps.println(msg);
//                     amagdy529   }
                        
                        //from-to id's and message to save it at database....
                        operation.sendMessage(from,to,msg);
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
                            
                            global_id = myid ;
                            emails.add(email);
                            
                            operationX = new ServerOperation();
                            String myName = operationX.returnName(myid);
                            String contactList = operationX.contactList(myid);
                            String friendsEmail = operationX.friendsEmail(myid);
                            if (contactList == null ){
                                contactList = " ";
                                friendsEmail = " ";
                            }
                            String groupList = operationX.groupList(myid);
                            if ( groupList == null )
                            {
                                groupList = " ";
                            }
                            
                            
                            System.out.println(myid);
                            System.out.println(groupList);
                            System.out.println(contactList);
                            operationX.setOnline(myid);
                            ps.println("1"+"$"+myName+"$"+contactList+"$"+groupList+"$"+friendsEmail);
                            
                            
                           // handler.ps.println("3"+"$"+email+"$"+password);
                        }else{
                            System.out.println("Not Found");
                            ps.println("0");
                        }

                        //System.out.println(str);
                    }else if (ch == '4'){
                     
                        System.out.println("add friend");

                        String[] parts = str.split("\\$");

                        String id = parts[0];
                        String friendMail = parts[1];
                        
                        System.out.println(id);
                        System.out.println(friendMail);
                        
                       if (operationX.returnId(friendMail) == 0){
                           
                           System.out.println("msh mwgood fel db"); //ab3t lel client en mafesh mail fe db esmo kda 
                           ps.println("4");
                       }
                       else {
                              int frnd_id = operationX.returnId(friendMail);
                              String frnd_name ="";
                          
                          if ((frnd_name = operationX.addFriend(global_id, frnd_id)).equals("0")) {
                               System.out.println("Already your friend"); // ab3t lel client en el frnd dh 3ndy asln 
                               ps.println("3");
                           }
                           else if (operationX.addFriend(global_id, frnd_id).equals("3"))
                           {
                               System.out.println("msh mwgood fel db"); //ab3t lel client en mafesh mail fe db esmo kda
                               ps.println("4");
                           }
                            else
                           {
                               System.out.println("done"); // done hyb3t rakam 2
                               ps.println("2$"+frnd_name);
                           }
                       }
                       
                    }else if (ch == '5')   
                    {
                        
                        System.out.println("create group");
                     
                        String[] parts = str.split("\\$");
//                      
                        String groupname = parts[1];
                        String frndsString = parts[2];
                        
                        String[] frnds = frndsString.split("\\&");
                        
                        int frndNo = frnds.length;
                        
                        operationX.createGroup(groupname, global_id);
                        
                        int group_id = operationX.returnGroupId(groupname);
                        operationX.assignFriendToGroup(global_id, group_id);
                        for (int x =0 ;x<frndNo ; x++)
                        {
                            int frnd_id = operationX.returnId(frnds[x]);
                            
                            if (operationX.checkFriend(frnd_id))
                            {
                                operationX.assignFriendToGroup(frnd_id,group_id );
                            }
                            
                            else {
                                System.out.println("Not found");
                                ps.println("5");
                            }
                        }
                        
                        
                        
                        
                        
                        //System.out.println(y);
                        
                    }else {
                        System.out.println("nothing");
                    }

                    //handle the operations here 
                    //login - register - send message - send files
                } catch (IOException ex) {
                    System.out.println("Client out");
                } catch (SQLException ex) {
                    
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
