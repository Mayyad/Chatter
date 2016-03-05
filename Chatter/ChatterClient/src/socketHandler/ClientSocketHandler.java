/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketHandler;

import JavaBeans.User;
import TabPaneHandler.CustomTabePane;
import chatterclient.*;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author ahmedsobhy
 */
public class ClientSocketHandler extends Thread {

    public Socket socket;
    public DataInputStream dis; //bystlm
    public PrintStream ps;   //byb3t
    //CustomTabePane tab;
    //MainPage mainPg;
    MainPage mainPageObj ;
    public ClientSocketHandler() {

        try {
            socket = new Socket("localhost", 12345);
            dis = new DataInputStream(socket.getInputStream());
            ps = new PrintStream(socket.getOutputStream());
            mainPageObj=new MainPage();
        } catch (IOException ex) {
            System.err.println("error");
        }
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg=dis.readLine();
                System.out.println(msg);
                char ch=msg.charAt(0);
                System.out.println("el char aheh\t"+ch);
                if(ch=='1'){
                     //user exists
                    
                    //MainPage mainPageObj = new MainPage();
                    
                     
                     String[] parts = msg.split("\\$");
                        
                        String id         = parts[0];
                        String loggedName = parts[1];
                        String contactList= parts[2];
                        String groupList  = parts[3];
                        String friendsEmail  = parts[4];
                        
                        if (contactList.equals(" "))
                        {
                            contactList = " ";
                            mainPageObj.setFriendListModel("");
                            friendsEmail = " ";
                        }
                        else {
                            String[] singleContactList = contactList.split("\\*");
                             int y = singleContactList.length;
                             for ( int i =0 ;  i < y ;  i ++){
                        
                                System.out.println(singleContactList[i]);
                                mainPageObj.setFriendListModel(singleContactList[i]);
                            }
             
                             String[] singleEmailContactList = friendsEmail.split("\\*");
                             int z = singleEmailContactList.length;
                             for ( int i =0 ;  i < z ;  i ++){
                                mainPageObj.emails.add(singleEmailContactList[i]);
                            }
                        }
                        
                        
                        if (groupList.equals(" "))
                        {
                            groupList = " ";
                            mainPageObj.setGroupListModel("");
                        }
                        
                        else {
                            String[] singleGroupList= groupList.split("\\*");

                            int z = singleGroupList.length;

                            for(int i=0;i<z;i++){
                                System.out.println(singleGroupList[i]);
                                mainPageObj.setGroupListModel(singleGroupList[i]);
                            }
                        }
                        
                        
                      
                        System.out.println(id);
                        System.out.println(loggedName);
                   //     System.out.println(s);
                   
                    
                    System.out.println("el kalam wsel");
                    JOptionPane.showMessageDialog(null, "email and password are coorect");


                    
                    mainPageObj.setVisible(true);
                    mainPageObj.setUserNamelbl(loggedName);
     
                    
                    
                    //loginObj.setVisible(false);
                    //userNameLbl.setText("User Name");
                    // mainPageObj.setUserNamelbl("xxx");
                } else if (ch == '0') {
                    //us//ab3t lel client en mafesh mail fe db esmo kdaer Not found
                    System.out.println("mesh mawgood");
                    JOptionPane.showMessageDialog(null, "Not found");
                    Login loginObj = new Login();
                    loginObj.setVisible(true);

                }else if(ch=='2'){
                   
                    System.out.println("add friend done");
                    
                    
                }else if(ch == '3'){ 
                   
                    System.out.println("friend already 3andk aslan "); 
                    JOptionPane.showMessageDialog(null, "this contact is already your friend :)");
                }else if(ch == '4'){
                    
                    System.out.println("doesnt exist in database aslan !!!");
                    JOptionPane.showMessageDialog(null, "Doesnt exist in our database , this contact isnt on our application");
                    
                }else if (msg.equals("f")) {
                    System.out.println("Email Registered Before .. Please Enter Another one..");
                    JOptionPane.showMessageDialog(new Registration(), "Email here please enter another email !!");
                } else {
                    //new message recieved 
                    System.out.println(msg);
                    
                                      
                }
            } catch (IOException ex) {
                System.out.println("error hereee");
                MainPage mainPg=new MainPage();
                JOptionPane.showMessageDialog(mainPg,
					"Server down....",
					"Server  error",
					JOptionPane.ERROR_MESSAGE);
					System.exit(1);
            }
        }
    }

    @Override
    public void destroy() {
        try {
            dis.close();
            ps.close();
        } catch (IOException ex) {

        }
    }

    void sendMsg(String msg) {
        ps.println(msg);
    }
    
    
    

    

}
