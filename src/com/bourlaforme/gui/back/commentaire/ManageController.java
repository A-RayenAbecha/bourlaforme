package com.bourlaforme.gui.back.commentaire;


import com.bourlaforme.entities.Commentaire;
import com.bourlaforme.gui.back.MainWindowController;
import com.bourlaforme.services.CommentaireService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import com.bourlaforme.utils.RelationObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public ComboBox<RelationObject> articleCB;
    @FXML
    public TextField auteurTF;
    @FXML
    public TextField contenuTF;
    @FXML
    public DatePicker dateDP;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Commentaire currentCommentaire;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (RelationObject article : CommentaireService.getInstance().getAllArticles()) {
            articleCB.getItems().add(article);
        }

        currentCommentaire = ShowAllController.currentCommentaire;

        if (currentCommentaire != null) {
            topText.setText("Modifier commentaire");
            btnAjout.setText("Modifier");

            try {
                articleCB.setValue(currentCommentaire.getArticle());
                auteurTF.setText(currentCommentaire.getAuteur());
                contenuTF.setText(currentCommentaire.getContenu());
                dateDP.setValue(currentCommentaire.getDate());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter commentaire");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {

            Commentaire commentaire = new Commentaire(
                    articleCB.getValue(),
                    auteurTF.getText(),
                    contenuTF.getText(),
                    dateDP.getValue()
            );

            if (currentCommentaire == null) {
                if (CommentaireService.getInstance().add(commentaire)) {
                    AlertUtils.makeSuccessNotification("Commentaire ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COMMENTAIRE);
                } else {
                    AlertUtils.makeError("Erreur");
                }
            } else {
                commentaire.setId(currentCommentaire.getId());
                if (CommentaireService.getInstance().edit(commentaire)) {
                    AlertUtils.makeSuccessNotification("Commentaire modifié avec succés");
                    ShowAllController.currentCommentaire = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COMMENTAIRE);
                } else {
                    AlertUtils.makeError("Erreur");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (articleCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir article");
            return false;
        }


        if (auteurTF.getText().isEmpty()) {
            AlertUtils.makeInformation("auteur ne doit pas etre vide");
            return false;
        }


        if (contenuTF.getText().isEmpty()) {
            AlertUtils.makeInformation("contenu ne doit pas etre vide");
            return false;
        }


        if (dateDP.getValue() == null) {
            AlertUtils.makeInformation("Choisir une date pour date");
            return false;
        }


        return true;
    }
}