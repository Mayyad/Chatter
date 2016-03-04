/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverOperation;
import DBConnections.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ahmedsobhy
 */
public class ServerOperation {
    DBConnection dbConnection;
    Statement stm;
    
    public ServerOperation(){
        try {
            dbConnection=new DBConnection();
            stm=dbConnection.connection.createStatement();
        } catch (SQLException ex) {
               
        }
    }
    
    public void login(){
        try {
            stm.execute("sql");
        } catch (SQLException ex) {

        }
    }
    
    //register insert data 
     public boolean register(String str){
        try {
            //check email validation then insert into database
            System.out.println("done");                                
            stm = dbConnection.connection.createStatement();                        
            
            String str1=str+",1";
            String query = "INSERT INTO users( name, password, email,gender,age,status) VALUES("+str1+")";
         
            stm.execute(query);           
            
            stm.close();
            dbConnection.connection.close();
            return true;
        } catch (SQLException ex) {
            
            return false;
        }
    }
    //send message from to 
    public void sendMessage(String from,String to,String msg){
        try {
            //insert into database
            String query="INSERT INTO messages(from_id,to_id,msg_txt) VALUES("+"'"+from+"'"+","+"'"+to+"'"+","+"'"+msg+"'"+")   ";
            stm.execute(query);
            
            //send the message to the target
        } catch (SQLException ex) {
            System.out.println("error");
        }
    }
    
    //list the all friends of the user
    public String friendList(){
        return "Friend list";
    }
    
    //contact list  --ayaad

    public String contactList(int id){
        
       dbConnection = new DBConnections.DBConnection();
       
        String myFrineds = null;
        String frndlst = null;
        try {
            stm = dbConnection.connection.createStatement();
            String query = new String("SELECT * FROM friends WHERE user_id = '"+id+"' ");
            ResultSet rs = stm.executeQuery(query);
            
            while (rs.next())
            {
                myFrineds = rs.getString("friend_id");
                stm = dbConnection.connection.createStatement();
                String queryString = new String("SELECT * FROM users WHERE user_id = '"+myFrineds+"' ");
                ResultSet fl = stm.executeQuery(queryString);
                
                while (fl.next())
                {
                    frndlst += fl.getString("name");
                    frndlst += "*";
                }
            }
            
        } catch (SQLException ex) {
            return "Error";
        }
        
         return frndlst ;
    }
    
    
    
    public String groupList (int id ){
          
        dbConnection = new DBConnections.DBConnection();
        String grpsid = new String("");
        String grplst = new String ("");
        try {
            stm = dbConnection.connection.createStatement();
            String query = new String("SELECT * FROM group_list WHERE user_id = '"+id+"' ");
            ResultSet rs = stm.executeQuery(query);
            
            while (rs.next())
            {
                grpsid = rs.getString("group_id");
                stm = dbConnection.connection.createStatement();
                String queryString = new String("SELECT * FROM groups WHERE group_id = '"+grpsid+"' ");
                ResultSet fl = stm.executeQuery(queryString);
                
                while (fl.next())
                {
                    grplst += fl.getString("group_name");
                    grplst += "*";
                }
            }
            
        } catch (SQLException ex) {
            return "Error";
        }
        
         return grplst ;
    }
    
    
    
    
    
