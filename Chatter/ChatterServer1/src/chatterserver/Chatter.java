/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatterserver;
import serverOperation.ServerOperation;
import serverSocket.*;

/**
 *
 * @author ahmedsobhy
 */
public class Chatter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //ServerSocketHandler serverSocketHandler=new ServerSocketHandler(12345);
        
        //MainPage mainPage=new MainPage();
        //mainPage.setVisible(true);
        ServerOperation s = new ServerOperation();
        int test = s.genderFemaleStat();
        
        
       System.out.println(test);
    }
    
}
