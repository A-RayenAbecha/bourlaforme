/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bourlaforme.utils;

import bourlaforme.Entity.User;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 *
 * @author aziz3
 */
public class LoginController {
    
    Statement st;
    
    public boolean isLoggedIn(User us) throws SQLException{
        
        st = DataSource.openConnection().createStatement();
        
        ResultSet res = st.executeQuery("select * from user where email='"+us.getEmail()+"' and password = '"+us.getPassword()+"'");
        
        if(res.next())
            return true;
        return false;
        
        
    }
    public boolean RegisterAccount(User us) throws SQLException{
        
        st = DataSource.openConnection().createStatement();
        System.out.println(us.getEmail()+"+"+us.getPassword()+"+"+us.getNom()+"+"+us.getPrenom());
        String[] rolesArray = {us.getRole()};
        String rolesString = Arrays.toString(rolesArray);
        st.executeUpdate("insert into user(email,password,roles,nom,prenom,is_coach,approved) values('"+us.getEmail()+"','"+us.getPassword()+"','"+rolesString+"','"+us.getNom()+"','"+us.getPrenom()+"','"+us.getIs_coach()+"','"+us.getApproved()+"')");

        return true;
    }
}
