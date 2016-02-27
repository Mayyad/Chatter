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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmedsobhy
 */
public class ClientSocketHandler extends Thread{
    DataInputStream dis;
    PrintStream ps;
    ClientSocket clientSocket;
    
    public ClientSocketHandler(Socket s){
       
        try {
            dis=new DataInputStream(s.getInputStream());
            ps=new PrintStream(s.getOutputStream());
        } catch (IOException ex) {
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
    
    void sendMsg(String msg){
        ps.println(msg);
    }
    
}
