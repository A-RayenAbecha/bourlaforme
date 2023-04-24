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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
                         AlertUtils.makeSuccessNotification("Commande ajouté avec succés");
                        try {
                            sendMail(emailTF.getText());
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
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

    public static void sendMail(String recepient) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "pidev.app.esprit@gmail.com";
        //Your gmail password
        String password = "jkemsuddgeptmcsb";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient);

        //Send mail
        if (message != null) {
            Transport.send(message);
            System.out.println("Mail sent successfully");
        } else {
            System.out.println("Error sending the mail");
        }
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Notification");
            String htmlCode = "<h1>Notification</h1> <br/> <h2><b>Commande ajouté avec succes</b></h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return null;
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