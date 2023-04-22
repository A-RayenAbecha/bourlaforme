package com.esprit.gui;

import com.esprit.entities.Reservation;
import com.esprit.entities.Seance;
import com.esprit.entities.User;
import com.esprit.hadil_jfx.HelloApplication;
import com.esprit.services.ReservationService;
import com.esprit.services.ServiceSeance;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
////

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private GridPane grid_reservations;

    @FXML
    private GridPane grid_sessions;

    @FXML
    private VBox page_reservation;

    @FXML
    private VBox page_seances;

    @FXML
    private ImageView CoachIcon;

    @FXML
    private ImageView AcceuilIcon;

    @FXML
    private ImageView SessionsIcon;



    @FXML
    private HBox coachs;

    @FXML
    private ImageView image;


    @FXML
    private HBox reservations;

    @FXML
    private ImageView reservationsIcon;

    @FXML
    private HBox sessions;

    @FXML
    private HBox Acceuil;

    User connectedUser;

  
    @FXML
    void coachsClicked(MouseEvent event) {
        // Set the background color of the coachs HBox to gray
        coachs.setStyle("-fx-background-color: #CCCCCC;");

        // Set the background color of the other HBoxes to white
        sessions.setStyle("-fx-background-color: #FFFFFF;");
        reservations.setStyle("-fx-background-color: #FFFFFF;");
        Acceuil.setStyle("-fx-background-color: #FFFFFF;");
    }



    @FXML
    void reservationsClicked(MouseEvent event) throws SQLException, IOException {
        // Set the background color of the reservations HBox to gray
        grid_reservations.getChildren().clear();
        reservations.setStyle("-fx-background-color: #CCCCCC;");

        // Set the background color of the other HBoxes to white
        coachs.setStyle("-fx-background-color: #FFFFFF;");
        sessions.setStyle("-fx-background-color: #FFFFFF;");
        Acceuil.setStyle("-fx-background-color: #FFFFFF;");
        ReservationService reservationService = new ReservationService();

        displayReservations(reservationService.getReservationsByUser(connectedUser));

        page_seances.setVisible(false);
        page_reservation.setVisible(true);
        page_reservation.toFront();
    }

    @FXML
    void sessionsClicked(MouseEvent event) {
        // Set the background color of the sessions HBox to gray
        sessions.setStyle("-fx-background-color: #CCCCCC;");

        // Set the background color of the other HBoxes to white
        coachs.setStyle("-fx-background-color: #FFFFFF;");
        reservations.setStyle("-fx-background-color: #FFFFFF;");
        Acceuil.setStyle("-fx-background-color: #FFFFFF;");
        ServiceSeance serviceSeance = new ServiceSeance();
        try {
            displaySessions(serviceSeance.afficher());
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

        page_reservation.setVisible(false);
        page_seances.setVisible(true);
        page_seances.toFront();


    }

    @FXML
    void acceuilClicked(MouseEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
   
        connectedUser = new User(4);
    }

    void displaySessions(List<Seance> seances) throws IOException, SQLException {
        grid_sessions.getChildren().clear();
        int col=0 , row = 1;
        for (int i=0;i<seances.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(HelloApplication.class.getResource("../gui/seance.fxml"));
            HBox seanceBox = fxmlLoader.load();
            SeanceController seanceController = fxmlLoader.getController();
            seanceController.setData(seances.get(i),connectedUser);
            grid_sessions.add(seanceBox,col,row);
            col++;
            if (col==4){
                ++row;
                col=0;
            }
        }
    }

    void displayReservations(List<Reservation> reservations) throws IOException, SQLException {
       // grid_reservations.getChildren().clear();
        int col=0 , row = 1;
        for (int i=0;i<reservations.size();i++){
            System.out.println(reservations.get(i));
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(HelloApplication.class.getResource("../gui/reservation.fxml"));
            VBox seanceBox = fxmlLoader.load();
            ReservationController reservationController = fxmlLoader.getController();
            reservationController.setData(reservations.get(i),connectedUser);
            grid_reservations.add(seanceBox,col,row);
            col++;
            if (col==4){
                ++row;
                col=0;
            }
        }
    }
}
