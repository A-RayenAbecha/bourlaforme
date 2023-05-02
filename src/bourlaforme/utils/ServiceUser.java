package bourlaforme.utils;

import com.bourlaforme.entities.User;

import java.io.StringReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue.ValueType;



public class ServiceUser {
    private Connection cnx;

    // Constructeur
    public ServiceUser() {
        cnx = DataSource.getInstance().getCnx();
    }

    private String setRole(String role) {

        JsonArray roleArray = Json.createArrayBuilder()
                .add(role)
                .build();
        String roleString = roleArray.toString();
        return roleString;
    }
    private String getRole(String roleString) {
        JsonReader jsonReader = Json.createReader(new StringReader(roleString));
        JsonStructure jsonStructure = jsonReader.read();

        if (jsonStructure.getValueType() == ValueType.ARRAY) {
            // Get the first element from the array
            String role = ((JsonArray) jsonStructure).getString(0);

            // Check if the role is "ROLE_ADMIN"
            return role;
        } else if (jsonStructure.getValueType() == ValueType.OBJECT) {
            // Get the "role" property from the object
            String role = ((JsonObject) jsonStructure).getString("role");

            // Check if the role is "ROLE_ADMIN"
            return role;
        } else {
            // The JSON structure is not valid
            throw new JsonException("Invalid JSON structure");
        }
    }


    public void ajouter(User u) {
        try {
            String requete = "INSERT INTO user (email, roles, password, nom, prenom, image, certificates, specialite, experiance, description, is_coach, approved, likes, moyenne) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, u.getEmail());
            pst.setString(2, setRole(u.getRoles()));
            pst.setString(3, u.getPassword());
            pst.setString(4, u.getNom());
            pst.setString(5, u.getPrenom());
            pst.setString(6, u.getImage());
            pst.setString(7, u.getCertificates());
            pst.setString(8, u.getSpecialite());
            pst.setString(9, u.getExperiance());
            pst.setString(10, u.getDescription());
            pst.setBoolean(11, u.isCoach());
            pst.setBoolean(12, u.isApproved());
            pst.setString(13, "");
            pst.setDouble(14, 0);
            pst.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public List<User> afficher() {
        List<User> listUsers = new ArrayList<>();
        try {
            String requete = "SELECT * FROM user";
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(requete);
            while (rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        getRole(rs.getString("roles")),
                        rs.getString("password"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("image"),
                        rs.getString("certificates"),
                        rs.getString("specialite"),
                        rs.getString("experiance"),
                        rs.getString("description"),
                        rs.getBoolean("is_coach"),
                        rs.getBoolean("approved"),
                        rs.getString("likes"),
                        rs.getDouble("moyenne")
                );
                listUsers.add(u);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listUsers;
    }
    
    public List<User> afficher_admins(String q) {
        List<User> listUsers = new ArrayList<>();
        try {
            String requete = "SELECT * FROM user where roles like '%"+q+"%'";
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(requete);
            while (rs.next()) {
                User u = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        getRole(rs.getString("roles")),
                        rs.getString("password"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("image"),
                        rs.getString("certificates"),
                        rs.getString("specialite"),
                        rs.getString("experiance"),
                        rs.getString("description"),
                        rs.getBoolean("is_coach"),
                        rs.getBoolean("approved"),
                        rs.getString("likes"),
                        rs.getDouble("moyenne")
                );
                listUsers.add(u);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listUsers;
    }

    
    
    
    public void modifier(User u) {
        try {
            String requete = "UPDATE user SET email=?, roles=?, password=?, nom=?, prenom=?, image=?, certificates=?, specialite=?, experiance=?, description=?, is_coach=?, approved=?, likes=?, moyenne=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, u.getEmail());
            pst.setString(2, setRole(u.getRoles()));
            pst.setString(3, u.getPassword());
            pst.setString(4, u.getNom());
            pst.setString(5, u.getPrenom());
            pst.setString(6, u.getImage());
            pst.setString(7, u.getCertificates());
            pst.setString(8, u.getSpecialite());
            pst.setString(9, u.getExperiance());
            pst.setString(10, u.getDescription());
            pst.setBoolean(11, u.isCoach());
            pst.setBoolean(12, u.isApproved());
            pst.setString(13, u.getLikes());
            pst.setDouble(14, u.getMoyenne());
            pst.setInt(15, u.getId());
            pst.executeUpdate();
            System.out.println("Utilisateur modifié avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void supprimer(int id) {
        try {
            String requete = "DELETE FROM user WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Utilisateur supprimé avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public User login(String email, String password) {
        User user = null;
        try {
            String query = "SELECT * FROM user WHERE email=? AND password=?";
            PreparedStatement pst = cnx.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        getRole(rs.getString("roles")),
                        rs.getString("password"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("image"),
                        rs.getString("certificates"),
                        rs.getString("specialite"),
                        rs.getString("experiance"),
                        rs.getString("description"),
                        rs.getBoolean("is_coach"),
                        rs.getBoolean("approved"),
                        rs.getString("likes"),
                        rs.getDouble("moyenne")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return user;
    }

 public int ChercherMail(String email) {

        try {
            String req = "SELECT * from `user` WHERE `user`.`email` ='" + email + "'  ";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                if (rs.getString("email").equals(email)) {
                    System.out.println("mail trouvé ! ");
                    return 1;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
 
 
  public void ResetPaswword(String email ,String password)
    {
         try {

                String req = "UPDATE User SET password = ? WHERE email = ?";
                PreparedStatement ps = cnx.prepareStatement(req);
               
                ps.setString(1, password);
                ps.setString(2, email);
               

                ps.executeUpdate();
                System.out.println("Password updated !");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    
    }



}
