/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bourlaforme.interfaceController;

import bourlaforme.utils.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
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
import bourlaforme.utils.EmailSender;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Firas
 */
public class ResetpasswordController implements Initializable {
    
    public static int code;
    public static String EmailReset ; 
    @FXML
    private Button btnSendMail;
    @FXML
    private TextField txtEmail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnSendMailAction(ActionEvent event) {
         code = generateVerificationCode();
        Alert A = new Alert(Alert.AlertType.WARNING);
        ServiceUser su = new ServiceUser();

        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        boolean verifMail = txtEmail.getText().matches(emailRegex);

        if (!txtEmail.getText().equals("") && verifMail) {
            if (su.ChercherMail(txtEmail.getText()) == 1) {
                EmailReset = txtEmail.getText();
                EmailSender.sendEmail("mohamedaziz.hendili@esprit.tn", "ypmvzjtakvjfhgqy", txtEmail.getText(), "Verification code", "Votre code est : " + code);

                try {

                    Parent page1 = FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/VerifCode.fxml"));

                    Scene scene = new Scene(page1);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    stage.setScene(scene);

                    stage.show();

                } catch (IOException ex) {

                    System.out.println(ex.getMessage());

                }

            } else {
                A.setContentText("pas de compte lié avec cette adresse ! ");
                A.show();
            }
        } else {
            A.setContentText("Veuillez saisir une adresse mail valide ! ");
            A.show();
        }
    }
    
     private int generateVerificationCode() {
        // Générer un code de vérification aléatoire à 6 chiffres
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
    
}
