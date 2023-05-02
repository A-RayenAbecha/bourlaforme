package com.bourlaforme.gui.front;

import com.bourlaforme.MainApp;
import com.bourlaforme.entities.User;
import com.bourlaforme.utils.Animations;
import com.bourlaforme.utils.Constants;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import com.bourlaforme.gui1.MainController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TopBarController implements Initializable {

    private final Color COLOR_GRAY = new Color(0.9, 0.9, 0.9, 1);
    private final Color COLOR_PRIMARY = Color.web("#FFFFFF");
    private final Color COLOR_DARK = new Color(1, 1, 1, 0.65);
    private Button[] liens;

    @FXML
    private Button btnArticles;

    @FXML
    private Button btnCommentaires;
    @FXML
    private Button btnPanier;
 
    @FXML
    private Button btnReclamations;

    @FXML
    private Button btnScores;
    @FXML
    private Button btnMessages;
    @FXML
    private AnchorPane mainComponent;
    @FXML
    private Button btnLogout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        liens = new Button[]{
                btnArticles,
                btnCommentaires,
                btnPanier
        };

        mainComponent.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }

        btnArticles.setTextFill(COLOR_DARK);

        btnCommentaires.setTextFill(COLOR_DARK);
        btnPanier.setTextFill(COLOR_DARK);


    }

    @FXML
    private void afficherArticles(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_DISPLAY_ALL_ARTICLE);

        btnArticles.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnArticles, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherCommentaires(ActionEvent event) {
        com.bourlaforme.gui.front.article.ShowAllController.currentArticle = null;
        goToLink(Constants.FXML_FRONT_DISPLAY_ALL_COMMENTAIRE);

        btnCommentaires.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnCommentaires, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
        @FXML

    private void afficherPanier(ActionEvent event) {
            MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_PANIER_ARTICLE);

    }
    
    public void redirectCoach(Event e) throws IOException{
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("/com/bourlaforme/gui/main-front.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void redirectClub(Event e) throws IOException{
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("/com/bourlaforme/gui1/main-front.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void afficherReclamations(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_DISPLAY_ALL_RECLAMATION);
 
    }

    @FXML
    private void afficherScores(ActionEvent event) {
        goToLink(Constants.FXML_FRONT_DISPLAY_ALL_SCORE);

   
    }

    @FXML
    private void afficherMessages(ActionEvent event) {
               goToLink(Constants.FXML_FRONT_MESSAGERIE);

    }

    
    
    
    private void goToLink(String link) {
        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            Animations.animateButton(lien, COLOR_GRAY, COLOR_DARK, COLOR_PRIMARY, 0, false);
        }
        MainWindowController.getInstance().loadInterface(link);
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        btnLogout.setOnAction(event -> {
        // Clear the connectedUser variable
        User.connectedUser = null;

        // Load the login page FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bourlaforme/interfaces/LoginForm.fxml"));
        Parent loginPageParent;
        try {
            loginPageParent = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Create a new Stage for the login page and show it
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(loginPageParent));
        loginStage.show();

        // Hide the current window
        ((Node)(event.getSource())).getScene().getWindow().hide();
        });
    }
}
