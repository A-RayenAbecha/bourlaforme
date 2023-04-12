/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui.image;

import edu.esprit.entities.Reponse;
import edu.esprit.services.Service;
import java.net.URL;
import java.sql.Date;
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
public class ModifierreponseController implements Initializable {
   
    private PopupListener listener;

    @FXML
    private TextField noms2;
    @FXML
    private TextField motiff2;
    @FXML
    private TextField aviss2;
    
    @FXML
    private DatePicker horaires2;
    @FXML
    private Button enregitrementbtn;
    @FXML
    private Button annuler;
    public interface PopupListener{
    void onInfoSent(Boolean value);
    }
    private PopupListener Listener;
    
   

   
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }     
    Reponse s =new Reponse();
    public void setAtrtribute(Reponse s) throws ParseException {
        this.s = s;
        // Remplir les champs de texte avec les données de la reponse
        noms2.setText(s.getSujet());
        motiff2.setText(s.getMotif());
        aviss2.setText(s.getAvis());
       
        java.util.Date utilDate =new java.util.Date(s.getDate().getTime());
        LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate localDate1 = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
  
      System.out.println(localDate);
        // format the date using the formatter
            
        
        horaires2.setValue(localDate);
    }
   public void setListener(PopupListener listener) {
        this.listener = listener;
    }
    @FXML
    void annuler(ActionEvent event) {
   Stage stage = (Stage) annuler.getScene().getWindow();
        stage.close();
    }
    

    @FXML
    private void enregitrement(ActionEvent event) {
          s.setSujet(noms2.getText());
        s.setMotif(motiff2.getText());
        s.setAvis(aviss2.getText());
        
        java.sql.Date date = java.sql.Date.valueOf(horaires2.getValue());
        s.setDate(date);
        Service st = new Service();
        st.modifier(s);
        if (listener != null) {
            listener.onInfoSent(true);
        }
         Stage stage = (Stage) annuler.getScene().getWindow();
        stage.close();
    }
    





  
}

    
