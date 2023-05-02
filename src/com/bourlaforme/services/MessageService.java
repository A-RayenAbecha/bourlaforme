package com.bourlaforme.services;

import com.bourlaforme.entities.Message;
import com.bourlaforme.entities.User;
import com.bourlaforme.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    private static MessageService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public MessageService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }

    public List<Message> getAll() {
        List<Message> listMessage = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM message AS x "
                    + "RIGHT JOIN user AS s ON x.sender_id = s.id "
                    + "RIGHT JOIN user AS r ON x.receiver_id = r.id "
                    + "WHERE x.sender_id = s.id "
                    + "AND x.receiver_id = r.id"
            );

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listMessage.add(new Message(
                        resultSet.getInt("id"),
                        new User(
                                resultSet.getInt("s.id"),
                                resultSet.getString("s.email"),
                                resultSet.getString("s.roles")
                        ), new User(
                                resultSet.getInt("r.id"),
                                resultSet.getString("r.email"),
                                resultSet.getString("r.roles")
                        ),
                        resultSet.getString("message")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) message : " + exception.getMessage());
        }
        return listMessage;
    }
    
    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM user");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("roles")
                );
                listUser.add(user
                );
                System.out.println(user);
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) user : " + exception.getMessage());
        }
        return listUser;
    }

    public User getOneById(int id) {
        User p = null;
        try {
            String req = "Select * from user where id = '" + id + "'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                p = new User(rs.getString("email"), rs.getString("roles"), rs.getString("password"),
                        rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("image"), rs.getString("certificates"),
                        rs.getString("specialite"), rs.getString("experiance"),
                        rs.getString("description"), rs.getBoolean("is_coach"),
                        rs.getBoolean("approved"), rs.getString("likes"), 
                        (float) rs.getDouble("moyenne"));
            }
            return p;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return p;
    }

    public User getByUsername(String user) {
        User p = null;
        try {
            String req = "Select * from user where prenom = '" + user + "'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                System.out.println(rs.getString("prenom"));
                p = new User(rs.getInt("id"),rs.getString("email"),rs.getString("roles"));
                return p;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return p;
    }

    public boolean add(Message message) {

        String request = "INSERT INTO message`(sender_id`, receiver_id, message) VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, message.getSender().getId());
            preparedStatement.setInt(2, message.getReceiver().getId());
            preparedStatement.setString(3, message.getMessage());

            preparedStatement.executeUpdate();
            System.out.println("Message added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) message : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Message message) {

        String request = "UPDATE message SET sender_id = ?, receiver_id = ?, message = ? WHERE `id`=" + message.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, message.getSender().getId());
            preparedStatement.setInt(2, message.getReceiver().getId());
            preparedStatement.setString(3, message.getMessage());

            preparedStatement.executeUpdate();
            System.out.println("Message edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) message : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM message WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Message deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) message : " + exception.getMessage());
        }
        return false;
    }
}