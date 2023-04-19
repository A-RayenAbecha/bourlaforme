package com.bourlaforme.gui.back.reclamation;


import com.bourlaforme.entities.Reclamation;
import com.bourlaforme.gui.back.MainWindowController;
import com.bourlaforme.services.ReclamationService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifierReclamationController implements Initializable {

    @FXML
    public TextField reponseTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Reclamation currentReclamation;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentReclamation = AficherToutReclamationController.currentReclamation;

        topText.setText("Modifier reclamation");
        btnAjout.setText("Modifier");

        reponseTF.setText(currentReclamation.getReponse());
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            Reclamation reclamation = new Reclamation(
                    currentReclamation.getUser(),
                    currentReclamation.getCoach(),
                    currentReclamation.getClub(),
                    currentReclamation.getArticle(),
                    LocalDate.now(),
                    "traité",
                    reponseTF.getText(),
                    "",
                    ""
            );

            reclamation.setId(currentReclamation.getId());
            if (ReclamationService.getInstance().edit(reclamation)) {
                AlertUtils.makeSuccessNotification("Reclamation modifié avec succés");
                AficherToutReclamationController.currentReclamation = null;
                MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_RECLAMATION);
            } else {
                AlertUtils.makeError("reclamation erreur");
            }
        }
    }

    private boolean controleDeSaisie() {

        if (reponseTF.getText() == null) {
            AlertUtils.makeInformation("reponse ne doit pas etre vide");
            return false;
        }

        if (reponseTF.getText().isEmpty()) {
            AlertUtils.makeInformation("reponse ne doit pas etre vide");
            return false;
        }

        return true;
    }
}