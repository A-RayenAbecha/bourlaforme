package com.bourlaforme.gui.backRec;

import com.bourlaforme.MainApp;
import com.bourlaforme.entities.User;
import com.bourlaforme.utils.Animations;
import com.bourlaforme.utils.Constants;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SideBarController implements Initializable {

    private final Color COLOR_GRAY = new Color(0.9, 0.9, 0.9, 1);
    private final Color COLOR_PRIMARY = Color.web("#000000");
    private final Color COLOR_DARK = new Color(1, 1, 1, 0.65);
    private Button[] liens;

    @FXML
    private Button btnReclamations;
    @FXML
    private Button btnScores;
    @FXML
    private Button btnLogout;
    @FXML
    private AnchorPane mainComponent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        liens = new Button[]{
                btnReclamations,
                btnScores,
        };

        mainComponent.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }
        btnReclamations.setTextFill(Color.WHITE);
        btnScores.setTextFill(Color.WHITE);
    }

    @FXML
    private void afficherReclamations(ActionEvent ignored) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_RECLAMATION);

        btnReclamations.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnReclamations, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherScores(ActionEvent ignored) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_SCORE);

        btnScores.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnScores, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    private void goToLink(String link) {
        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            Animations.animateButton(lien, COLOR_GRAY, COLOR_DARK, COLOR_PRIMARY, 0, false);
        }
        MainWindowController.getInstance().loadInterface1(link);
    }

    @FXML
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
