package com.bourlaforme.gui.front.article;

import com.bourlaforme.entities.Article;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.services.ArticleService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ShowAllController implements Initializable {

    public static Article currentArticle;

    @FXML
    public Text topText;
    @FXML
    public VBox mainVBox;

    List<Article> listArticle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listArticle = ArticleService.getInstance().getAll();
        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listArticle);

        if (!listArticle.isEmpty()) {
            for (Article article : listArticle) {
                if (!article.getEtat().equals("supprimer")) {
                    mainVBox.getChildren().add(makeArticleModel(article));
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

    public Parent makeArticleModel(
            Article article
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_ARTICLE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + article.getNom());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + article.getDescription());

            ((Text) innerContainer.lookup("#prixText")).setText("Prix : " + article.getPrix());
            ((Text) innerContainer.lookup("#etatText")).setText("Etat : " + article.getEtat());
            Path selectedImagePath = FileSystems.getDefault().getPath(article.getImage());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            ((Button) innerContainer.lookup("#showCommentsButton")).setOnAction((event) -> specialAction(article));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void specialAction(Article article) {
        currentArticle = article;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_COMMENTAIRE);
    }
}
