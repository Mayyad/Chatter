/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaBeans;

/**
 *
 * @author ahmedsobhy
 */
public class User {
    
   private static String email;
   private static String name;
   private static User user = null; 
   
   public static User getInstance(){
       if(user == null){
           user=new User();
       }
       return user;
   }
   
   protected User(){
        // Exists only to defeat instantiation.
   }
    
   public void setEmail(String email){
       User.email=email;
   }
   
   public String getEmail(){
       return email;
   }
   
   public void setName(String name){
       this.name=name;
   }
   
   public String getName(){
       return name;
   }
}
