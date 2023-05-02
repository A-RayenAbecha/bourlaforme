package bourlaforme.gui;

import bourlaforme.entities.Club;
import bourlaforme.entities.User;
import bourlaforme.entities.Participation;
import bourlaforme.services.ServiceClub;
import bourlaforme.services.ServiceParticipation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.control.Alert;

public class ClubController {

    Club club;
    User user;

    @FXML
    private Label description;

    @FXML
    private ImageView imageClub;

    @FXML
    private Label nom;

    @FXML
    private HBox ox;

    @FXML
    private Button participerBtn;

    @FXML
    private Label prixClub;

    @FXML
    private Label type;

    @FXML
    void participer(ActionEvent event) {
            // Vérifier que l'utilisateur est connecté
            ServiceParticipation participationService = new ServiceParticipation();
            if (user == null) {
                System.out.println("Vous devez être connecté pour participer à un club.");
                return;
            }
            // Vérifier si l'utilisateur participe déjà à ce club
            if (participationService.participationExiste(club, user)) {
                System.out.println("Vous participez déjà à ce club.");
                return;
            }
            // Ajouter une nouvelle participation
            Participation participation = new Participation();
            participation.setIdClub(club.getId());
            participation.setIdUser(user.getId());
            participation.setDateDebut(new Date());
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 1);
            participation.setDateFin(cal.getTime());
            participation.setParticipated(false);
            participationService.ajouter(participation);
            // Afficher une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Participation ajoutée");
            alert.setHeaderText(null);
            alert.setContentText("Votre participation a été ajoutée avec succès !");
            alert.showAndWait();
        participerBtn.setDisable(true);
        participerBtn.setText("Vous êtes déjà membre de ce club");


    }

    public void setData(Club club, User user) {
        ServiceParticipation serviceParticipation = new ServiceParticipation();
        this.club = club;
        this.user = user;
        nom.setText(club.getNom());
        type.setText(club.getTypeActivite());
        description.setText(club.getDescription());
        prixClub.setText(club.getPrix() + " DT");

        // set image du club
        File file = new File(club.getImage());
        Image image = new Image(file.toURI().toString());

        imageClub.setImage(image);
        // vérification de l'appartenance de l'utilisateur au club
        if (serviceParticipation.participationExiste(club,user)) {
            participerBtn.setDisable(true);
            participerBtn.setText("Vous êtes déjà membre de ce club");
        }
        // customisation de la VBox ox
        ox.setStyle("-fx-background-radius: 20; " +
                "-fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.14), 10, 0, 0, 10); " +
                "-fx-background-color: #fff;");
    }


}
