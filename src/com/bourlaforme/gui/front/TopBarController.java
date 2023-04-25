package com.bourlaforme.gui.front;

import com.bourlaforme.MainApp;
import com.bourlaforme.utils.Animations;
import com.bourlaforme.utils.Constants;
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

public class TopBarController implements Initializable {

    private final Color COLOR_GRAY = new Color(0.9, 0.9, 0.9, 1);
    private final Color COLOR_PRIMARY = Color.web("#000000");
    private final Color COLOR_DARK = new Color(1, 1, 1, 0.65);
    private Button[] liens;

    @FXML
    private Button btnReclamations;

    @FXML
    private Button btnScores;

    @FXML
    private Button btnMessages;

    @FXML
    private AnchorPane mainComponent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        liens = new Button[]{
                btnReclamations,
                btnScores,
                btnMessages,
                btnMessages,
        };

        mainComponent.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }

        btnReclamations.setTextFill(COLOR_DARK);

        btnScores.setTextFill(COLOR_DARK);

        btnMessages.setTextFill(COLOR_DARK);

        btnMessages.setTextFill(COLOR_DARK);

    }

    @FXML
    private void afficherReclamations(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_DISPLAY_ALL_RECLAMATION);

        btnReclamations.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnReclamations, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherScores(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_DISPLAY_ALL_SCORE);

        btnScores.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnScores, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherMessages(ActionEvent event) {
               goToLink("/com/bourlaforme/gui/front/message/Messagerie.fxml");

    }

    private void goToLink(String link) {
        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            Animations.animateButton(lien, COLOR_GRAY, COLOR_DARK, COLOR_PRIMARY, 0, false);
        }
        MainWindowController.getInstance().loadInterface(link);
    }

    @FXML
    public void logout(ActionEvent ignored) {
        MainApp.getInstance().logout();
    }
}
