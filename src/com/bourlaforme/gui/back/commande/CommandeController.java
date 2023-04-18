package com.bourlaforme.gui.back.commande;

import com.bourlaforme.entities.Commande;
import com.bourlaforme.gui.back.MainWindowController;
import com.bourlaforme.services.CommandeService;
import com.bourlaforme.utils.AlertUtils;
import com.bourlaforme.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
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

public class CommandeController implements Initializable {

    @FXML
    public Text topText;
    @FXML
    public VBox mainVBox;
    @FXML
    public ComboBox<String> sortCB;
    @FXML
    public TextField searchTF;

    List<Commande> listCommande;

    @Override
public void initialize(URL url, ResourceBundle rb) {
    Pagination pagination=new Pagination() ;
    listCommande = CommandeService.getInstance().getAll();
    sortCB.getItems().addAll("Montant", "Date", "ConfirmeAdmin");

    int itemsPerPage = 3;
    int totalPages = (int) Math.ceil(listCommande.size() / (double) itemsPerPage);
    pagination = new Pagination(totalPages, 0);
    pagination.setPageFactory(this::createPage);

    mainVBox.getChildren().add(pagination);
}

public VBox createPage(int pageIndex) {
    VBox pageBox = new VBox();
    int itemsPerPage = 3;
    int startIndex = pageIndex * itemsPerPage;
    int endIndex = Math.min(startIndex + itemsPerPage, listCommande.size());
    List<Commande> itemsToShow = listCommande.subList(startIndex, endIndex);

    for (Commande commande : itemsToShow) {
        Parent commandeModel = makeCommandeModel(commande);
        pageBox.getChildren().add(commandeModel);
    }

    return pageBox;
}

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listCommande);

        if (!listCommande.isEmpty()) {
            for (Commande commande : listCommande) {
                if (String.valueOf(commande.getMontant()).startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeCommandeModel(commande));
                }

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeCommandeModel(
            Commande commande
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_COMMANDE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#montantText")).setText("Montant : " + commande.getMontant());
            ((Text) innerContainer.lookup("#dateText")).setText("Date : " + commande.getDate());
            ((Text) innerContainer.lookup("#idPanierText")).setText("Panier : " + commande.getPanier().getId());
            ((Text) innerContainer.lookup("#idAddressText")).setText("Nom et Prenom : " + commande.getBillingAddress().getNom());
            ((Text) innerContainer.lookup("#confirmeAdminText")).setText("ConfirmeAdmin : " + commande.isConfirmeAdmin());


            ((Button) innerContainer.lookup("#approveButton")).setOnAction((event) -> approve(commande));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> delete(commande));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void approve(Commande commande) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir confirmer cette commande ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                commande.setConfirmeAdmin(true);
                if (CommandeService.getInstance().edit(commande)) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COMMANDE);
                } else {
                    AlertUtils.makeError("Could not accept commande");
                }
            }
        }
    }

    private void delete(Commande commande) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer cette commande ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                commande.setConfirmeAdmin(true);
                if (CommandeService.getInstance().delete(commande.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_COMMANDE);
                } else {
                    AlertUtils.makeError("Could not delete commande");
                }
            }
        }
    }

    @FXML
    public void sort(ActionEvent actionEvent) {
        Constants.compareVar = sortCB.getValue();
        Collections.sort(listCommande);
        displayData("");
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
