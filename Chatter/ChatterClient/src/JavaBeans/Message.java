/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaBeans;

/**
 *
 * @author ayyad
 */
public class Message {
    private static String message;
    
    public Message(){
        
    }
    
    public static void setMessage(String msg){
        message=msg;
    }
    
    public static String getMessage(){
        return message;
    }
}
