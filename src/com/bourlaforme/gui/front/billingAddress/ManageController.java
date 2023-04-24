package com.bourlaforme.gui.front.billingAddress;


import com.bourlaforme.entities.BillingAddress;
import com.bourlaforme.entities.Commande;
import com.bourlaforme.entities.Panier;
import com.bourlaforme.entities.PanierArticle;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.gui.front.panier.MonPanierController;
import com.bourlaforme.services.BillingAddressService;
import com.bourlaforme.services.CommandeService;
import com.bourlaforme.services.PanierArticleService;
import com.bourlaforme.services.PanierService;
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
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ManageController implements Initializable {

    @FXML
    public TextField nomTF;
    @FXML
    public TextField emailTF;
    @FXML
    public TextField addressTF;
    @FXML
    public TextField phoneTF;
    @FXML
    public TextField descriptionTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        topText.setText("Ajouter vos informations");
        btnAjout.setText("Ajouter");
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {

            BillingAddress billingAddress = new BillingAddress(
                    nomTF.getText(),
                    emailTF.getText(),
                    addressTF.getText(),
                    Integer.parseInt(phoneTF.getText()),
                    descriptionTF.getText()
            );


            int idAddresse = BillingAddressService.getInstance().add(billingAddress);
            if (idAddresse != -1) {
                billingAddress.setId(idAddresse);

                int idPanier = PanierService.getInstance().add(MonPanierController.panier);
                if (idPanier != -1) {

                    MonPanierController.panier.setId(idPanier);
                    for (PanierArticle panierArticle : MonPanierController.monPanierArticleList) {
                        panierArticle.setPanier(MonPanierController.panier);
                        PanierArticleService.getInstance().add(panierArticle);
                    }

                    if (CommandeService.getInstance().add(new Commande(
                            MonPanierController.panier.getPrix(),
                            LocalDate.now(),
                            MonPanierController.panier,
                            billingAddress,
                            false
                    ))) {

                        AlertUtils.makeInformation("Commande ajouté avec succés");
                        MonPanierController.monPanierArticleList.clear();
                        MonPanierController.panier = new Panier();
                        MainWindowController.getInstance().loadInterface("/com/bourlaforme/gui/front/payment/Manage.fxml");


                    } else {
                        AlertUtils.makeError("Erreur");
                    }
                }
            } else {
                AlertUtils.makeError("Erreur");
            }

        }
    }

    private boolean controleDeSaisie() {


        if (nomTF.getText().isEmpty()) {
            AlertUtils.makeInformation("nom ne doit pas etre vide");
            return false;
        }


        if (emailTF.getText().isEmpty()) {
            AlertUtils.makeInformation("email ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^(.+)@(.+)$").matcher(emailTF.getText()).matches()) {
            AlertUtils.makeInformation("Email invalide");
            return false;
        }


        if (addressTF.getText().isEmpty()) {
            AlertUtils.makeInformation("address ne doit pas etre vide");
            return false;
        }


        if (phoneTF.getText().isEmpty()) {
            AlertUtils.makeInformation("phone ne doit pas etre vide");
            return false;
        }


        try {
            Integer.parseInt(phoneTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("phone doit etre un nombre");
            return false;
        }

        if (descriptionTF.getText().isEmpty()) {
            AlertUtils.makeInformation("description ne doit pas etre vide");
            return false;
        }


        return true;
    }
}