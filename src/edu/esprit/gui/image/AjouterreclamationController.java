/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui.image;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.services.ServiceReclamation;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AjouterreclamationController implements Initializable {

    @FXML
    private Button affiche;

    @FXML
    private Button ajoute;

    @FXML
    private TextField description1;

    @FXML
    private TextField etat1;

    @FXML
    private TextField email1;

    @FXML
    private TextField sujet1;

    @FXML
    private TextField nom_c1;
    /**
     * Initializes the controller class.
     */
    ServiceReclamation sc = new ServiceReclamation();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    

  @FXML
private void ajoute(ActionEvent event)  {
    String nom = nom_c1.getText();
    String email = email1.getText();
    String sujet = sujet1.getText();
    String etat = etat1.getText();
    String description = description1.getText();

    // Validation de saisie
    if (nom.isEmpty() || description.isEmpty() || email.isEmpty() || sujet.isEmpty() ||etat.isEmpty()||(!Pattern.compile("^(.+)@(.+)$").matcher(email1.getText()).matches())) {
        Alert validationAlert = new Alert(Alert.AlertType.ERROR, "Tous les champs sont requis ou bien verifier l'email", ButtonType.OK);
        validationAlert.showAndWait();
       
        
        

       
        
        
    }else{
        Reclamation c = new Reclamation();
    c.setNom(nom);
    c.setEmail(email);
    c.setEtat(etat);
    c.setDescription(description);
    c.setSujet(sujet);
  
    sc.ajouter(c);
    
    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Reclamation ajouté", ButtonType.OK);
    a.showAndWait();

    // Vider les champs de saisie
    nom_c1.setText("");
    email1.setText("");
    sujet1.setText("");
    etat1.setText("");
    description1.setText("");}
}


 

    @FXML
    private void affiche(ActionEvent event) throws IOException {
        Reclamation c = new Reclamation();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("afficherreclamation.fxml"));
        Parent root = loader.load();
        AfficherreclamationController controller = loader.getController();
        controller.setdatareclamation(c);

        affiche.getScene().setRoot(root);
    }

}  
