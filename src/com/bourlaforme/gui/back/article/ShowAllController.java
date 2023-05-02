package com.bourlaforme.gui.back.article;

import com.bourlaforme.entities.Article;
import com.bourlaforme.gui.back.MainWindowController;
import com.bourlaforme.services.ArticleService;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Article currentArticle;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
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

                mainVBox.getChildren().add(makeArticleModel(article));

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
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_ARTICLE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + article.getNom());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + article.getDescription());

            ((Text) innerContainer.lookup("#prixText")).setText("Prix : " + article.getPrix());
            ((Text) innerContainer.lookup("#etatText")).setText("Etat : " + article.getEtat());
            Path selectedImagePath = FileSystems.getDefault().getPath(article.getImage());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierArticle(article));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerArticle(article));
            ((Button) innerContainer.lookup("#showCommentsButton")).setOnAction((event) -> displayComments(article));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterArticle(ActionEvent event) {
        currentArticle = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_ARTICLE);
    }

    private void modifierArticle(Article article) {
        currentArticle = article;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_ARTICLE);
    }

    private void supprimerArticle(Article article) {
        currentArticle = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer article ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            if (ArticleService.getInstance().delete(article.getId())) {
                MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_ARTICLE);
            } else {
                AlertUtils.makeError("Could not delete article");
            }
        }
    }

    private void displayComments(Article article) {
        currentArticle = article;
        com.bourlaforme.gui.back.MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COMMENTAIRE);
    }
}
