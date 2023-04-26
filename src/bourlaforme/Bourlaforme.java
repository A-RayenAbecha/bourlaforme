/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package bourlaforme;

import bourlaforme.Entity.User;
import bourlaforme.utils.ServiceUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.management.relation.Role;

/**
 *
 * @author rayen
 */
public class Bourlaforme extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/LoginForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Create a User object with some sample data
        List<String> role =new ArrayList<>() ;
        role.add("role_user");
User user = new User();
user.setEmail("john.doe@example.com");
user.setRoles(role);
user.setPassword("mypassword");
user.setNom("Doe");
user.setPrenom("John");
user.setImage("profile.jpg");
user.setCertificates("certificate1, certificate2");
user.setSpecialite("Software Engineering");
user.setExperiance("5 years");
user.setDescription("I am a software engineer with experience in Java and Python");
user.setIsCoach(true);
user.setApproved(true);
user.setLikes("500");
user.setMoyenne(4.5);

// Add the user to the database using the ajouter method
ServiceUser serviceUser =  new ServiceUser();
serviceUser.ajouter(user);
        
        
        
        
        launch(args);
    }
    
}
