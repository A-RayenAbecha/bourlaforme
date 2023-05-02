package com.bourlaforme.gui.back.score;

import com.bourlaforme.entities.Score;
import com.bourlaforme.services.ScoreService;
import com.bourlaforme.utils.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AfficherToutScoreController implements Initializable {

    @FXML
    public Text topText;
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
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_SCORE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#userText")).setText("User : " + score.getUser());
            ((Text) innerContainer.lookup("#coachText")).setText("Coach : " + score.getCoach());
            ((Text) innerContainer.lookup("#noteText")).setText("Note : " + score.getNote());

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void search(KeyEvent ignored) {
        displayData(searchTF.getText());
    }
}
