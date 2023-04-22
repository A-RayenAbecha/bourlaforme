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
import java.util.Optional;
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
    private Label noteText;

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

     User user;

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
                
                reservationService.incr_annulation(reservation);
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
        this.user = user;
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
        int note = reservationService.getNoteByUserAndSeance(user.getId(), seance.getId());
        if(note==-1){
            noteText.setText("notez :");
            rating.setRating(0);
        }else {
            rating.setRating(note);
            noteText.setText("modifier votre note ("+note+") ");
        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


   
      @FXML
    void noter(MouseEvent event) {
     System.out.println(rating.getRating()); 
     ReservationService reservationService = new ReservationService();
     reservationService.ajouterRating(user.getId(),reservation.getSeance().getId(),(int) rating.getRating());
      noteText.setText("modifier votre note ("+rating.getRating()+") ");
             }

@FXML
void cancelRating(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirmation de suppression");
    alert.setHeaderText("Êtes-vous sûr(e) de vouloir supprimer cette note ?");
    alert.setContentText("Cette action est irréversible !");
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        ReservationService reservationService = new ReservationService();
        reservationService.deleteRating(user.getId(), reservation.getSeance().getId());
        noteText.setText("notez :");
        rating.setRating(0);
    }
}


}
