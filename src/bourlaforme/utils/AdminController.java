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
import java.util.Arrays;
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
     
    public void updateUser(User u){
         try {
            Connection conn = DataSource.openConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET email=?, nom=?, prenom=? WHERE id=?");
            ps.setString(1, u.getEmail());
            ps.setString(2, u.getNom());
            ps.setString(3, u.getPrenom());
            ps.setInt(4, u.getId());
            ps.executeUpdate();
            DataSource.closeConnection();
        } catch (SQLException ex) {
            DataSource.closeConnection();
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public boolean AddUser(User us) throws SQLException{
        
        st = DataSource.openConnection().createStatement();
        System.out.println(us.getEmail()+"+"+us.getPassword()+"+"+us.getNom()+"+"+us.getPrenom());
        String[] rolesArray = {us.getRole()};
        String rolesString = Arrays.toString(rolesArray);
        st.executeUpdate("insert into user(email,password,roles,nom,prenom,is_coach,approved) values('"+us.getEmail()+"','"+us.getPassword()+"','"+rolesString+"','"+us.getNom()+"','"+us.getPrenom()+"','"+us.getIs_coach()+"','"+us.getApproved()+"')");

        return true;
    }
}
