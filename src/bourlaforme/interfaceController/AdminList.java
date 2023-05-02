/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bourlaforme.interfaceController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import bourlaforme.Entity.User;
import bourlaforme.utils.ServiceUser;
import java.sql.SQLException;
import java.sql.SQLDataException;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author aziz3
 */

public class AdminList implements Initializable{
    
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<?, ?> email;
    @FXML
    private TableColumn<?, ?> nom;
    @FXML
    private TableColumn<?, ?> prenom;
    @FXML
    private TableColumn<?, ?> certificates;
    @FXML
    private TableColumn<?, ?> specialite;
    @FXML
    private TableColumn<?, ?> experiance;
    @FXML
    private TableColumn<?, ?> description;
    @FXML
    Button btnsupp;
    @FXML
    Button btnupdate;
    @FXML
    TextField txtNEmail;
    @FXML
    TextField txtNNom;
    @FXML
    TextField txtNPrenom;
    @FXML
    Button btnAdd;
    
  //  AdminController Lc = new AdminController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afficher();
        if (!User.connectedUser.getRoles().equals("ROLE_ADMIN_COACH")){
            btnupdate.setVisible(false);
        }
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        certificates.setCellValueFactory(new PropertyValueFactory<>("certificates"));
        specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        experiance.setCellValueFactory(new PropertyValueFactory<>("experiance"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        
    }
    void afficher(){
        ServiceUser serviceUser = new ServiceUser();
        ArrayList<User> u = new ArrayList<>();
        if(User.connectedUser.getRoles().equals("ROLE_SUPER_ADMIN")){
            u = (ArrayList<User>) serviceUser.afficher_admins("ADMIN");
        }else if (User.connectedUser.getRoles().equals("ROLE_ADMIN_COACH")){
            u = (ArrayList<User>) serviceUser.afficher_admins("ROLE_COACH");
        } else if (User.connectedUser.getRoles().equals("ROLE_ADMIN_CLUBOWNER")) {
            u = (ArrayList<User>) serviceUser.afficher_admins("ROLE_CLUBOWNER");
        }
        ObservableList<User> obs2 = FXCollections.observableArrayList(u);
        table.setItems(obs2);
    }
    public void resetTableData() throws SQLDataException, SQLException {
   /*     AdminController Lu = new AdminController();
        List<User> listrec = new ArrayList<>();
        listrec = Lu.getAllUsers();
        ObservableList<User> data = FXCollections.observableArrayList(listrec);
        table.setItems(data);*/
    }
    @FXML
    private void supp(ActionEvent event) throws SQLException {
         if (event.getSource() == btnsupp) {
            User rec = new User();

        ServiceUser Lu = new ServiceUser();
            rec.setId(table.getSelectionModel().getSelectedItem().getId());
            System.out.print(rec.getId());
            Lu.supprimer(rec.getId());
            System.out.println(rec.getId());
            afficher();
 
        }
         
    }
    
    @FXML
    private void update(ActionEvent event) throws SQLException {
     
                       User user = table.getSelectionModel().getSelectedItem();
                       user.setApproved(true);
                       ServiceUser AC = new ServiceUser();
                       AC.modifier(user);
                       afficher();
                    
                

    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void redirectAddUser(Event e) throws IOException{
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("/bourlaforme/interfaces/AdminAddUser.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
