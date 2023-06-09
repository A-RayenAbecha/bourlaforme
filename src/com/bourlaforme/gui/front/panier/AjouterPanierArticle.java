package com.bourlaforme.gui.front.panier;


import com.bourlaforme.entities.Article;
import com.bourlaforme.entities.PanierArticle;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.services.PanierArticleService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AjouterPanierArticle implements Initializable {

    @FXML
    public ComboBox<Article> articleCB;
    @FXML
    public TextField quantityTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Article article : PanierArticleService.getInstance().getAllArticle()) {
            articleCB.getItems().add(article);
        }

        topText.setText("Ajouter article au panier");
        btnAjout.setText("Ajouter");
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie1()) {

            PanierArticle panierArticle = new PanierArticle(
                    articleCB.getValue(),
                    MonPanierController.panier,
                    Integer.parseInt(quantityTF.getText())
            );

            MonPanierController.monPanierArticleList.add(panierArticle);
            AlertUtils.makeSuccessNotification("Article ajouté avec succés");
            MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_PANIER_ARTICLE);
        }
    }


    private boolean controleDeSaisie1() {


        if (articleCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir article");
            return false;
        }

        if (quantityTF.getText().isEmpty()) {
            AlertUtils.makeInformation("quantity ne doit pas etre vide");
            return false;
        }

        try {
            Integer.parseInt(quantityTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("quantity doit etre un nombre");
            return false;
        }

        return true;
    }
         @FXML
    public void Return (ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("/com/bourlaforme/gui/front/panier/MonPanier.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}