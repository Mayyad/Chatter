/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSocket;

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
public class ServerSocketStream extends Thread{
    DataInputStream dis ; 
    PrintStream ps ; 
    public ServerSocketStream(Socket s){
        try {
            dis = new DataInputStream(s.getInputStream());
            ps  = new PrintStream(s.getOutputStream());
            
        } catch (IOException ex) {
            Logger.getLogger(ServerSocketStream.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        start();
       
    }
    
    public void run(){
        while(true){
            try {
                String str = dis.readLine();
                System.out.println(str);
            } catch (IOException ex) {
                Logger.getLogger(ServerSocketStream.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
