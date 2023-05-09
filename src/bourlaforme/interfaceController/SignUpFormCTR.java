/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bourlaforme.interfaceController;

import com.bourlaforme.entities.User;
import bourlaforme.utils.ServiceUser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
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
public class SignUpFormCTR implements  Initializable{

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtNom;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnSignIn;
    @FXML
    private Label path;
    @FXML
    private RadioButton rb_coach;
    @FXML
    private RadioButton rb_client;
    @FXML
    private RadioButton coach;
    @FXML
    private RadioButton client;
    
    private String role;
    
    

   // String role;
    private String imagePathAjout="";
    User us= new User();
    //LoginController Lc = new LoginController();
    
    
    ToggleGroup toggleGroup ;
    
    @FXML
    public void CreationSuccess(Event e) throws SQLException, IOException{
        us.setEmail(txtEmail.getText());
        us.setPassword(txtPassword.getText());
        us.setNom(txtNom.getText());
        us.setPrenom(txtPrenom.getText());
        us.setRoles(role);
        if (role == "ROLE_COACH"){
            us.setCoach(true);
        } else 
            us.setCoach(false);
        us.setApproved(true);
        us.setImage(imagePathAjout);
//        toggleGroup = new ToggleGroup();
        
        
        ServiceUser serviceUser = new ServiceUser();
 if(imagePathAjout!=""){
     if(txtEmail.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")){
            if(txtNom.getText().matches("[a-zA-Z]+")){
                if(txtPrenom.getText().matches("[a-zA-Z]+")){
                    if(txtPassword.getText().matches("^.{6,}$")){
                            serviceUser.ajouter(us);
                            makeSuccessNotification("account created");
                            Node node = (Node) e.getSource();
                            Stage stage = (Stage) node.getScene().getWindow();

                            Parent root = FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/LoginForm.fxml"));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                    //    }
                    }else
                        showAlert("Please enter a password.");
                }else
                    showAlert("Family name should contain only letters.");
            }else
                showAlert("First name should contain only letters.");
        }else
            showAlert("Please enter a valid email address.");
       } else 
           showAlert("Please an image.");
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


    @FXML
    private void redirectSignIn(ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/LoginForm.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

      @FXML
    void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Get the selected file name
            String fileName = selectedFile.getName();

            // Set the image path with the new file name
            imagePathAjout = fileName;

            // Copy the selected file to the uploads folder
            Path sourcePath = Paths.get(selectedFile.getAbsolutePath());
            Path destinationPath = Paths.get("C:\\Users\\aziz3\\OneDrive\\Bureau\\projet\\Projet_Anarchy\\public\\uploads\\articles\\" + fileName);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
  toggleGroup = new ToggleGroup();
        ToggleGroup toggleGroup = new ToggleGroup();
        
        rb_client.setToggleGroup(toggleGroup);
        rb_client.setOnAction(event -> {
            role="ROLE_CLIENT";
            us.setCoach(false);
        });

    rb_coach.setToggleGroup(toggleGroup);
    rb_coach.setOnAction(event -> {
        // set the role to ROLE_COACH
        role="ROLE_COACH";
        us.setCoach(true);
    });
    }
    
     private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}
