package com.esprit.gui;

import com.esprit.entities.Reservation;
import com.esprit.entities.Seance;
import com.esprit.entities.User;
import com.esprit.services.ReservationService;
import com.esprit.services.ServiceSeance;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.controlsfx.control.Rating;

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
     @FXML
    private Rating rating;


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
                reservationService.incr_annulation(reservation);
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

    private void showRatingDialog() {
    // Create a rating dialog
    Dialog<Integer> ratingDialog = new Dialog<>();
    ratingDialog.setTitle("Noter cette séance");
    ratingDialog.setHeaderText("Veuillez entrer une note de 1 à 5");

    // Create the components of the dialog
    Label noteLabel = new Label("Note :");
    Rating rating = new Rating();
    VBox content = new VBox(noteLabel, rating);
    ratingDialog.getDialogPane().setContent(content);

    // Add OK and Cancel buttons to the dialog
    ratingDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    // Set the result converter to retrieve the selected rating value
    ratingDialog.setResultConverter(new Callback<ButtonType, Integer>() {
        @Override
        public Integer call(ButtonType buttonType) {
            if (buttonType == ButtonType.OK) {
                int note = (int) rating.getRating();
                if (note == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "La note doit être supérieure à 0 !");
                    alert.showAndWait();
                    return null;
                }
                return note;
            } else {
                return null;
            }
        }
    });

    // Show the dialog and retrieve the result
    ratingDialog.showAndWait().ifPresent(note -> {
        // Save the rating to the database or do something else with the rating here
        System.out.println("Note enregistrée : " + note);
    });
}

    @FXML
    void notation(ActionEvent event) {
        showRatingDialog();
    }
      @FXML
    void noter(MouseEvent event) {
     System.out.println(rating.getRating()); 
     ServiceSeance S= new ServiceSeance();
     S.modifierrating(this.reservation.getSeance(), (int) rating.getRating());    }



}
