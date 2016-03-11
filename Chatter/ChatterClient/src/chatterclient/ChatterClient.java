/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatterclient;

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import socketHandler.*;

/**
 *
 * @author ahmedsobhy
 */
public class ChatterClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Login l = new Login();
        l.setVisible(true);

        l.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

}
