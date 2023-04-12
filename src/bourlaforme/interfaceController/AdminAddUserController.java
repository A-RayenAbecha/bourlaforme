/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package bourlaforme.interfaceController;

import bourlaforme.Entity.User;
import bourlaforme.utils.AdminController;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aziz3
 */
public class AdminAddUserController {

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPrenom;
    @FXML
    private TextField txtNom;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private RadioButton rb_client;
    @FXML
    private RadioButton rb_coach;
    @FXML
    private Button btnSignUp;
    @FXML
    private Button btnCancel;

    
    User us= new User();
    AdminController AC= new AdminController();
    ToggleGroup toggleGroup;
    
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
        us.setNom(txtPrenom.getText());
        us.setPrenom(txtNom.getText());
        toggleGroup = new ToggleGroup();
        rb_client.setToggleGroup(toggleGroup);
        rb_coach.setToggleGroup(toggleGroup);
        
        
        rb_client.setOnAction((ActionEvent e1) -> {
            if (rb_client.isSelected()) {
                us.setRole("\"ROLE_CLIENT\"");
                us.setIs_coach(0);
                us.setApproved(1);
            }
        });
        rb_coach.setOnAction((ActionEvent e1) -> {
            if (rb_coach.isSelected()) {
                us.setRole("\"ROLE_COACH\"");
                us.setIs_coach(1);
                us.setApproved(0);
            }
        });
        if(AC.AddUser(us)){
            Node node = (Node) e.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            
            Parent root = FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/AdminList.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
          
    }
    
}