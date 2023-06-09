package com.bourlaforme.gui.back.commentaire;

import com.bourlaforme.entities.Commentaire;
import com.bourlaforme.gui.back.MainWindowController;
import com.bourlaforme.services.CommentaireService;
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

public class ShowAllController implements Initializable {

    public static Commentaire currentCommentaire;

    @FXML
    public Text topText;
    @FXML
    public VBox mainVBox;
    @FXML
    public TextField searchTF;

    List<Commentaire> listCommentaire;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (com.bourlaforme.gui.back.article.ShowAllController.currentArticle != null) {
            listCommentaire = CommentaireService.getInstance().getByArticle(
                    com.bourlaforme.gui.back.article.ShowAllController.currentArticle.getId()
            );
        } else {
            listCommentaire = CommentaireService.getInstance().getAll();
        }

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listCommentaire);

        if (!listCommentaire.isEmpty()) {
            for (Commentaire commentaire : listCommentaire) {
                if (commentaire.getContenu().toLowerCase().startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeCommentaireModel(commentaire));
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

    public Parent makeCommentaireModel(
            Commentaire commentaire
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_COMMENTAIRE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#articleText")).setText("Article : " + commentaire.getArticle());
            ((Text) innerContainer.lookup("#auteurText")).setText("Auteur : " + commentaire.getAuteur());
            ((Text) innerContainer.lookup("#contenuText")).setText("Contenu : " + commentaire.getContenu());
            ((Text) innerContainer.lookup("#dateText")).setText("Date : " + commentaire.getDate());

            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerCommentaire(commentaire));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void supprimerCommentaire(Commentaire commentaire) {
        currentCommentaire = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer commentaire ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            if (CommentaireService.getInstance().delete(commentaire.getId())) {
                MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COMMENTAIRE);
            } else {
                AlertUtils.makeError("Could not delete commentaire");
            }
        }
    }

    @FXML
    private void search(KeyEvent event) {
        displayData(searchTF.getText());
    }

    private void specialAction(Commentaire commentaire) {

    }
}
