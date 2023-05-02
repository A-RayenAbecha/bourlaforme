package com.bourlaforme.gui.front.commentaire;

import com.bourlaforme.entities.Commentaire;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.services.CommentaireService;
import com.bourlaforme.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
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

public class ShowAllController implements Initializable {

    public static Commentaire currentCommentaire;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;

    List<Commentaire> listCommentaire;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        if (com.bourlaforme.gui.front.article.ShowAllController.currentArticle != null) {
            listCommentaire = CommentaireService.getInstance().getByArticle(
                    com.bourlaforme.gui.front.article.ShowAllController.currentArticle.getId()
            );
        } else {
            listCommentaire = CommentaireService.getInstance().getAll();
        }

        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listCommentaire);

        if (!listCommentaire.isEmpty()) {
            for (Commentaire commentaire : listCommentaire) {
                mainVBox.getChildren().add(makeCommentaireModel(commentaire));
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeCommentaireModel(
            Commentaire commentaire
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_COMMENTAIRE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#articleText")).setText("Article : " + commentaire.getArticle());
            ((Text) innerContainer.lookup("#auteurText")).setText("Auteur : " + commentaire.getAuteur());
            ((Text) innerContainer.lookup("#contenuText")).setText("Contenu : " + commentaire.getContenu());
            ((Text) innerContainer.lookup("#dateText")).setText("Date : " + commentaire.getDate());


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterCommentaire(ActionEvent event) {
        currentCommentaire = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_COMMENTAIRE);
    }

}
