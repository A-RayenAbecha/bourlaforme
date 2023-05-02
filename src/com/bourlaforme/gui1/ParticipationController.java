package com.bourlaforme.gui1;

import com.bourlaforme.entities.Club;
import com.bourlaforme.entities.Participation;
import com.bourlaforme.entities.User;
import com.bourlaforme.services.ServiceClub;
import com.bourlaforme.services.ServiceParticipation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;

public class ParticipationController {

    @FXML
    private Label daf;

    @FXML
    private Label dated;

    @FXML
    private Label description;

    @FXML
    private ImageView imageClub;

    @FXML
    private Label nom;

    @FXML
    private HBox ox;

    @FXML
    private VBox ox1;

    @FXML
    private Button participerBtn;

    @FXML
    private Label prixClub;

    @FXML
    private Label type;
    Club club;
    User user;
    Participation participation;
    
        @FXML
    private Label etat;

    @FXML
    void participer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr(e) de vouloir annuler cette participation ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        ServiceParticipation participationService = new ServiceParticipation();
        if (alert.getResult() == ButtonType.YES) {
            if (participation != null) {
                participationService.supprimer(participation);
                participerBtn.setDisable(true);
                participerBtn.setText("Participation annulée");
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Participation annulée avec succès !");
                successAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Veuillez sélectionner une participation à annuler.");
                errorAlert.showAndWait();
            }
        }
    }

    public void setData(Participation participation, User user) {
        this.participation = participation;
        ServiceParticipation serviceParticipation = new ServiceParticipation();
        ServiceClub serviceClub = new ServiceClub();
        this.club = serviceClub.getClubById(participation.getIdClub());
        this.user = user;
        nom.setText(club.getNom());
        type.setText(club.getTypeActivite());
        description.setText(club.getDescription());
        prixClub.setText(club.getPrix() + " DT");
        dated.setText(String.valueOf(participation.getDateDebut()));
        daf.setText(String.valueOf(participation.getDateFin()));
        if(participation.isParticipated()) {
            etat.setText("accepté");
        } else {
              etat.setText("en attente");
        }

        // set image du club
        File file = new File(club.getImage());
        Image image = new Image(file.toURI().toString());

        imageClub.setImage(image);
        // vérification de l'appartenance de l'utilisateur au club

        // customisation de la VBox ox
        ox1.setStyle("-fx-background-radius: 20; " +
                "-fx-effect: dropShadow(three-pass-box, rgba(0, 0, 0, 0.14), 10, 0, 0, 10); " +
                "-fx-background-color: #fff;");
    }

}
