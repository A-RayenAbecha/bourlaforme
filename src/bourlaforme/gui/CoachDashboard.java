package bourlaforme.gui;

import bourlaforme.entities.Club;
import bourlaforme.entities.User;
import bourlaforme.services.ServiceClub;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.Alert.AlertType;

public class CoachDashboard implements Initializable {

    @FXML
    private TableColumn<?, ?> activiteColumn;

    @FXML
    private Button ajouterBtn;

    @FXML
    private Button ajouterBtn1;


    @FXML
    private TableColumn<?, ?> idColumn;

    @FXML
    private TableColumn<?, ?> localisationColumn;

    @FXML
    private TextField localisationField;

    @FXML
    private TextField localisationField1;

    @FXML
    private TableColumn<?, ?> nomColumn;

    @FXML
    private TextField nomField;

    @FXML
    private TextField nomField1;

    @FXML
    private TextField descriptionField;

    @FXML
    private ImageView imageClub;

    @FXML
    private TextField descriptionField1;

    @FXML
    private TableColumn<?, ?> prixColumn;

    @FXML
    private TextField prixField;

    @FXML
    private TextField prixField1;

    @FXML
    private TableView<Club> tableViewClub;

    @FXML
    private TableColumn<?, ?> telephoneColumn;

    @FXML
    private TableColumn<?, ?> telephoneColumn2;

    @FXML
    private TextField telephoneField;

    @FXML
    private TextField telephoneField1;

    @FXML
    private TextField typeActiviteField;

    @FXML
    private TextField typeActiviteField1;

    private ObservableList<Club> clubsdata;

    private String imagePathAjout;
    private String imagePathModif;

    User connectedUser;
    @FXML
    void selection(MouseEvent event) {
        // Récupération de la ligne sélectionnée
        Club selectedClub = tableViewClub.getSelectionModel().getSelectedItem();

        // Vérification que la sélection n'est pas nulle
        if (selectedClub != null) {
            // Remplissage des champs de texte avec les données de la ligne sélectionnée
            nomField1.setText(selectedClub.getNom());
            localisationField1.setText(selectedClub.getLocalisation());
            typeActiviteField1.setText(selectedClub.getTypeActivite());
            telephoneField1.setText(selectedClub.getTelephone());
            descriptionField1.setText(selectedClub.getDescription());
            prixField1.setText(selectedClub.getPrix());
            imagePathModif = selectedClub.getImage(); // sauvegarde du chemin de l'image pour la modification

            // Affichage de l'image du club
            File file = new File(imagePathModif);
            Image image = new Image(file.toURI().toString());
            imageClub.setImage(image);
        }
    }


    @FXML
    void upload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePathAjout = selectedFile.getAbsolutePath();
        }
    }

    @FXML
    void upload2(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePathAjout = selectedFile.getAbsolutePath();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         connectedUser = new User(2);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        localisationColumn.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        activiteColumn.setCellValueFactory(new PropertyValueFactory<>("typeActivite"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        telephoneColumn2.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        ServiceClub serviceClub = new ServiceClub();

        clubsdata = FXCollections.observableList(serviceClub.afficher());
        tableViewClub.setItems(clubsdata);
    }

    @FXML
    void ajouter(ActionEvent event) {
        // Récupération des valeurs des champs de texte
        String nom = nomField.getText();
        String localisation = localisationField.getText();
        String image = imagePathAjout; // récupérer l'image à partir de l'upload précédent
        String typeActivite = typeActiviteField.getText();
        int idClubOwnerId = connectedUser.getId(); // Méthode à implémenter pour récupérer l'id de l'utilisateur courant
        String telephone = telephoneField.getText();
        String description = descriptionField.getText();
        String prix = prixField.getText();
        double longitude = 0;
        double latitude = 0;

        // Création d'un nouveau club avec les valeurs des champs de texte et l'utilisateur courant
        Club newClub = new Club(nom, localisation, image, typeActivite, idClubOwnerId, telephone, description, prix, longitude, latitude);

        // Ajout du nouveau club à la base de données à l'aide de ServiceClub
        ServiceClub serviceClub = new ServiceClub();
        serviceClub.ajouter(newClub);
        clubsdata = FXCollections.observableList(serviceClub.afficher());
        tableViewClub.setItems(clubsdata);

        // Affichage d'un message de confirmation à l'utilisateur
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Club ajouté");
        alert.setHeaderText(null);
        alert.setContentText("Le club a été ajouté avec succès !");
        alert.showAndWait();
    }

@FXML
    void modifier(ActionEvent event) {
        // Vérification qu'un club est bien sélectionné dans la TableView
        Club clubSelectionne = tableViewClub.getSelectionModel().getSelectedItem();
        if (clubSelectionne == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Aucun club sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un club à modifier.");
            alert.showAndWait();
            return;
        }

        // Récupération des nouvelles valeurs des champs de texte
        String nom = nomField1.getText();
        String localisation = localisationField1.getText();
        String image = imagePathModif; // récupérer l'image à partir de l'upload précédent
        String typeActivite = typeActiviteField1.getText();
        int idClubOwnerId = connectedUser.getId(); // Méthode à implémenter pour récupérer l'id de l'utilisateur courant
        String telephone = telephoneField1.getText();
        String description = descriptionField1.getText();
        String prix = prixField1.getText();
        double longitude = 0;
        double latitude = 0;

        // Création du club modifié avec les nouvelles valeurs
        Club clubModifie = new Club(clubSelectionne.getId(), nom, localisation, image, typeActivite, idClubOwnerId, telephone, description, prix, longitude, latitude);

        // Modification du club dans la base de données à l'aide de ServiceClub
        ServiceClub serviceClub = new ServiceClub();
        serviceClub.modifier(clubModifie);
        clubsdata = FXCollections.observableList(serviceClub.afficher());
        tableViewClub.setItems(clubsdata);

        // Affichage d'un message de confirmation à l'utilisateur
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Club modifié");
        alert.setHeaderText(null);
        alert.setContentText("Le club a été modifié avec succès !");
        alert.showAndWait();
    }



    @FXML
    void supprimer(ActionEvent event) {
        // Récupération du club sélectionné dans la table view
        Club selectedClub = tableViewClub.getSelectionModel().getSelectedItem();

        if (selectedClub == null) {
            // Aucun club sélectionné, affichage d'un message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de suppression");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un club à supprimer !");
            alert.showAndWait();
            return;
        }

        // Confirmation de la suppression
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr de vouloir supprimer le club " + selectedClub.getNom() + " ?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Suppression du club de la base de données à l'aide de ServiceClub
            ServiceClub serviceClub = new ServiceClub();
            serviceClub.supprimer(selectedClub);

            // Mise à jour de la table view
            clubsdata = FXCollections.observableList(serviceClub.afficher());
            tableViewClub.setItems(clubsdata);

            // Affichage d'un message de confirmation à l'utilisateur
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Club supprimé");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Le club a été supprimé avec succès !");
            successAlert.showAndWait();
        }
    }

}
