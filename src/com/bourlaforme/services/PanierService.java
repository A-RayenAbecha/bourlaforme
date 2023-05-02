package com.bourlaforme.services;

import com.bourlaforme.entities.Commande;
import com.bourlaforme.entities.Panier;
import com.bourlaforme.utils.DatabaseConnection;

import java.sql.*;

public class PanierService {

    private static PanierService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public PanierService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static PanierService getInstance() {
        if (instance == null) {
            instance = new PanierService();
        }
        return instance;
    }

    public Panier getPanier(int idPanier) {
        try {
            preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM `panier` AS x " +
                    //"RIGHT JOIN `commande` AS y ON x.id_commande_id = y.id " +
                    //"WHERE x.id_commande_id = y.id " +
                    "WHERE x.id = " + idPanier);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Commande commande = null;
                if (resultSet.getInt("x.id_commande_id") != 0) {
                    commande = new Commande(resultSet.getInt("id_commande_id"));
                }
                return new Panier(
                        resultSet.getInt("id"),
                        commande,
                        resultSet.getInt("prix"),
                        resultSet.getInt("quantity"),
                        resultSet.getBoolean("confirme")
                );
            }
            return null;
        } catch (SQLException exception) {
            System.out.println("Error (get) panier : " + exception.getMessage());
            return null;
        }
    }

    public int add(Panier panier) {

        String request = "INSERT INTO `panier`(`prix`, `quantity`, `confirme`) VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, panier.getPrix());
            preparedStatement.setInt(2, panier.getQuantity());
            preparedStatement.setBoolean(3, panier.getConfirme());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating panier failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Insertion failed, no ID obtained.");
                }
            }

        } catch (SQLException exception) {
            System.out.println("Error (add) panier : " + exception.getMessage());
        }
        return -1;
    }

    public boolean edit(Panier panier) {

        String request = "UPDATE `panier` SET `id_commande_id` = ?, `prix` = ?, `quantity` = ?, `confirme` = ? WHERE `id`=" + panier.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, panier.getCommande().getId());
            preparedStatement.setInt(2, panier.getPrix());
            preparedStatement.setInt(3, panier.getQuantity());
            preparedStatement.setBoolean(4, panier.getConfirme());

            preparedStatement.executeUpdate();
            System.out.println("Panier edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) panier : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `panier` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Panier deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) panier : " + exception.getMessage());
        }
        return false;
    }
}
