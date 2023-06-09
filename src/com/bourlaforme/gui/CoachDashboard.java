package com.bourlaforme.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import com.bourlaforme.entities.Seance;
import com.bourlaforme.entities.User;
import com.bourlaforme.services.ReservationService;
import java.util.regex.*;
import tray.animations.*;
import tray.notification.*;
import tray.notification.TrayNotification;



import com.bourlaforme.services.ServiceSeance;
import com.salesboxai.zoom.ZoomAPI;
import com.salesboxai.zoom.ZoomAPIException;
import com.salesboxai.zoom.ZoomAuthorizerJWT;
import com.salesboxai.zoom.ZoomMeeting;
import com.salesboxai.zoom.ZoomMeetingRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class CoachDashboard implements Initializable {

    @FXML
    private Button ajouterBtn;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField descriptionField2;

    @FXML
    private TableColumn<?, ?> descripttion;

    @FXML
    private TableColumn<?, ?> groupeColumn;



    @FXML
    private Spinner<Integer> nbr_grpField;

    @FXML
    private Spinner<Integer> nbr_grpField2;

    @FXML
    private Spinner<Integer> nbr_seancesField;

    @FXML
    private Spinner<Integer> nbr_seancesField2;

    @FXML
    private TableColumn<?, ?> seancesColumn;

    @FXML
    private TableView<Seance> tableViewSeance;

    @FXML
    private TextField titreField;

    @FXML
    private TextField titreField2;

    @FXML
    private TableColumn<?, ?> titrecolumn;
    
    private ObservableList<Seance> seancesdata;
    
        @FXML
    private TableColumn<?, ?> noteColumn;

    @FXML
    private TableColumn<?, ?> reservationsColumn;
    
      @FXML
    private Button btnInviter;
    @FXML
    private Button btnLogout;

    @FXML
void ajouter(ActionEvent event) {
    // Récupération des valeurs des champs de texte
    String description = descriptionField.getText();
    int nbr_grp = nbr_grpField.getValue();
    int nbr_seance = nbr_seancesField.getValue();
    String titre = titreField.getText();

    // Vérification des champs de texte pour empêcher les caractères spéciaux
    Pattern pattern = Pattern.compile("^[a-zA-Z0-9_ ]*$");
    Matcher matcherDescription = pattern.matcher(description);
    Matcher matcherTitre = pattern.matcher(titre);
    if(!matcherDescription.matches() || !matcherTitre.matches()){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("La description et le titre ne doivent pas contenir de caractères spéciaux !");
        alert.showAndWait();
        return;
    }
    // Vérification que les champs ne sont pas vides
    if (description.isEmpty() || titre.isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Les champs Titre et Description ne peuvent pas être vides !");
        alert.showAndWait();
        return; // Sortie de la méthode si les champs sont vides
    }


    // Création d'une nouvelle séance avec les valeurs des champs de texte et l'utilisateur courant
    User currentUser = User.connectedUser;// Méthode à implémenter pour récupérer l'utilisateur courant
    Seance newSeance = new Seance(nbr_grp, description, nbr_seance, titre, currentUser);

    // Ajout de la nouvelle séance à la base de données à l'aide de ServiceSeance
    ServiceSeance serviceSeance = new ServiceSeance();
    serviceSeance.ajouter(newSeance);
    seancesdata = FXCollections.observableList(serviceSeance.afficher());
            tableViewSeance.setItems(seancesdata);


    // Affichage d'un message de confirmation à l'utilisateur
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Séance ajoutée");
    alert.setHeaderText(null);
    alert.setContentText("La séance a été ajoutée avec succès !");
    alert.showAndWait();
}



 public void modifier(ActionEvent event) {
    // Récupération de la séance sélectionnée dans la liste de séances
    Seance seanceSelectionnee = tableViewSeance.getSelectionModel().getSelectedItem();
    if (seanceSelectionnee == null) {
        // Si aucune séance n'est sélectionnée, on affiche un message d'erreur
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur de sélection");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une séance à modifier !");
        alert.showAndWait();
        return;
    }

    // Récupération des valeurs des champs de texte
    String description = descriptionField2.getText();
    int nbr_grp = nbr_grpField2.getValue();
    int nbr_seance = nbr_seancesField2.getValue();
    String titre = titreField2.getText();

    // Vérification des champs de texte pour empêcher les caractères spéciaux
    Pattern pattern = Pattern.compile("^[a-zA-Z0-9_ ]*$");
    Matcher matcherDescription = pattern.matcher(description);
    Matcher matcherTitre = pattern.matcher(titre);
    if (!matcherDescription.matches() || !matcherTitre.matches()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText("La description et le titre ne doivent pas contenir de caractères spéciaux !");
        alert.showAndWait();
        return;
    }
   // Vérification que les champs ne sont pas vides
    if (description.isEmpty() || titre.isEmpty()) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Les champs Titre et Description ne peuvent pas être vides !");
        alert.showAndWait();
        return; // Sortie de la méthode si les champs sont vides
    }


    // Modification de la séance sélectionnée avec les nouvelles valeurs des champs de texte
    seanceSelectionnee.setDescription(description);
    seanceSelectionnee.setNbr_grp(nbr_grp);
    seanceSelectionnee.setNbr_seance(nbr_seance);
    seanceSelectionnee.setTitre(titre);

    // Mise à jour de la séance dans la base de données à l'aide de ServiceSeance
    ServiceSeance serviceSeance = new ServiceSeance();
    serviceSeance.modifier(seanceSelectionnee);
    seancesdata = FXCollections.observableList(serviceSeance.afficher());
            tableViewSeance.setItems(seancesdata);


    // Affichage d'un message de confirmation à l'utilisateur
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Séance modifiée");
    alert.setHeaderText(null);
    alert.setContentText("La séance a été modifiée avec succès !");
    alert.showAndWait();
}


    @FXML
