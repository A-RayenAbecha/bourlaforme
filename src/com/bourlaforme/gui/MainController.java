package com.bourlaforme.gui;

import com.bourlaforme.entities.Reservation;
import com.bourlaforme.entities.Seance;
import com.bourlaforme.entities.User;
import com.bourlaforme.hadil_jfx.HelloApplication;
import com.bourlaforme.services.ReservationService;
import com.bourlaforme.services.ServiceSeance;
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
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private ImageView AcceuilIcon;

    @FXML
    private ImageView SessionsIcon;

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
      @FXML
    private TextField searchField;

    
    @FXML
    private Button btnAcceuil;
    
    public void redirectAcceuil(Event e) throws IOException{
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("/com/bourlaforme/gui/front/MainWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

  
    @FXML
    void coachsClicked(MouseEvent event) {
        // Set the background color of the coachs HBox to gray

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
        sessions.setStyle("-fx-background-color: #FFFFFF;");
        Acceuil.setStyle("-fx-background-color: #FFFFFF;");
        ReservationService reservationService = new ReservationService();

        displayReservations(reservationService.getReservationsByUser(User.connectedUser));

        page_seances.setVisible(false);
        page_reservation.setVisible(true);
        page_reservation.toFront();
    }

    @FXML
    void sessionsClicked(MouseEvent event) {
        // Set the background color of the sessions HBox to gray
        sessions.setStyle("-fx-background-color: #CCCCCC;");

        // Set the background color of the other HBoxes to white
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
   
    }

    void displaySessions(List<Seance> seances) throws IOException, SQLException {
        grid_sessions.getChildren().clear();
        int col=0 , row = 1;
        for (int i=0;i<seances.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(HelloApplication.class.getResource("/com/bourlaforme/gui/seance.fxml"));
            HBox seanceBox = fxmlLoader.load();
            SeanceController seanceController = fxmlLoader.getController();
            seanceController.setData(seances.get(i),User.connectedUser);
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
            fxmlLoader.setLocation(HelloApplication.class.getResource("/com/bourlaforme/gui/reservation.fxml"));
            VBox seanceBox = fxmlLoader.load();
            ReservationController reservationController = fxmlLoader.getController();
            reservationController.setData(reservations.get(i),User.connectedUser);
            grid_reservations.add(seanceBox,col,row);
            col++;
            if (col==4){
                ++row;
                col=0;
            }
        }
    }
}
