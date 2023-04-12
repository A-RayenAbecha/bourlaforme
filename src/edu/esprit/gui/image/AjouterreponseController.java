/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui.image;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.services.Service;
import edu.esprit.services.ServiceReclamation;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
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
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AjouterreponseController implements Initializable {
 
    @FXML
    private TextField motiff;

    @FXML
    private Button afficher;

    @FXML
    private Button ajouter;

   

    @FXML
    private DatePicker dates;

    @FXML
    private TextField aviss;

    @FXML
    private TextField suj;

    /**
     * Initializes the controller class.
     */
     Service st = new Service();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajouter(ActionEvent event) {
    String sujet = suj.getText();
    String motif = motiff.getText();
    String avis = aviss.getText();
    
    Date date =  Date.valueOf(dates.getValue());
    

    // Validation de saisie
    if (sujet.isEmpty() || motif.isEmpty() || avis.isEmpty()  || date.before(Date.valueOf(LocalDate.now()))) {
        Alert validationAlert = new Alert(Alert.AlertType.ERROR, "Tous les champs sont requis", ButtonType.OK);
        validationAlert.showAndWait();
        
    }else{
    Reponse s = new Reponse();
    s.setMotif(motif);
   
    s.setDate(date);
    
    s.setAvis(avis);
    s.setSujet(sujet);
    st.ajouter(s);
    }

    Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Reponse ajouté", ButtonType.OK);
    a.showAndWait();

    // Vider les champs de saisie
    suj.setText("");
    motiff.setText("");
    aviss.setText("");
    
    dates.setValue(null);
    
}


    
    
    
 
      
    

    

    @FXML
    private void afficher(ActionEvent event) throws IOException {
         Reponse s = new Reponse();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("afficherreponse.fxml"));
            Parent root = loader.load();
            AfficherreponseController controller = loader.getController();
            controller.setdatareponse(s);

      afficher.getScene().setRoot(root);
    }
    
}
