package com.esprit.gui;

import com.esprit.entities.Reservation;
import com.esprit.entities.Seance;
import com.esprit.entities.User;
import com.esprit.services.ReservationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SeanceController implements Initializable {

    @FXML
    private Label coach;

    @FXML
    private Label description;

    @FXML
    private Label nbr_grp;

    @FXML
    private Label nbr_reservation;

    @FXML
    private Label nbr_seance;

    @FXML
    private VBox ox;

    @FXML
    private Button reserver;

    @FXML
    private Label titre;

    User user;

    Seance seance;

    @FXML
    void reserver(ActionEvent event) {
        // Vérifier que l'utilisateur est connecté
        ReservationService reservationService = new ReservationService();
        if (user == null) {
            System.out.println("Vous devez être connecté pour effectuer une réservation.");
            return;
        }
        // Vérifier si une réservation existe déjà pour cette séance et cet utilisateur
        if (reservationService.existeReservation(seance.getId(), user.getId())) {
            System.out.println("Vous avez déjà réservé cette séance.");
            return;
        }
        // Ajouter une nouvelle réservation
        Reservation reservation = new Reservation();
        reservation.setUser_id(user.getId());
        reservation.setSeance(seance);
        reservation.setDate(LocalDate.now());
        reservationService.ajouter(reservation);
        // Afficher une alerte de succès
        reserver.setDisable(true);
        reserver.setText("groupe complet");
         reserver.prefWidthProperty().bind(reserver.textProperty().length().multiply(7));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Réservation ajoutée");
        alert.setHeaderText(null);
        alert.setContentText("Votre réservation a été ajoutée avec succès !");
        alert.showAndWait();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(Seance seance, User user) throws SQLException {
        ReservationService reservationService = new ReservationService();
        int nbr_reser = reservationService.getNombreReservations(seance.getId());
        this.seance = seance;
        this.user = user;
        nbr_reservation.setText(String.valueOf(nbr_reser));
        titre.setText(seance.getTitre());
        description.setText(seance.getDescription());
        nbr_grp.setText(String.valueOf(seance.getNbr_grp()));
        nbr_seance.setText(String.valueOf(seance.getNbr_seance()));
        coach.setText(seance.getUser().getPrenom()+" "+seance.getUser().getNom());
        ox.setStyle("-fx-background-radius: 20;" +
                "-fx-effect:dropShadow(three-pass-box, rgba(0,0,0,0),10,0,0,10);" +
                "-fx-background-color: #fff;"
                );
        // Vérification de l'existence d'une réservation pour cette séance et cet utilisateur
        if (reservationService.existeReservation(seance.getId(), user.getId())) {
            reserver.setDisable(true);
            reserver.setText("vous avez deja reservé");
            reserver.prefWidthProperty().bind(reserver.textProperty().length().multiply(7));

        } else if (nbr_reser==seance.getNbr_grp()) {
            reserver.setDisable(true);
            reserver.setText("groupe complet");
        }


    }
}
