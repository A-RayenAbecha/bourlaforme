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
import bourlaforme.utils.AdminController;
import java.sql.SQLException;
import java.sql.SQLDataException;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author Mega-PC
 */

public class AdminList implements Initializable{
    
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<?, ?> id;
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
    
    AdminController Lc = new AdminController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<User> u = new ArrayList<>();
        u = (ArrayList<User>) Lc.getAllUsers();
        ObservableList<User> obs2 = FXCollections.observableArrayList(u);
        table.setItems(obs2);
        
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        certificates.setCellValueFactory(new PropertyValueFactory<>("certificates"));
        specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));
        experiance.setCellValueFactory(new PropertyValueFactory<>("experiance"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
    }
    public void resetTableData() throws SQLDataException, SQLException {
        AdminController Lu = new AdminController();
        List<User> listrec = new ArrayList<>();
        listrec = Lu.getAllUsers();
        ObservableList<User> data = FXCollections.observableArrayList(listrec);
        table.setItems(data);
    }
    @FXML
    private void supp(ActionEvent event) throws SQLException {
         if (event.getSource() == btnsupp) {
            User rec = new User();

        AdminController Lu = new AdminController();
            rec.setId(table.getSelectionModel().getSelectedItem().getId());
            System.out.print(rec.getId());
            Lu.SupprimerUser(rec.getId());
            System.out.println(rec.getId());
            resetTableData();
 
        }
         
    }
    
    @FXML
    private void update(ActionEvent event) throws SQLException {
         if (event.getSource() == btnupdate) {
            User user = table.getSelectionModel().getSelectedItem();
            user.setEmail(txtNEmail.getText());
            user.setNom(txtNNom.getText());
            user.setPrenom(txtNPrenom.getText());
            AdminController AC = new AdminController();
            AC.updateUser(user);
            resetTableData();
         }
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
