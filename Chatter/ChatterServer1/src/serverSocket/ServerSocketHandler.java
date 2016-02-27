/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmedsobhy
 */
public class ServerSocketHandler {
    ServerSocket ss;
    
    public ServerSocketHandler(int port){
        try {
            ss=new ServerSocket(port);
            
        } catch (IOException ex) {
        }
        
        while(true){
            try {
                Socket s=ss.accept();
            } catch (IOException ex) {
            }
        }
    }
    
}
