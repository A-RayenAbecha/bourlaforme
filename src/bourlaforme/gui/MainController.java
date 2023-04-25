package bourlaforme.gui;

import bourlaforme.entities.*;
import bourlaforme.tests.HelloApplication;
import bourlaforme.services.ServiceClub;
import bourlaforme.services.ServiceParticipation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

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
    private TextField searchField;

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
    private StackPane stackpane;

  
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
        ServiceParticipation serviceParticipation = new ServiceParticipation();

        displayParticipations(serviceParticipation.afficheReservationByUser(connectedUser));

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
        ServiceClub serviceClub = new ServiceClub();
        try {
            displayClubs(serviceClub.afficher());
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
           ServiceClub serviceClub = new ServiceClub();
        connectedUser = new User(2);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
    // Code to execute when the text in the search field changes
    // For example, call the search method with the new search term
        //    List<Club> results = search(newValue);
        if(page_seances.isVisible())
        try {
            displayClubs(serviceClub.search(newValue));
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        });
        
                page_reservation.setVisible(false);
        page_seances.setVisible(false);


    }



    void displayClubs(List<Club> clubs) throws IOException, SQLException {
        grid_sessions.getChildren().clear();
        int col=0 , row = 1;
        for (int i=0;i<clubs.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(HelloApplication.class.getResource("../gui/club.fxml"));
            HBox seanceBox = fxmlLoader.load();
            ClubController clubController  = fxmlLoader.getController();
            clubController.setData(clubs.get(i),connectedUser);
            grid_sessions.add(seanceBox,col,row);
            col++;
            if (col==3){
                grid_sessions.addRow(++row);
                col=0;
            }
        }
    }

    void displayParticipations(List<Participation> participations) throws IOException, SQLException {
       // grid_reservations.getChildren().clear();
        int col=0 , row = 1;
        for (int i=0;i<participations.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(HelloApplication.class.getResource("../gui/participation.fxml"));
            VBox seanceBox = fxmlLoader.load();
            ParticipationController participationController = fxmlLoader.getController();
            participationController.setData(participations.get(i),connectedUser);
            grid_reservations.add(seanceBox,col,row);
            col++;
            if (col==4){
                grid_reservations.addRow(++row);
                col=0;
            }
        }
    }
}
