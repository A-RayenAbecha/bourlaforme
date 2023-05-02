/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bourlaforme.interfaceController;

import bourlaforme.utils.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aziz3
 */
public class ResetPasswordfController implements Initializable {

    @FXML
    private Button btnVerif;
    @FXML
    private PasswordField txtEmail;
    @FXML
    private PasswordField txtEmail1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnVerifAction(ActionEvent event) {
         Alert A = new Alert(Alert.AlertType.INFORMATION);
        if (!txtEmail.getText().equals("") && txtEmail.getText().equals(txtEmail1.getText()))
        {
            ServiceUser su = new ServiceUser();
            su.ResetPaswword(ResetpasswordController.EmailReset, txtEmail.getText());
            A.setContentText("Mot de passe modifié avec succes ! ");
            A.show();
              try {

            Parent page1 = FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/LoginForm.fxml"));

            Scene scene = new Scene(page1);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(scene);

            stage.show();

        } catch (IOException ex) {

           System.out.println(ex.getMessage());

        }
        }
        else 
        {
            A.setContentText("veuillez saisir un mot de passe conforme !");
            A.show();
        }
        
    }
    
}
