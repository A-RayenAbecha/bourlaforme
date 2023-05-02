/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.bourlaforme.gui.back.commande;

import com.bourlaforme.entities.Commande;
import com.bourlaforme.services.CommandeService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class StatController implements Initializable {
    @FXML
    private Pane statPane;
    private CommandeService commandeService;

    public StatController() {
        commandeService = new CommandeService();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Récupérer les données des commandes
        List<Commande> commandes = commandeService.getAll();

        // Calculer les statistiques
        int maxMontant = 0;
        int minMontant = Integer.MAX_VALUE;
        double avgMontant = 0.0;
        int totalMontant = 0;
        int nbCommandes = commandes.size();
        ObservableList<XYChart.Data<String, Number>> data = FXCollections.observableArrayList();

        for (Commande commande : commandes) {
            int montant = commande.getMontant();
            maxMontant = Math.max(maxMontant, montant);
            minMontant = Math.min(minMontant, montant);
            totalMontant += montant;

            // Ajouter les données pour le graphique
            data.add(new XYChart.Data<>(commande.getDate().toString(), montant));
        }

        if (nbCommandes > 0) {
            avgMontant = (double) totalMontant / nbCommandes;
        }

        // Créer le graphique de statistiques
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Montant");

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setTitle("Statistiques des montants de commandes");

        XYChart.Series<String, Number> series = new XYChart.Series<>(data);
        series.setName("Montant");
        chart.getData().add(series);

        // Afficher les statistiques
        System.out.println("Nombre de commandes : " + nbCommandes);
        System.out.println("Montant maximum : " + maxMontant);
        System.out.println("Montant minimum : " + minMontant);
        System.out.println("Montant moyen : " + avgMontant);

        statPane.getChildren().add(chart);
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
