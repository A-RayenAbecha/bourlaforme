/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bourlaforme.utils;

import bourlaforme.Entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aziz3
 */
public class AdminController {
    Connection connection;
    Statement st;
    PreparedStatement ste;
    public List<User> getAllUsers()
   {
        List<User> userlist = new ArrayList<>();
        try {
            st = DataSource.openConnection().createStatement();
            ResultSet result =  st.executeQuery("SELECT * FROM user");
            while(result.next())
            {
                 userlist.add(new User(
                        result.getInt(1),
                        result.getString(2),
                        result.getString(6),
                        result.getString(5),
                        result.getString(8),
                        result.getString(9),
                        result.getString(10),
                        result.getString(11)
                ));
                System.out.println(result.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
       return userlist;
   }
     
    public void SupprimerUser(int id)
    {
        try {
            st = DataSource.openConnection().createStatement();
            st.executeUpdate("Delete FROM `user` WHERE id = " + id);
            DataSource.closeConnection();
        } catch (SQLException ex) {
            DataSource.closeConnection();
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public List<User> updateUser(User u ){
        List<User> utilisateur = new ArrayList<>();
        String sql="UPDATE user SET id=?,email=?,nom=?,prenom=?,password=? WHERE id ="+ u.getId();
         try {
            ste=connection.prepareStatement(sql);
            ste.setInt(1, u.getId());
            ste.setString(2, u.getEmail());
            ste.setString(6, u.getNom());
            ste.setString(5, u.getPrenom());
            ste.setString(4, u.getPassword());
            ste.executeUpdate();
            System.out.println("Utilisateur Modifiée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return utilisateur;
        
        
      }
}
