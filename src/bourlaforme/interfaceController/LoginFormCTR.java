/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bourlaforme.interfaceController;

import bourlaforme.entity.User;
import bourlaforme.utils.ServiceUser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 *
 * @author aziz3
 */
public class LoginFormCTR implements Initializable{
    
    @FXML
    TextField txtEmail;
    
    @FXML
    PasswordField txtPassword;
    
    @FXML
    Button btnSignIn;
    
    @FXML
    TextField txtNom;
    
    @FXML
    TextField txtPrenom;
    
    Label labelMSG;
    
    @FXML
    Button btnSignUp;
    


    
    public void isSign(Event e) throws SQLException, IOException{
        ServiceUser serviceUser = new ServiceUser();
        User u = serviceUser.login(txtEmail.getText(), txtPassword.getText());
        if (u == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText("Identifiants incorrects");
            alert.setContentText("Veuillez vérifier votre adresse email et votre mot de passe.");
            alert.showAndWait();
        } else {
            System.out.println("Utilisateur connecté : " + u.getRole());
            User.connectedUser = u;
            makeSuccessNotification("logged in");
            
            Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        String role = User.connectedUser.getRole();
        Parent root;
            System.out.println(role);
        if(role.equals("ROLE_SUPER_ADMIN") || role.equals("ROLE_ADMIN_COACH") || role.equals("ROLE_ADMIN_CLUBOWNER")){
            System.out.println("addmin connected");
           root=FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/AdminList.fxml"));
        }else 
            root=FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/main-front.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
         
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void redirectSignUp(Event e) throws IOException{
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/SignUpForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void redirectSignIn(Event e) throws IOException{
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/LoginForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
    
    public static void makeSuccessNotification(String message) {
        TrayNotification tray = new TrayNotification();
        AnimationType type = AnimationType.POPUP;
        tray.setAnimationType(type);
        tray.setTitle("Success");
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.SUCCESS);
        tray.showAndDismiss(Duration.millis(3000));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
   /*     client.setToggleGroup(toggleGroup);
        client.setOnAction(event -> {
            role="ROLE_CLIENT";
        });

    coach.setToggleGroup(toggleGroup);
    coach.setOnAction(event -> {
        // set the role to ROLE_COACH
        role="ROLE_COACH";
    });*/

    }
    
    
    
      @FXML
    private void btnForgetAction(ActionEvent event) {
        try {

                    Parent page1 = FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/Resetpassword.fxml"));

                    Scene scene = new Scene(page1);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    stage.setScene(scene);

                    stage.show();

                } catch (IOException ex) {

                    System.out.println(ex.getMessage());

                }
    }
}
         






    
