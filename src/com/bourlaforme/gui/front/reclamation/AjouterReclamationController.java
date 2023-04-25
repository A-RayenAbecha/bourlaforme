package com.bourlaforme.gui.front.reclamation;


import com.bourlaforme.MainApp;
import com.bourlaforme.entities.Reclamation;
import com.bourlaforme.entities.User;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.services.MessageService;
import com.bourlaforme.services.ReclamationService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.BadWords;
import com.bourlaforme.utils.Constants;
import com.bourlaforme.utils.RelationObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AjouterReclamationController implements Initializable {

    @FXML
    public ComboBox<String> reclamerSurCB;
    @FXML
    public VBox coachVBox;
    @FXML
    public VBox clubVBox;
    @FXML
    public VBox articleVBox;
    @FXML
    public ComboBox<User> coachCB;
    @FXML
    public ComboBox<RelationObject> clubCB;
    @FXML
    public ComboBox<RelationObject> articleCB;
    @FXML
    public TextField messageTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        coachVBox.setVisible(false);
        clubVBox.setVisible(false);
        articleVBox.setVisible(false);

        reclamerSurCB.getItems().add("User");
        reclamerSurCB.getItems().add("Coach");
        reclamerSurCB.getItems().add("Club");
        reclamerSurCB.getItems().add("Article");

        reclamerSurCB.setOnAction(event -> {
            String selectedItem = reclamerSurCB.getValue();
            switch (selectedItem) {
                case "Coach":
                    coachVBox.setVisible(true);
                    clubVBox.setVisible(false);
                    articleVBox.setVisible(false);
                    break;
                case "Club":
                    coachVBox.setVisible(false);
                    clubVBox.setVisible(true);
                    articleVBox.setVisible(false);
                    break;
                case "Article":
                    coachVBox.setVisible(false);
                    clubVBox.setVisible(false);
                    articleVBox.setVisible(true);
                    break;
                default:
                    coachVBox.setVisible(false);
                    clubVBox.setVisible(false);
                    articleVBox.setVisible(false);
                    break;
            }
        });

        for (User user : MessageService.getInstance().getAllUsers()) {
            if (user.getRoles().equals("[\"ROLE_COACH\"]")) {
                coachCB.getItems().add(user);
            }
        }
        for (RelationObject user : ReclamationService.getInstance().getAllClubs()) {
            clubCB.getItems().add(user);
        }
        for (RelationObject user : ReclamationService.getInstance().getAllArticles()) {
            articleCB.getItems().add(user);
        }

        topText.setText("Ajouter reclamation");
        btnAjout.setText("Ajouter");
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            User coach = null;
            RelationObject club = null;
            RelationObject article = null;

            String type = "";

            switch (reclamerSurCB.getValue()) {
                case "Coach":
                    coach = coachCB.getValue();
                    type = "reclamation_coach";
                    break;
                case "Club":
                    club = clubCB.getValue();
                    type = "reclamation_club";
                    break;
                case "Article":
                    article = articleCB.getValue();
                    type = "reclamation_article";
                    break;
                default:
                    break;
            }

            Reclamation reclamation = new Reclamation(
                    MainApp.session,
                    coach,
                    club,
                    article,
                    LocalDate.now(),
                    "non traité",
                    "",
                    type,
                    messageTF.getText()
            );

            if (ReclamationService.getInstance().add(reclamation, reclamerSurCB.getValue())) {
                AlertUtils.makeSuccessNotification("Reclamation ajouté avec succés");
                MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_RECLAMATION);
            } else {
                AlertUtils.makeError("reclamation erreur");
            }
        }
    }

    private boolean controleDeSaisie() {

        if (reclamerSurCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir sur quoi reclamer");
            return false;
        }

        if (coachCB.getValue() == null && clubCB.getValue() == null && articleCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir coach ou club ou article");
            return false;
        }

        if (messageTF.getText().isEmpty()) {
            AlertUtils.makeInformation("message ne doit pas etre vide");
            return false;
        }

        if (BadWords.filterText(messageTF.getText())) {
            AlertUtils.makeInformation("Message contains bad words !");
            return false;
        }

        return true;
    }
}