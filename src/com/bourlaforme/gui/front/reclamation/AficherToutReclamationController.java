package com.bourlaforme.gui.front.reclamation;

import com.bourlaforme.MainApp;
import com.bourlaforme.entities.Reclamation;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.services.ReclamationService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AficherToutReclamationController implements Initializable {

    public static Reclamation currentReclamation;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public ComboBox<String> sortCB;

    List<Reclamation> listReclamation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listReclamation = ReclamationService.getInstance().getAll();
        sortCB.getItems().addAll("User", "Coach", "Club", "Article", "DateReclamation", "Etat", "Reponse", "Type", "Message");
        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listReclamation);

        if (!listReclamation.isEmpty()) {
            for (Reclamation reclamation : listReclamation) {
                if (reclamation.getUser().getId() == MainApp.session.getId()){
                    mainVBox.getChildren().add(makeReclamationModel(reclamation));
                }
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeReclamationModel(
            Reclamation reclamation
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_RECLAMATION)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));

            if (reclamation.getCoach() != null) {
                if (reclamation.getCoach().getEmail() != null) {
                    ((Text) innerContainer.lookup("#coachText")).setText("Coach : " + reclamation.getCoach().getEmail());
                }
            }
            if (reclamation.getClub() != null) {
                if (reclamation.getClub().getName() != null) {
                    ((Text) innerContainer.lookup("#clubText")).setText("Club : " + reclamation.getClub().getName());
                }
            }
            if (reclamation.getArticle() != null) {
                if (reclamation.getArticle().getName() != null) {
                    ((Text) innerContainer.lookup("#articleText")).setText("Article : " + reclamation.getArticle().getName());
                }
            }

            ((Text) innerContainer.lookup("#dateReclamationText")).setText("DateReclamation : " + reclamation.getDateReclamation());
            ((Text) innerContainer.lookup("#etatText")).setText("Etat : " + reclamation.getEtat());
            ((Text) innerContainer.lookup("#reponseText")).setText(
                    "Reponse : " + (reclamation.getReponse() == null ? "null" : reclamation.getReponse())
            );
            ((Text) innerContainer.lookup("#typeText")).setText("Type : " + reclamation.getType());
            ((Text) innerContainer.lookup("#messageText")).setText("Message : " + reclamation.getMessage());


            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerReclamation(reclamation));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterReclamation(ActionEvent ignored) {
        currentReclamation = null;
        com.bourlaforme.gui.front.MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_RECLAMATION);
    }

    private void supprimerReclamation(Reclamation reclamation) {
        currentReclamation = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer reclamation ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (ReclamationService.getInstance().delete(reclamation.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_RECLAMATION);
                } else {
                    AlertUtils.makeError("Could not delete reclamation");
                }
            }
        }
    }

    @FXML
    public void sort(ActionEvent ignored) {
        Constants.compareVar = sortCB.getValue();
        Collections.sort(listReclamation);
        displayData();
    }
}
