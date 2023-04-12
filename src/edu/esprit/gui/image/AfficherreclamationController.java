/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui.image;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.services.ServiceReclamation;
import edu.esprit.utils.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author HP
 */
 
public class AfficherreclamationController implements Initializable {
      @FXML
    private TableView<Reclamation> tv2;


    @FXML
    private TableColumn<Reclamation,String> nom;
    @FXML
    private TableColumn<Reclamation,String> email;
    @FXML
    private TableColumn<Reclamation,String> sujet;
    @FXML
    private TableColumn<Reclamation,String> etat;
    @FXML
    private TableColumn<Reclamation,String> description;
ServiceReclamation sc = new ServiceReclamation();
Reclamation c = new Reclamation();
 ObservableList<Reclamation>  ListCIR = FXCollections.observableArrayList();
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
  
    public void remplir(){
       try{
             List list = sc.getAll();
        ListCIR.addAll(list);
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
         tv2.setItems(ListCIR);
       }catch(Exception e){
           System.out.println("asslema"+e);
    }}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        remplir();
        
        
    }    
    void setdatareclamation(Reclamation c) {
     }  
   
    

    @FXML
    private void supprimer1(  ActionEvent event) {
        
    

  Reclamation c = tv2.getSelectionModel().getSelectedItem(); 
    if (c != null) {
        sc.supprimer(c.getId());
        ListCIR.remove(c); 
    }
    }

    @FXML
    private void modifier1(ActionEvent event) throws IOException, ParseException {
        Reclamation reclamation = tv2.getSelectionModel().getSelectedItem();
    if (reclamation != null) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifierreclamation.fxml"));
        Parent root = loader.load();
        ModifierreclamationController controller = loader.getController();
        
        controller.setListener(new ModifierreclamationController.PopupListener() {
               @Override
                        public void onInfoSent( Boolean Rendez_vousInstance) {
                           tv2.getItems().clear();
                           
                           remplir();
                }
                });
            
             controller.setAtrtribute(reclamation);
               controller.setListener(new ModifierreclamationController.PopupListener() {

            @Override
            public void onInfoSent(Boolean value) {
               
                remplir(); 
            }
        });
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner un reclamation à modifier.");
    }
}
    }

