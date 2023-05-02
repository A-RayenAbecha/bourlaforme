package com.bourlaforme.gui.front.commentaire;


import com.bourlaforme.entities.Commentaire;
import com.bourlaforme.entities.User;
import com.bourlaforme.gui.front.MainWindowController;
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
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public ComboBox<RelationObject> articleCB;
    
    @FXML
    public TextField contenuTF;
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
                contenuTF.setText(currentCommentaire.getContenu());

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
                    User.connectedUser.getNom(),
                    contenuTF.getText(),
                    LocalDate.now()
            );

            if (currentCommentaire == null) {
                if (CommentaireService.getInstance().add(commentaire)) {
                    AlertUtils.makeSuccessNotification("Commentaire ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_COMMENTAIRE);
                } else {
                    AlertUtils.makeError("Erreur");
                }
            } else {
                commentaire.setId(currentCommentaire.getId());
                if (CommentaireService.getInstance().edit(commentaire)) {
                    AlertUtils.makeSuccessNotification("Commentaire modifié avec succés");
                    ShowAllController.currentCommentaire = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_COMMENTAIRE);
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


        if (contenuTF.getText().isEmpty()) {
            AlertUtils.makeInformation("contenu ne doit pas etre vide");
            return false;
        }


        return true;
    }
}