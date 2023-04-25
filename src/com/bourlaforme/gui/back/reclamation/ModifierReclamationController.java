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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.time.LocalDate;
import java.util.Properties;
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
                try {
                    if (reclamation.getUser() != null) {
                        if (reclamation.getUser().getEmail() != null) {
                            sendMail(reclamation.getUser().getEmail());
                        }
                    } else if (reclamation.getCoach() != null) {
                        if (reclamation.getCoach().getEmail() != null) {
                            sendMail(reclamation.getCoach().getEmail());
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                AlertUtils.makeSuccessNotification("Reclamation modifié avec succés");
                AficherToutReclamationController.currentReclamation = null;
                MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_RECLAMATION);
            } else {
                AlertUtils.makeError("reclamation erreur");
            }
        }
    }

    public static void sendMail(String recepient) throws Exception {
        System.out.println(recepient);
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
        String myAccountEmail = "esprit.projet.pidev@gmail.com";
        //Your gmail password
        String password = "grepuiityvgvesos";
 
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
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    
    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Notification");
            String htmlCode = "<h1>Notification</h1> <br/> <h2><b>Une réponse a été ajouté a votre reclamation</b></h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (MessagingException ex) {
            System.out.println(ex);
        }
        return null;
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