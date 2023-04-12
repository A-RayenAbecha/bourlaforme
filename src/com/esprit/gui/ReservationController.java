package com.esprit.gui;

import com.esprit.entities.Reservation;
import com.esprit.entities.Seance;
import com.esprit.entities.User;
import com.esprit.services.ReservationService;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class ReservationController implements Initializable {

    @FXML
    private Button annuler;

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
    private Label titre;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    Reservation reservation;

    @FXML
    void annulerReservation(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Êtes-vous sûr(e) de vouloir annuler cette réservation ?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        ReservationService reservationService = new ReservationService();
        if (alert.getResult() == ButtonType.YES) {
            if (reservation != null) {
                reservationService.supprimer(reservation);
                annuler.setDisable(true);
                annuler.setText("Réservation annulée");
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Réservation annulée avec succès !");
                successAlert.showAndWait();
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Veuillez sélectionner une réservation à annuler.");
                errorAlert.showAndWait();
            }
        }
    }


    public void setData(Reservation reservation, User user) throws SQLException {
        this.reservation = reservation;
        Seance seance = reservation.getSeance();
        ReservationService reservationService = new ReservationService();
        int nbr_reser = reservationService.getNombreReservations(seance.getId());

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



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
