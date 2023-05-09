package com.bourlaforme.gui1;

import com.bourlaforme.entities.Club;
import com.bourlaforme.entities.Participation;
import com.bourlaforme.entities.User;
import com.bourlaforme.services.ServiceClub;
import com.bourlaforme.services.ServiceParticipation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
//import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class CoachDashboard implements Initializable {

    @FXML
    private TableColumn<?, ?> activiteColumn;

    @FXML
    private Button ajouterBtn;

    @FXML
    private Button ajouterBtn1;


    

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
    
    private Club clubSelected;

    private ObservableList<Club> clubsdata;

    private String imagePathAjout;
    private String imagePathModif;

    @FXML
    private TableColumn<Participation, String> colAction;

 //   private TableColumn<?, ?> colClubId;

    @FXML
    private TableColumn<?, ?> colDateDebut;

    @FXML
    private TableColumn<?, ?> colDateFin;

    @FXML
    private TableColumn<?, ?> colParticipated;

    @FXML
    private Button btnLogout;

    @FXML
    private TableView<Participation> tableViewParticipations;

    private ObservableList<Participation> particips;

    @FXML
    void selection(MouseEvent event) {
        // Récupération de la ligne sélectionnée
        Club selectedClub = tableViewClub.getSelectionModel().getSelectedItem();

        // Vérification que la sélection n'est pas nulle
        if (selectedClub != null) {
             clubSelected = selectedClub;
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

            ServiceParticipation serviceParticipation = new ServiceParticipation();
            tableViewParticipations.setVisible(true);
            particips = FXCollections.observableList(serviceParticipation.getParticipationsByClub(selectedClub.getId()));
            tableViewParticipations.setItems(particips);
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
            // Get the selected file name
            String fileName = selectedFile.getName();

            // Set the image path with the new file name
            imagePathAjout = fileName;

            // Copy the selected file to the uploads folder
            Path sourcePath = Paths.get(selectedFile.getAbsolutePath());
            Path destinationPath = Paths.get("C:\\Users\\aziz3\\OneDrive\\Bureau\\projet\\Projet_Anarchy\\public\\uploads\\articles\\" + fileName);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            // Get the selected file name
            String fileName = selectedFile.getName();

            // Set the image path with the new file name
            imagePathAjout = fileName;

            // Copy the selected file to the uploads folder
            Path sourcePath = Paths.get(selectedFile.getAbsolutePath());
            Path destinationPath = Paths.get("C:\\Users\\aziz3\\OneDrive\\Bureau\\projet\\Projet_Anarchy\\public\\uploads\\articles\\" + fileName);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        localisationColumn.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        activiteColumn.setCellValueFactory(new PropertyValueFactory<>("typeActivite"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        telephoneColumn2.setCellValueFactory(new PropertyValueFactory<>("telephone"));

     //   colClubId.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        colDateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        colDateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        colParticipated.setCellValueFactory(new PropertyValueFactory<>("participated"));


        colAction.setCellFactory(column -> {
            return new TableCell<Participation, String>() {
                final Button deleteButton = new Button("Delete");
                final Button acceptButton = new Button("Accept");
                final Button declineButton = new Button("Decline");

                {
                    deleteButton.setOnAction(event -> {
                        Participation p = getTableView().getItems().get(getIndex());
                        // TODO: Implement delete action for the Participation object p
                        ServiceParticipation serviceParticipation = new ServiceParticipation();
                        try {
                            sendmail(serviceParticipation.getEmailForParticipation(p.getId()),"annulation de participation" , "participation annulé" );
                        } catch (MessagingException ex) {
                            Logger.getLogger(CoachDashboard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        serviceParticipation.supprimer(p);
                        tableViewParticipations.setVisible(true);
                        particips = FXCollections.observableList(serviceParticipation.getParticipationsByClub(clubSelected.getId()));
                        tableViewParticipations.setItems(particips);
                        

                    });

                    acceptButton.setOnAction(event -> {
                        Participation p = getTableView().getItems().get(getIndex());
                        // TODO: Implement accept action for the Participation object p
                        ServiceParticipation serviceParticipation = new ServiceParticipation();
                        serviceParticipation.accepter(p.getId());
                        tableViewParticipations.setVisible(true);
                        particips = FXCollections.observableList(serviceParticipation.getParticipationsByClub(clubSelected.getId()));
                        tableViewParticipations.setItems(particips);
                      ///////////////////////////////////////////  
                     String paymentLink = "";
                      //String paymentLink = "https://buy.stripe.com/test_00g5mKekGda13RK6oo";   


// stripe
Stripe.apiKey = "sk_test_51MijNgFdj6Z5ZOtZhwQv52Tzmw6QI3tzQY2LUFrLqc3Q4CVuaoh4WZXb0v4vAMe1xcoteWelWsYt27ay3x88KoNN00w7x4UGX7"; // replace with your API key

SessionCreateParams params =
  SessionCreateParams.builder()
    .addLineItem(
      SessionCreateParams.LineItem.builder()
        .setQuantity(1L)
        .setPriceData(
          SessionCreateParams.LineItem.PriceData.builder()
            .setCurrency("USD")
            .setUnitAmount(Long.valueOf(clubSelected.getPrix()) )
            .setProductData(
              SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(clubSelected.getNom()+" subscrbtion")
                .build())
            .build())
        .build())
    .setMode(SessionCreateParams.Mode.PAYMENT)
    .setSuccessUrl("https://example.com/success")
    .setCancelUrl("https://example.com/cancel")
    .build();

try {
  com.stripe.model.checkout.Session session = com.stripe.model.checkout.Session.create(params);
  paymentLink = session.getUrl();
  System.out.println(paymentLink);
} catch (StripeException e) {
  e.printStackTrace();
}

try {
                            sendmail(serviceParticipation.getEmailForParticipation(p.getId()),"proceed to payement" , paymentLink );
                        } catch (MessagingException ex) {
                            Logger.getLogger(CoachDashboard.class.getName()).log(Level.SEVERE, null, ex);
                        }


                    });
////////////////////////////////////////////////////////////
                    declineButton.setOnAction(event -> {
                        Participation p = getTableView().getItems().get(getIndex());
                        // TODO: Implement decline action for the Participation object p
                        ServiceParticipation serviceParticipation = new ServiceParticipation();
                        try {
                            sendmail(serviceParticipation.getEmailForParticipation(p.getId()),"reponse de participation" , "participation refusé" );
                        } catch (MessagingException ex) {
                            Logger.getLogger(CoachDashboard.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        serviceParticipation.supprimer(p);
                        tableViewParticipations.setVisible(true);
                        particips = FXCollections.observableList(serviceParticipation.getParticipationsByClub(clubSelected.getId()));
                        tableViewParticipations.setItems(particips);
                        
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (!empty) {
                        Participation p = getTableView().getItems().get(getIndex());
                        if (p.isParticipated() == true) {
                            setGraphic(deleteButton);
                        } else {
                            HBox hbox = new HBox();
                            hbox.setSpacing(5);
                            hbox.getChildren().addAll(acceptButton, declineButton);
                            setGraphic(hbox);
                        }
                    }
                }
            };
        });


        ServiceClub serviceClub = new ServiceClub();

        clubsdata = FXCollections.observableList(serviceClub.afficher());
        tableViewClub.setItems(clubsdata);
    }

    @FXML
    void ajouter(ActionEvent event) {
    String nom = nomField.getText();
    String localisation = localisationField.getText();
    String image = imagePathAjout; // récupérer l'image à partir de l'upload précédent
    String typeActivite = typeActiviteField.getText();
    int idClubOwnerId = User.connectedUser.getId(); // Méthode à implémenter pour récupérer l'id de l'utilisateur courant
    String telephone = telephoneField.getText();
    String description = descriptionField.getText();
    String prix = prixField.getText();
    double longitude = 0;
    double latitude = 0;

    // Vérifier si les champs obligatoires sont remplis
    if (nom.isEmpty() || localisation.isEmpty() || typeActivite.isEmpty() || telephone.isEmpty() || description.isEmpty() || prix.isEmpty()) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs obligatoires.");
        alert.showAndWait();
        return;
    }

    // Vérifier si les données sont valides
    if (!telephone.matches("\\d{10}")) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Le numéro de téléphone doit contenir 10 chiffres.");
        alert.showAndWait();
        return;
    }
    if (!prix.matches("\\d+(\\.\\d+)?")) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Le prix doit être un nombre.");
        alert.showAndWait();
        return;
    }

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
        int idClubOwnerId = User.connectedUser.getId(); // Méthode à implémenter pour récupérer l'id de l'utilisateur courant
        String telephone = telephoneField1.getText();
        String description = descriptionField1.getText();
        String prix = prixField1.getText();
        double longitude = 0;
        double latitude = 0;
        
        // Vérifier si les champs obligatoires sont remplis
    if (nom.isEmpty() || localisation.isEmpty() || typeActivite.isEmpty() || telephone.isEmpty() || description.isEmpty() || prix.isEmpty()) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez remplir tous les champs obligatoires.");
        alert.showAndWait();
        return;
    }

    // Vérifier si les données sont valides
    if (!telephone.matches("\\d{10}")) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Le numéro de téléphone doit contenir 10 chiffres.");
        alert.showAndWait();
        return;
    }
    if (!prix.matches("\\d+(\\.\\d+)?")) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("Le prix doit être un nombre.");
        alert.showAndWait();
        return;
    }

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
            tableViewParticipations.setVisible(false);

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
    
        public void sendmail(String email,String subject,String Body) throws MessagingException {
	String host = "smtp.gmail.com";
	String username = "coachkhalifa31@gmail.com";
	String password = "jzaisjegdwuldnmk";

	Properties props = new Properties();

        
        props.put("mail.smtp.user","username"); 
props.put("mail.smtp.host", "smtp.gmail.com"); 
props.put("mail.smtp.port", "25"); 
props.put("mail.debug", "true"); 
props.put("mail.smtp.auth", "true"); 
props.put("mail.smtp.starttls.enable","true"); 
props.put("mail.smtp.EnableSSL.enable","true");

props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
props.setProperty("mail.smtp.socketFactory.fallback", "false");   
props.setProperty("mail.smtp.port", "465");   
props.setProperty("mail.smtp.socketFactory.port", "465"); 

	Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

	Message message = new MimeMessage(session);
	message.setFrom(new InternetAddress("coachkhalifa31@gmail.com"));
	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
	message.setSubject(subject);
	message.setText(Body);

	Transport.send(message);
}
        
        public void logout(ActionEvent actionEvent) {
        btnLogout.setOnAction(event -> {
        // Clear the connectedUser variable
        User.connectedUser = null;

        // Load the login page FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bourlaforme/interfaces/LoginForm.fxml"));
        Parent loginPageParent;
        try {
            loginPageParent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create a new Stage for the login page and show it
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(loginPageParent));
        loginStage.show();

        // Hide the current window
        ((Node)(event.getSource())).getScene().getWindow().hide();
        });
    }

}
