package com.bourlaforme.gui.front.panier;

import com.bourlaforme.entities.Panier;
import com.bourlaforme.entities.PanierArticle;
import com.bourlaforme.gui.front.MainWindowController;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MonPanierController implements Initializable {

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;

    public static Panier panier;
    @FXML

    public Button validateButton;
    @FXML
    public TextField searchTF;

    public static List<PanierArticle> monPanierArticleList = new ArrayList<>();
    @FXML
    public Text totalText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panier = new Panier();

        displayData("");

        int prixTotal = 0;
        for (PanierArticle panierArticle : monPanierArticleList) {
            prixTotal += panierArticle.getQuantity() * panierArticle.getArticle().getPrix();
        }
        totalText.setText("Total : " + prixTotal);
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        int panierArticleCount = 0;
        for (PanierArticle panierArticle : monPanierArticleList) {
            if (panierArticle.getPanier() != null) {
                if (panierArticle.getArticle().getNom().toLowerCase().startsWith(searchText.toLowerCase())) {
                    panierArticleCount++;
                    mainVBox.getChildren().add(makePanierArticleModel(panierArticle));
                }
            }
        }

        if (panierArticleCount == 0) {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makePanierArticleModel(
            PanierArticle panierArticle
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_PANIER_ARTICLE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#articleNameText")).setText("Article : " + panierArticle.getArticle().getNom());
            ((Text) innerContainer.lookup("#quantityText")).setText("Quantity : " + panierArticle.getQuantity());
            ((Text) innerContainer.lookup("#unitPriceText")).setText("Unit price : " + panierArticle.getArticle().getPrix());
            ((Text) innerContainer.lookup("#totalPriceText")).setText("Total price : " + (panierArticle.getArticle().getPrix() * panierArticle.getQuantity()));

            ((Button) innerContainer.lookup("#addButton")).setOnAction((event) -> addQuantity(panierArticle));
            ((Button) innerContainer.lookup("#removeButton")).setOnAction((event) -> removeQuantity(panierArticle));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void addQuantity(PanierArticle panierArticle) {
        panierArticle.setQuantity(panierArticle.getQuantity() + 1);
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);
    }

    private void removeQuantity(PanierArticle panierArticle) {
        if (panierArticle.getQuantity() == 1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmer la suppression");
            alert.setHeaderText(null);
            alert.setContentText("Etes vous sûr de vouloir supprimer cet article de votre panier ?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.isPresent()) {
                if (action.get() == ButtonType.OK) {
                    monPanierArticleList.remove(panierArticle);
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);
                }
            }
        } else {
            panierArticle.setQuantity(panierArticle.getQuantity() - 1);
            MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_MY_PANIER);
        }
    }

    @FXML
    private void ajouterPanierArticle(ActionEvent event) {
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_ADD_PANIER_ARTICLE);
    }

    public void validerPanier(ActionEvent actionEvent) {
        if (monPanierArticleList.isEmpty()) {
            AlertUtils.makeError("Panier vide");
        } else {

            int prixTotal = 0;
            for (PanierArticle panierArticle : monPanierArticleList) {
                prixTotal += panierArticle.getQuantity() * panierArticle.getArticle().getPrix();
            }
            panier.setPrix(prixTotal);

            MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_BILLINGADDRESS);
        }
    }

    @FXML
    private void search(KeyEvent event) {
        displayData(searchTF.getText());
    }
        public void Logout (ActionEvent e) throws IOException {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root=FXMLLoader.load(getClass().getResource("/com/bourlaforme/gui/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
