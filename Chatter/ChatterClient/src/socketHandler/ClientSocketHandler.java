/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketHandler;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author ahmedsobhy
 */
public class ClientSocketHandler extends Thread{
    
    public Socket socket;
    public DataInputStream dis;
    public PrintStream ps;
    
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
                System.out.println(msg);
            } catch (IOException ex) {
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
