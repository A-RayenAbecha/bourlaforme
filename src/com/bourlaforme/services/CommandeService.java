package com.bourlaforme.services;

import com.bourlaforme.entities.BillingAddress;
import com.bourlaforme.entities.Commande;
import com.bourlaforme.entities.Panier;
import com.bourlaforme.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandeService {

    private static CommandeService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public CommandeService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static CommandeService getInstance() {
        if (instance == null) {
            instance = new CommandeService();
        }
        return instance;
    }

    public Commande getByPanier(int panierId) {
        try {
            preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM `commande` " +
                    "WHERE commande.id_panier_id = " + panierId
            );

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Commande(
                        resultSet.getInt("id"),
                        resultSet.getInt("montant"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("date"))),
                        null,
                        null,
                        resultSet.getBoolean("confirme_admin")

                );
            }
            return null;
        } catch (SQLException exception) {
            System.out.println("Error (getAll) commande : " + exception.getMessage());
            return null;
        }
    }

    public List<Commande> getAll() {
        List<Commande> listCommande = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM `commande` AS x " +
                    "RIGHT JOIN `panier` AS y ON x.id_panier_id = y.id " +
                    "RIGHT JOIN `billing_address` AS z ON x.id_address_id = z.id " +
                    "WHERE x.id_panier_id = y.id " +
                    "AND x.id_address_id = z.id"
            );

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listCommande.add(new Commande(
                        resultSet.getInt("id"),
                        resultSet.getInt("montant"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("date"))),
                        new Panier(
                                resultSet.getInt("y.id"),
                                null,
                                resultSet.getInt("y.prix"),
                                resultSet.getInt("y.quantity"),
                                resultSet.getBoolean("y.confirme")
                        ),
                        new BillingAddress(
                                resultSet.getInt("z.id"),
                                resultSet.getString("z.nom"),
                                resultSet.getString("z.email"),
                                resultSet.getString("z.address"),
                                resultSet.getInt("z.phone"),
                                resultSet.getString("z.description")
                                
                        ),
                        resultSet.getBoolean("confirme_admin")

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) commande : " + exception.getMessage());
        }
        return listCommande;
    }

    public boolean add(Commande commande) {

        String request = "INSERT INTO `commande`(`montant`, `date`, `id_panier_id`, `id_address_id`, `confirme_admin`) VALUES(?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, commande.getMontant());
            preparedStatement.setDate(2, Date.valueOf(commande.getDate()));
            preparedStatement.setInt(3, commande.getPanier().getId());
            preparedStatement.setInt(4, commande.getBillingAddress().getId());
            preparedStatement.setBoolean(5, commande.isConfirmeAdmin());

            preparedStatement.executeUpdate();
            System.out.println("Commande added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) commande : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Commande commande) {

        String request = "UPDATE `commande` SET `montant` = ?, `date` = ?, `id_panier_id` = ?, `id_address_id` = ?, `confirme_admin` = ? WHERE `id`=" + commande.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, commande.getMontant());
            preparedStatement.setDate(2, Date.valueOf(commande.getDate()));
            preparedStatement.setInt(3, commande.getPanier().getId());
            preparedStatement.setInt(4, commande.getBillingAddress().getId());
            preparedStatement.setBoolean(5, commande.isConfirmeAdmin());

            preparedStatement.executeUpdate();
            System.out.println("Commande edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) commande : " + exception.getMessage());
        }
        return false;
    }
        public boolean updateConfirmeAdmin (int id, boolean Confirm ) {

        String request = "UPDATE `commande` SET  `confirme_admin` = ? WHERE `id`=" + id;
        try {
            preparedStatement = connection.prepareStatement(request);

 
            preparedStatement.setBoolean(5, Confirm);

            preparedStatement.executeUpdate();
            System.out.println("Commande edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) commande : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `commande` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Commande deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) commande : " + exception.getMessage());
        }
        return false;
    }
 


}
