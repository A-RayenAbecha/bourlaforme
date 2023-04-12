package com.bourlaforme.services;

import com.bourlaforme.entities.BillingAddress;
import com.bourlaforme.utils.DatabaseConnection;

import java.sql.*;

public class BillingAddressService {

    private static BillingAddressService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public BillingAddressService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static BillingAddressService getInstance() {
        if (instance == null) {
            instance = new BillingAddressService();
        }
        return instance;
    }

    public int add(BillingAddress billingAddress) {

        String request = "INSERT INTO `billing_address`(`nom`, `email`, `address`, `phone`, `description`) VALUES(?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, billingAddress.getNom());
            preparedStatement.setString(2, billingAddress.getEmail());
            preparedStatement.setString(3, billingAddress.getAddress());
            preparedStatement.setInt(4, billingAddress.getPhone());
            preparedStatement.setString(5, billingAddress.getDescription());

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
            System.out.println("Error (add) billingAddress : " + exception.getMessage());
        }
        return -1;
    }

    public boolean edit(BillingAddress billingAddress) {


        String request = "UPDATE `billing_address` SET `nom` = ?, `email` = ?, `address` = ?, `phone` = ?, `description` = ? WHERE `id`=" + billingAddress.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, billingAddress.getNom());
            preparedStatement.setString(2, billingAddress.getEmail());
            preparedStatement.setString(3, billingAddress.getAddress());
            preparedStatement.setInt(4, billingAddress.getPhone());
            preparedStatement.setString(5, billingAddress.getDescription());

            preparedStatement.executeUpdate();
            System.out.println("BillingAddress edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) billingAddress : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `billing_address` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("BillingAddress deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) billingAddress : " + exception.getMessage());
        }
        return false;
    }
}
