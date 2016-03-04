/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBConnections;

import java.sql.*;

/**
 *
 * @author ahmedsobhy
 */
public class DBConnection extends Thread {

    public Connection connection;
    String URL = "jdbc:mysql://localhost:3306/Chatter";
<<<<<<< HEAD
    String user = "root";
    String pass = "";
=======
    String user = "ayyad";
    String pass = "sql";
>>>>>>> 7634878a3797eb721f19dc59f611b86f9b938277

    public DBConnection() {
        try {
            connection = DriverManager.getConnection(URL, user, pass);
            start();
        } catch (Exception e) {

        }
    }

    @Override
    public void run() {

    }

}
