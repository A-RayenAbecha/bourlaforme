 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.gui.image;

import edu.esprit.entities.Reclamation;
import edu.esprit.entities.Reponse;
import edu.esprit.gui.image.ModifierreponseController.PopupListener;
import edu.esprit.services.Service;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AfficherreponseController implements Initializable {
      @FXML
    private TableView<Reponse> tv;
    //int index=-1;

    @FXML
    private TableColumn<Reponse, String> sujet;
    @FXML
    private TableColumn<Reponse, String> motif;
    @FXML
    private TableColumn<Reponse, String> avis;
    @FXML
    private TableColumn<Reponse, Date> date;
   
   
    Reponse s = new Reponse();
    Service st = new Service();
    ObservableList<Reponse>  ListRes = FXCollections.observableArrayList();
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    @FXML
    private TextField filtree;

    /**
     * Initializes the controller class.
     */
    
    public void remplir(){
       try{
        List list = st.getAll();
        ListRes.addAll(list);
        sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        motif.setCellValueFactory(new PropertyValueFactory<>("motif"));
        avis.setCellValueFactory(new PropertyValueFactory<>("avis"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        tv.setItems(ListRes);
    }catch(Exception e){
           System.out.println("asslema"+e);
    }}
        
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        remplir();
        
    }
    
    
    void setdatareponse(Reponse s) {
     }

    @FXML
    private void supprimer(ActionEvent event) {
      
   Reponse reponse = tv.getSelectionModel().getSelectedItem();
    if (reponse != null) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer la reponse " + reponse.getSujet() + " ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
         
            ListRes.remove(reponse);
        }
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner une reponse à supprimer.");
    }
    }

    
    
    
    
    @FXML
   private void modifier(ActionEvent event) throws IOException, ParseException {
    Reponse reponse = tv.getSelectionModel().getSelectedItem();
    if (reponse != null) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifierreponse.fxml"));
        Parent root = loader.load();
        ModifierreponseController controller = loader.getController();
        
        
         controller. setListener(new ModifierreponseController.PopupListener() {
                        @Override
                        public void onInfoSent( Boolean Rendez_vousInstance) {
                           tv.getItems().clear();
                           
                           remplir();
                }
                    });
        controller.setAtrtribute(reponse);
        controller.setListener(new PopupListener() {
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
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner une reponse à modifier.");
    }
}

    @FXML
    private void handlesearch(KeyEvent event) {
             if (event.getCode() == KeyCode.ENTER) {
            String searchText = filtree.getText().trim();
            if (searchText.isEmpty()) {
            	tv.setItems(ListRes);
            } else {
                ObservableList<Reponse> filteredList = FXCollections.observableArrayList();
                for (Reponse b : ListRes) {
                    if (b.getSujet().toLowerCase().contains(searchText.toLowerCase())) {
                        filteredList.add(b);
                    }
                }
                tv.setItems(filteredList);
            }
        }
    }

    
    }

    


        

    
    
    
    

