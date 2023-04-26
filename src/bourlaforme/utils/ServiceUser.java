/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bourlaforme.utils;

/**
 *
 * @author aziz3
 */
import bourlaforme.Entity.User;
import bourlaforme.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;





public class ServiceUser {
    
    private Connection cnx = DataSource.getInstance().getCnx();

    

public void ajouter(User u) {
    try {
        String requete = "INSERT INTO user (email, roles, password, nom, prenom, image, certificates, specialite, experiance, description, is_coach, approved, likes, moyenne) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = cnx.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, u.getEmail());
        pst.setObject(2, u.getRoles().toArray());
        pst.setString(3, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(13)));
        System.out.println( BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(13)));
        pst.setString(4, u.getNom());
        pst.setString(5, u.getPrenom());
        pst.setString(6, u.getImage());
        pst.setString(7, u.getCertificates());
        pst.setString(8, u.getSpecialite());
        pst.setString(9, u.getExperiance());
        pst.setString(10, u.getDescription());
        pst.setBoolean(11, u.isIsCoach());
        pst.setBoolean(12, u.isApproved());
        pst.setString(13, u.getLikes());
        pst.setDouble(14, u.getMoyenne());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            u.setId(rs.getInt(1));
        }
        System.out.println("Utilisateur ajouté avec succès !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    
    public void supprimer(User u) {
        try {
            String requete = "DELETE FROM user WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, u.getId());
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Utilisateur supprimé avec succès !");
            } else {
                System.out.println("Aucun utilisateur trouvé avec l'identifiant spécifié.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modifier(User u) {
    try {
        String requete = "UPDATE user SET email=?, roles=?, password=?, nom=?, prenom=?, image=?, certificates=?, specialite=?, experiance=?, description=?, isCoach=?, approved=?, likes=?, moyenne=? WHERE id=?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setString(1, u.getEmail());
        pst.setObject(2, u.getRoles().toArray());
        pst.setString(3, BCrypt.hashpw(u.getPassword(), BCrypt.gensalt()));
        pst.setString(4, u.getNom());
        pst.setString(5, u.getPrenom());
        pst.setString(6, u.getImage());
        pst.setString(7, u.getCertificates());
        pst.setString(8, u.getSpecialite());
        pst.setString(9, u.getExperiance());
        pst.setString(10, u.getDescription());
        pst.setBoolean(11, u.isIsCoach());
        pst.setBoolean(12, u.isApproved());
        pst.setString(13, u.getLikes());
        pst.setDouble(14, u.getMoyenne());
        pst.setInt(15, u.getId());
        pst.executeUpdate();
        System.out.println("Utilisateur modifié avec succès !");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}


public List<User> afficher() {
    List<User> users = new ArrayList<>();
    try {
        String requete = "SELECT * FROM user";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setEmail(rs.getString("email"));
            u.setRoles(Arrays.asList(rs.getString("roles").split(",")));
            u.setPassword(rs.getString("password"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setImage(rs.getString("image"));
            u.setCertificates(rs.getString("certificates"));
            u.setSpecialite(rs.getString("specialite"));
            u.setExperiance(rs.getString("experiance"));
            u.setDescription(rs.getString("description"));
            u.setIsCoach(rs.getBoolean("isCoach"));
            u.setApproved(rs.getBoolean("approved"));
            u.setLikes(rs.getString("likes"));
            u.setMoyenne(rs.getDouble("moyenne"));
            users.add(u);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return users;
}



public User login(String email, String password) {
    try {
        String requete = "SELECT * FROM user WHERE email=?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            String hashedPassword = rs.getString("password");
            if (BCrypt.checkpw(password, hashedPassword)) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setRoles(Arrays.asList(rs.getString("roles").split(",")));
                u.setPassword(hashedPassword);
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setImage(rs.getString("image"));
                u.setCertificates(rs.getString("certificates"));
                u.setSpecialite(rs.getString("specialite"));
                u.setExperiance(rs.getString("experiance"));
                u.setDescription(rs.getString("description"));
                u.setIsCoach(rs.getBoolean("is_coach"));
                u.setApproved(rs.getBoolean("approved"));
                u.setLikes(rs.getString("likes"));
                u.setMoyenne(rs.getDouble("moyenne"));
                return u;
            }
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return null;
}


}