void selection(MouseEvent event) {
    // Récupération de la séance sélectionnée dans le TableView
    Seance seanceSelectionnee = tableViewSeance.getSelectionModel().getSelectedItem();
    if (seanceSelectionnee == null) {
        // Si aucune séance n'est sélectionnée, on ne fait rien
        return;
    }

    // Remplissage des champs de texte avec les valeurs de la séance sélectionnée
    descriptionField2.setText(seanceSelectionnee.getDescription());
    nbr_grpField2.getValueFactory().setValue(seanceSelectionnee.getNbr_grp());
   nbr_seancesField2.getValueFactory().setValue(seanceSelectionnee.getNbr_seance());
    titreField2.setText(seanceSelectionnee.getTitre());
}


    @FXML
void supprimer(ActionEvent event) {
    // Get the selected item from the TableView
    Seance selectedSeance = tableViewSeance.getSelectionModel().getSelectedItem();

    if (selectedSeance != null) {
        // Remove the selected item from the data source
        ServiceSeance serviceSeance = new ServiceSeance();
        serviceSeance.supprimer(selectedSeance); // assuming that the service method takes the ID of the Seance to delete
            seancesdata = FXCollections.observableList(serviceSeance.afficher());
            tableViewSeance.setItems(seancesdata);


        // Show a confirmation message to the user
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Séance supprimée");
        alert.setHeaderText(null);
        alert.setContentText("La séance a été supprimée avec succès !");
        alert.showAndWait();
    } else {
        // Show an error message if no item was selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur de suppression");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une séance à supprimer.");
        alert.showAndWait();
    }
}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
     //   sendmail() ;
           ServiceSeance serviceSeance = new ServiceSeance();
           nbr_grpField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 30, 5));
           nbr_grpField2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 30, 5));
           nbr_seancesField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 30, 5));
           nbr_seancesField2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 30, 5));
                 //  spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));

           
            seancesdata = FXCollections.observableList(serviceSeance.afficher());
            tableViewSeance.setItems(seancesdata);

           
            titrecolumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
            descripttion.setCellValueFactory(new PropertyValueFactory<>("description"));
            seancesColumn.setCellValueFactory(new PropertyValueFactory<>("nbr_seance"));
            groupeColumn.setCellValueFactory(new PropertyValueFactory<>("nbr_grp"));
            noteColumn.setCellValueFactory(new PropertyValueFactory<>("avg_rating"));
            reservationsColumn.setCellValueFactory(new PropertyValueFactory<>("total_reservations"));
            
      /*      TrayNotification tray = new Notification("titre","ze", 5);
tray.setTitle("Title of notification");
tray.setMessage("Message of notification");
tray.setNotificationType(NotificationType.INFORMATION);
tray.setAnimationType(AnimationType.POPUP);
tray.showAndDismiss(Duration.seconds(5));*/
        ReservationService reservationService = new  ReservationService();
        
        TrayNotification tray = new TrayNotification();
        
        int nbr_annulation =  reservationService.getNbrIncrementation(User.connectedUser.getId());
        
        String title = "Réservations annulées";
        String message;
        switch (nbr_annulation) {
            case 0:
                message = "Aucune réservation n'a été annulée.";
                break;
            case 1:
                message = "1 réservation a été annulée.";
                break;
            default:
                message = nbr_annulation + " réservations ont été annulées.";
                break;
        }
        reservationService.resetNbrAnnulation(User.connectedUser);


        tray.setTitle(title);
        tray.setMessage(message);
        tray.setNotificationType(NotificationType.NOTICE);
        tray.setAnimationType(AnimationType.SLIDE);
        tray.showAndWait();

            

    }
    
    
    @FXML
    void inviter(ActionEvent event) {
        Seance selectedSeance = tableViewSeance.getSelectionModel().getSelectedItem();

    if (selectedSeance != null) {
            ServiceSeance serviceSeance = new ServiceSeance();
            ArrayList<String> recipients;
            recipients= serviceSeance.getEmailsForSeance(selectedSeance.getId());
            try {
                sendmail(recipients);
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("envoie");
                alert.setHeaderText(null);
                alert.setContentText("mails envoyés.");
                alert.showAndWait();
            } catch (MessagingException ex) {
                Logger.getLogger(CoachDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
    
            } else {
        // Show an error message if no item was selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur d'envoi");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez sélectionner une séance à alerter.");
        alert.showAndWait();
    }
   
    }
    
    
public void sendmail(ArrayList<String> recipients) throws MessagingException {
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

    // Create an array of InternetAddress objects from the ArrayList of email addresses
    InternetAddress[] addressArray = new InternetAddress[recipients.size()];
    for (int i = 0; i < recipients.size(); i++) {
        addressArray[i] = new InternetAddress(recipients.get(i));
    }

    message.setRecipients(Message.RecipientType.TO, addressArray);
    message.setSubject("zoom meet");
        try {
            message.setText("zoom link" + scheduleMeetingTest());
            //  ZoomAPI api;
        } catch (ZoomAPIException ex) {
            Logger.getLogger(CoachDashboard.class.getName()).log(Level.SEVERE, null, ex);
        }

    Transport.send(message);
}



/*
public String generateGoogleMeetLink(Seance seance) throws IOException, GeneralSecurityException {
    String meetingLink = null;
    String calendarId = "primary"; // Replace with your calendar ID
    DateTime startDateTime = new DateTime(seance.getDateDebut().getTime());
    DateTime endDateTime = new DateTime(seance.getDateFin().getTime());

    // Build the credentials object
    GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("path/to/credentials.json"))
            .createScoped(Collections.singleton(CalendarScopes.CALENDAR));

    // Build the calendar client
    Calendar calendar = new Calendar.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            new JacksonFactory(),
            new HttpCredentialsAdapter(credentials))
            .setApplicationName("My Application")
            .build();

    // Create the event
    Event event = new Event()
            .setSummary(seance.getTitre())
            .setDescription(seance.getDescription())
            .setStart(new EventDateTime().setDateTime(startDateTime))
            .setEnd(new EventDateTime().setDateTime(endDateTime));

    // Generate the Google Meet link and add it to the event
    ConferenceSolutionKey conferenceSolutionKey = new ConferenceSolutionKey()
            .setType("hangoutsMeet");
    CreateConferenceRequest createConferenceRequest = new CreateConferenceRequest()
            .setRequestId(UUID.randomUUID().toString())
            .setConferenceSolutionKey(conferenceSolutionKey);
    ConferenceData conferenceData = new ConferenceData()
            .setCreateRequest(createConferenceRequest);
    event.setConferenceData(conferenceData);

    // Insert the event and get the Google Meet link
    event = calendar.events().insert(calendarId, event).setConferenceDataVersion(1).execute();
    ConferenceData conferenceDataResponse = event.getConferenceData();
    if (conferenceDataResponse != null) {
        CreateConferenceResponse createConferenceResponse = conferenceDataResponse.getCreateRequest().getConferenceSolution().getEntryPoints().get(0).getUri();
        meetingLink = createConferenceResponse;
    }

    return meetingLink;
}*/


    static String scheduleMeetingTest() throws ZoomAPIException {
                    ZoomAuthorizerJWT authorizer = new ZoomAuthorizerJWT("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOm51bGwsImlzcyI6InJ4LThqanpsUUxXLWRqWldhQnFLX1EiLCJleHAiOjE2ODI3ODExMTcsImlhdCI6MTY4MjE3NjMxOH0.bmdxG2xP3FGuUGCxNev7CHdOHVJBG9PzaYZKBKBIRxk");
                    ZoomAPI za = new ZoomAPI(authorizer);
                    ZoomMeetingRequest mreq = ZoomMeetingRequest.requestDefaults("Test Meeting", "Let's talk about the weather");
                    ZoomMeeting meeting = za.createMeeting("me", mreq);
                    System.out.println(meeting);
            String join_url = meeting.join_url;
                    return join_url;
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