    public void createGroup(String grpname , int id){
        dbConnection = new DBConnections.DBConnection();
        try {
            stm = dbConnection.connection.createStatement();
        } catch (SQLException ex) {
            System.out.println("Error bs");
        }
        String insertString = new String ("INSERT INTO groups (group_name,user_id)" + " VALUES ( '"+grpname+"' , '"+id+"' )");
        
        try {
            stm.execute(insertString);
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
    
    public void addFriend(int my_id , int frnd_id)
    {
         dbConnection = new DBConnections.DBConnection();
        try {
            stm = dbConnection.connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
          String addfrnd = new String ("INSERT INTO friends " + "VALUES ('"+my_id+"' , '"+frnd_id+"')");
        try {
            stm.execute(addfrnd);
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
    
    
    
    
    public String returnName (int id)
    {
        dbConnection = new DBConnections.DBConnection();
        String name = new String ();
        try {
            stm = dbConnection.connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
         String query = new String("SELECT * FROM users WHERE user_id = '"+id+"' ");
        try {
           ResultSet s =  stm.executeQuery(query);
           s.next();
           name = s.getString("name");
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return name ;
    }
    
    public void setOnline (int id)
    {
        try {
            dbConnection = new DBConnections.DBConnection();
            
            stm = dbConnection.connection.createStatement();
            
            String query = new String ("UPDATE users SET status = '1' WHERE user_id = '"+id+"' ");
            
            stm.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    
    public void setBusy (int id)
    {
        try {
            dbConnection = new DBConnections.DBConnection();
            
            stm = dbConnection.connection.createStatement();
            
            String query = new String ("UPDATE users SET status = '3' WHERE user_id = '"+id+"' ");
            
            stm.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    
    public void setOffline (int id)
    {
        try {
            dbConnection = new DBConnections.DBConnection();
            
            stm = dbConnection.connection.createStatement();
            
            String query = new String ("UPDATE users SET status = '0' WHERE user_id = '"+id+"' ");
            
            stm.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    
    public void setAway (int id)
    {
        try {
            dbConnection = new DBConnections.DBConnection();
            
            stm = dbConnection.connection.createStatement();
            
            String query = new String ("UPDATE users SET status = '2' WHERE user_id = '"+id+"' ");
            
            stm.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    
    public String getOnlineUsers ()
    {
        String onlinelist = "";
        try {
            dbConnection = new DBConnections.DBConnection();
            stm = dbConnection.connection.createStatement();
            String query = new String ("SELECT * FROM users WHERE status = '1' ");
            
            ResultSet ol =  stm.executeQuery(query);
            
            while (ol.next())
            {
                try {
                    onlinelist += ol.getString("name");
                    onlinelist += "*";
                } catch (SQLException ex) {
                    Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return onlinelist;
    }
    
    
    
    public String getOfflineUsers ()
    
   
    {
        String offlinelist = "";
        try {
            dbConnection = new DBConnections.DBConnection();
            stm = dbConnection.connection.createStatement();
            String query = new String ("SELECT * FROM users WHERE status = '0' ");
            
            ResultSet ol =  stm.executeQuery(query);
            
            while (ol.next())
            {
                try {
    
   
                    offlinelist += ol.getString("name");
                    offlinelist += "*";
                } catch (SQLException ex) {
                    Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return offlinelist;
    }
    
    public int genderMaleStat ()
    {
        int x = 0;
        try {
           
            dbConnection = new DBConnections.DBConnection();
            stm = dbConnection.connection.createStatement();
            String query = new String ("SELECT COUNT(gender) FROM users WHERE gender = 'male' ");
            ResultSet all = stm.executeQuery(query);
            
            while (all.next())
            {
                try {
                    x = all.getInt(1);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x ;
    }
    
    public int genderFemaleStat ()
    {
        int x = 0;
        try {
           
            dbConnection = new DBConnections.DBConnection();
            stm = dbConnection.connection.createStatement();
            String query = new String ("SELECT COUNT(gender) FROM users WHERE gender = 'female' ");
            ResultSet all = stm.executeQuery(query);
            
            while (all.next())
            {
                try {
                    x = all.getInt(1);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return x ;
    }
    
    
    
    
    
    public int getOnline ()
    {
        int x = 0;
        try {
            
            dbConnection = new DBConnections.DBConnection();
            stm = dbConnection.connection.createStatement();
            String query = new String ("SELECT COUNT(status) FROM users WHERE status = '1' ");
            ResultSet onlines = stm.executeQuery(query);
            while (onlines.next())
            {
                try {
                    x = onlines.getInt(1);
                } catch (SQLException ex) {
                    Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return x;
    }
    
    
    
}
