/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmedsobhy
 */
public class ClientSocket {
    
    Socket socket;
    
    public ClientSocket(String ip,int port){
        
        try {
            socket=new Socket(ip,port);
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}
