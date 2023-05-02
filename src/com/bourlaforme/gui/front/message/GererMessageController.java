package com.bourlaforme.gui.front.message;


import com.bourlaforme.MainApp;
import com.bourlaforme.entities.Message;
import com.bourlaforme.entities.User;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.services.MessageService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GererMessageController implements Initializable {

    @FXML
    public Text receiverText;
    @FXML
    public ComboBox<User> receiverCB;
    @FXML
    public TextField messageTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Message currentMessage;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (User user : MessageService.getInstance().getAllUsers()) {
            if (User.connectedUser.getRoles().equals("[\"ROLE_COACH\"]")) {
                receiverText.setText("Client : ");
                if (user.getRoles().equals("[\"ROLE_CLIENT\"]")) {
                    receiverCB.getItems().add(user);
                }
            } else {
                receiverText.setText("Coach : ");
                if (user.getRoles().equals("[\"ROLE_COACH\"]")) {
                    receiverCB.getItems().add(user);
                }
            }
        }

        currentMessage = AfficherToutMessageController.currentMessage;

        if (currentMessage != null) {
            topText.setText("Modifier message");
            btnAjout.setText("Modifier");

            try {
                receiverCB.setValue(currentMessage.getReceiver());
                messageTF.setText(currentMessage.getMessage());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter message");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent event) {

        if (controleDeSaisie()) {

            Message message = new Message(
                    User.connectedUser,
                    receiverCB.getValue(),
                    messageTF.getText()
            );

            if (currentMessage == null) {
                if (MessageService.getInstance().add(message)) {
                    AlertUtils.makeSuccessNotification("Message ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_MESSAGE);
                } else {
                    AlertUtils.makeError("message erreur");
                }
            } else {
                message.setId(currentMessage.getId());
                if (MessageService.getInstance().edit(message)) {
                    AlertUtils.makeSuccessNotification("Message modifié avec succés");
                    AfficherToutMessageController.currentMessage = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_MESSAGE);
                } else {
                    AlertUtils.makeError("message erreur");
                }
            }

        }
    }


    private boolean controleDeSaisie() {

        if (receiverCB.getValue() == null) {
            AlertUtils.makeInformation("Choisir receiver");
            return false;
        }


        if (messageTF.getText().isEmpty()) {
            AlertUtils.makeInformation("message ne doit pas etre vide");
            return false;
        }


        return true;
    }
}