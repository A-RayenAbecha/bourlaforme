/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bourlaforme.interfaceController;

import bourlaforme.Entity.User;
import bourlaforme.utils.LoginController;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author aziz3
 */
public class LoginFormCTR {
    
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
    @FXML
    Label labelMSG;
    @FXML
    Button btnSignUp;
    @FXML
    RadioButton rb_client;
    @FXML
    RadioButton rb_coach;
    
    /*
    public void initialize() {
        
        txtNom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]+")) {
                txtNom.setText(oldValue);
                showAlert("First name should contain only letters.");
            }
        });
        txtPrenom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]+")) {
                txtPrenom.setText(oldValue);
                showAlert("Last name should contain only letters.");
            }
        });
        txtEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
                txtEmail.setText(oldValue);
                showAlert("Please enter a valid email address.");
            }
        });
        txtPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
                txtPassword.setText(oldValue);
                showAlert("Password should be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespaces.");
            }
        });
    
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    */
    User us= new User();
    LoginController Lc = new LoginController();
    
    
    ToggleGroup toggleGroup;
    
    public void isSign(Event e) throws SQLException, IOException{
        
        us.setEmail(txtEmail.getText());
        us.setPassword(txtPassword.getText());
        if(txtEmail.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")){
            if(txtPassword.getText().matches("^.{6,}$")){
                if(Lc.isLoggedIn(us)){
                    Node node = (Node) e.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();

                    Parent root=FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/Home.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else
                    labelMSG.setText("email or password is uncorrect");
            }
            else
                showAlert("Please enter a password.");
        }else
            showAlert("Please enter a valid email address.");
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
    
    @FXML
    public void CreationSuccess(Event e) throws SQLException, IOException{
        us.setEmail(txtEmail.getText());
        us.setPassword(txtPassword.getText());
        us.setNom(txtNom.getText());
        us.setPrenom(txtPrenom.getText());
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
        if(txtEmail.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")){
            if(txtNom.getText().matches("[a-zA-Z]+")){
                if(txtPrenom.getText().matches("[a-zA-Z]+")){
                    if(txtPassword.getText().matches("^.{6,}$")){
                        if(Lc.RegisterAccount(us)){
                            Node node = (Node) e.getSource();
                            Stage stage = (Stage) node.getScene().getWindow();

                            Parent root = FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/Home.fxml"));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }
                    }else
                        showAlert("Please enter a password.");
                }else
                    showAlert("Family name should contain only letters.");
            }else
                showAlert("First name should contain only letters.");
        }else
            showAlert("Please enter a valid email address.");
    }
        
}
