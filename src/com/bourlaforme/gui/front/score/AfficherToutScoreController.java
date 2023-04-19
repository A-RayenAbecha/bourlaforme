package com.bourlaforme.gui.front.score;

import com.bourlaforme.entities.Score;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.services.ScoreService;
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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AfficherToutScoreController implements Initializable {

    public static Score currentScore;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public TextField searchTF;

    List<Score> listScore;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listScore = ScoreService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listScore);

        if (!listScore.isEmpty()) {
            for (Score score : listScore) {
                if (String.valueOf(score.getNote()).toLowerCase().startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeScoreModel(score));
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

    public Parent makeScoreModel(
            Score score
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_SCORE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#coachText")).setText("Coach : " + score.getCoach());
            ((Text) innerContainer.lookup("#userText")).setText("User : " + score.getUser());
            ((Text) innerContainer.lookup("#noteText")).setText("Note : " + score.getNote());


            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierScore(score));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerScore(score));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterScore(ActionEvent ignored) {
        currentScore = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_SCORE);
    }

    private void modifierScore(Score score) {
        currentScore = score;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_SCORE);
    }

    private void supprimerScore(Score score) {
        currentScore = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer score ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (ScoreService.getInstance().delete(score.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_SCORE);
                } else {
                    AlertUtils.makeError("Could not delete score");
                }
            }
        }
    }


    @FXML
    private void search(KeyEvent ignored) {
        displayData(searchTF.getText());
    }
}
