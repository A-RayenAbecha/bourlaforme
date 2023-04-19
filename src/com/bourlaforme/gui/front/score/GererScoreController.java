package com.bourlaforme.gui.front.score;


import com.bourlaforme.MainApp;
import com.bourlaforme.entities.Score;
import com.bourlaforme.entities.User;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.services.MessageService;
import com.bourlaforme.services.ScoreService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GererScoreController implements Initializable {

    @FXML
    public ComboBox<User> coachCB;
    @FXML
    public TextField noteTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Score currentScore;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (User user : MessageService.getInstance().getAllUsers()) {
            if (user.getRoles().equals("[\"ROLE_COACH\"]")) {
                coachCB.getItems().add(user);
            }
        }

        currentScore = AfficherToutScoreController.currentScore;

        if (currentScore != null) {
            topText.setText("Modifier score");
            btnAjout.setText("Modifier");

            try {
                coachCB.setValue(currentScore.getUser());
                noteTF.setText(String.valueOf(currentScore.getNote()));

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter score");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            Score score = new Score(
                    coachCB.getValue(),
                    MainApp.session,
                    Integer.parseInt(noteTF.getText())
            );

            if (currentScore == null) {
                if (ScoreService.getInstance().add(score)) {
                    AlertUtils.makeSuccessNotification("Score ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_SCORE);
                } else {
                    AlertUtils.makeError("score erreur");
                }
            } else {
                score.setId(currentScore.getId());
                if (ScoreService.getInstance().edit(score)) {
                    AlertUtils.makeSuccessNotification("Score modifié avec succés");
                    AfficherToutScoreController.currentScore = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_SCORE);
                } else {
                    AlertUtils.makeError("score erreur");
                }
            }

        }
    }


    private boolean controleDeSaisie() {

        if (coachCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir coach");
            return false;
        }

        if (noteTF.getText().isEmpty()) {
            AlertUtils.makeInformation("note ne doit pas etre vide");
            return false;
        }

        try {
            Integer.parseInt(noteTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("note doit etre un nombre");
            return false;
        }

        try {
            int i = Integer.parseInt(noteTF.getText());
            if (i < 0 || i > 5) {
                AlertUtils.makeInformation("note doit etre entre 0 et 5");
                return false;
            }
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("note doit etre un nombre");
            return false;
        }

        return true;
    }
}