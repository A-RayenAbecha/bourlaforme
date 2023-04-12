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
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ModifierreclamationController implements Initializable {

    @FXML
    private TextField nom_c1;
    @FXML
    private TextField sujet1;
    @FXML
    private TextField email1;
    @FXML
    private TextField description1;
    @FXML
    private TextField etat1;
    @FXML
    private Button annuler1;
    @FXML
    private Button confirmer;
    
     public interface PopupListener{
    void onInfoSent(Boolean value);
    }
    private PopupListener Listener;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
        Reclamation c = new Reclamation();
    public void setAtrtribute(Reclamation c) throws ParseException {
        this.c = c;
        // Remplir les champs de texte avec les données de la reponse
        nom_c1.setText(c.getNom());
        email1.setText(String.valueOf(c.getEmail()));
        sujet1.setText(String.valueOf(c.getSujet()));
        description1.setText(c.getDescription());
        etat1.setText(c.getEtat());
        
        
    }
   public void setListener(ModifierreclamationController.PopupListener listener) {
        this.Listener = listener;
    }
        // TODO
    
    @FXML
    private void annuler1(ActionEvent event) {
    Stage stage = (Stage) annuler1.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void confirmer(ActionEvent event) {
         c.setNom(nom_c1.getText());
        
        c.setEmail(email1.getText());
        c.setSujet(sujet1.getText());
        c.setDescription(description1.getText());
        c.setEtat(etat1.getText());
        
        
         ServiceReclamation sc = new ServiceReclamation();     
         sc.modifier(c);
          if (Listener != null) {
            Listener.onInfoSent(true);
    }
  Stage stage = (Stage) annuler1.getScene().getWindow();
        stage.close();
    }
    
}
