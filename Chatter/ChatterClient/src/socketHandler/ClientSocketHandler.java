/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketHandler;

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
public class ClientSocketHandler extends Thread{
    
    public Socket socket;
    public DataInputStream dis; //bystlm
    public PrintStream ps;   //byb3t
    
    public ClientSocketHandler(){
       
        try {
            socket=new Socket("localhost",12345);
            dis=new DataInputStream(socket.getInputStream());
            ps=new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.err.println("error");
        }
        start();
    }
    
    @Override
    public void run(){
        while(true){            
            try {
                String msg=dis.readLine();
                char ch=msg.charAt(0);
                System.out.println("el char aheh\t"+ch);
                if(ch=='1'){
                    //user exists
                    System.out.println("el kalam wsel");
                    //JOptionPane.showMessageDialog(null, "username and password are correct");
                    JOptionPane.showMessageDialog(null, "email and password are coorect");
                    
                    
                    Login loginObj = new Login();
                    loginObj.closeLoginWindow();
                    
                    MainPage mainPageObj = new MainPage();
                    mainPageObj.setVisible(true);
                    loginObj.setVisible(false);
                    
                    //userNameLbl.setText("User Name");
                   // mainPageObj.setUserNamelbl("xxx");
                       
                }
                else if(ch=='0'){
                    //user Not found
                    System.out.println("mesh mawgood");
                    JOptionPane.showMessageDialog(null, "Not found");
                    
                }else if(msg.equals("f")){
                    System.out.println("Email Registered Before .. Please Enter Another one..");                    
                    JOptionPane.showMessageDialog(new Registration(), "Email here please enter another email !!");
                }
                else{
                    System.out.println("nothing");
                }
                System.out.println(msg);
            } catch (IOException ex) {
                System.out.println("error");
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
    
    void sendMsg(String msg){
        ps.println(msg);
    }
    
}
