/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package bourlaforme.interfaceController;

import com.bourlaforme.entities.User;
import static bourlaforme.interfaceController.SignUpFormCTR.makeSuccessNotification;
import bourlaforme.utils.ServiceUser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author aziz3
 */
public class AdminAddUserController implements Initializable{

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtNom;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private RadioButton rb_coach;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnCancel;
    String imagePathAjout="";
    
    
    User us= new User();
 //   AdminController AC= new AdminController();
    ToggleGroup toggleGroup;
    @FXML
    private RadioButton rb_clubOwner;
    @FXML
    private RadioButton rb_reservations;
    @FXML
    private RadioButton rb_products;
    
    
    String role;
    @FXML
    private Label path;
    @FXML
    private AnchorPane role_pane;
    @FXML
    public void redirectAdminList(Event e) throws IOException{
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/AdminList.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
@FXML    
    public void CreationSuccess(Event e) throws SQLException, IOException{
        us.setEmail(txtEmail.getText());
        us.setPassword(txtPassword.getText());
        us.setNom(txtNom.getText());
        us.setPrenom(txtPrenom.getText());
        us.setRoles(role);
       
            us.setCoach(false);
        us.setApproved(false);
        us.setImage(imagePathAjout);
        if(User.connectedUser.getRoles().equals("ROLE_ADMIN_COACH")) role = "ROLE_COACH";
        else if (User.connectedUser.getRoles().equals("ROLE_ADMIN_CLUBOWNER")) role = "ROLE_CLUBOWNER";
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

                            Parent root = FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/AdminList.fxml"));
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
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleGroup = new ToggleGroup();
        rb_coach.setToggleGroup(toggleGroup);
        rb_reservations.setToggleGroup(toggleGroup);
        rb_products.setToggleGroup(toggleGroup);
        rb_clubOwner.setToggleGroup(toggleGroup);
        
        
        rb_coach.setOnAction((ActionEvent e1) -> {
            role = "ROLE_ADMIN_COACH";
        });
        rb_reservations.setOnAction((ActionEvent e1) -> {
            role = "ROLE_ADMIN_RESERVATION";
        });
        rb_products.setOnAction((ActionEvent e1) -> {
            role = "ROLE_ADMIN_PRODUCT";
        });
        rb_clubOwner.setOnAction((ActionEvent e1) -> {
            role = "ROLE_ADMIN_CLUBOWNER";
        });
        if (!User.connectedUser.getRoles().equals("ROLE_SUPER_ADMIN")){
            role_pane.setVisible(false);
        }
        
        
        
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
            imagePathAjout = selectedFile.getAbsolutePath();
        }
    }
    
}

