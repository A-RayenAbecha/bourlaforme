package com.bourlaforme.gui;

import com.bourlaforme.MainApp;
import com.bourlaforme.entities.User;
import com.bourlaforme.services.MessageService;
import com.bourlaforme.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    public ComboBox<User> userCB;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (User user : MessageService.getInstance().getAllUsers()) {
            userCB.getItems().add(user);
        }
    }

    @FXML
    public void frontend(ActionEvent ignored) {
        if (userCB.getValue() == null) {
            AlertUtils.makeError("Choose user");
        } else {
            MainApp.session = userCB.getValue();
            MainApp.getInstance().loadFront();
        }
    }

    @FXML
    public void backend(ActionEvent ignored) {
        MainApp.getInstance().loadBack();
    }
}
