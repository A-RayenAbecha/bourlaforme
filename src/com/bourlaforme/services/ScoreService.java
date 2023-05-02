package com.bourlaforme.services;

import com.bourlaforme.entities.Score;
import com.bourlaforme.entities.User;
import com.bourlaforme.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreService {

    private static ScoreService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public ScoreService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ScoreService getInstance() {
        if (instance == null) {
            instance = new ScoreService();
        }
        return instance;
    }

    public List<Score> getAll() {
        List<Score> listScore = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM score AS x "
                    + "RIGHT JOIN user AS c ON x.coach_id = c.id "
                    + "RIGHT JOIN user AS u ON x.user_id = u.id "
                    + "WHERE x.coach_id = c.id "
                    + "AND x.user_id = u.id"
            );

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listScore.add(new Score(
                        resultSet.getInt("id"),
                        new User(
                                resultSet.getInt("c.id"),
                                resultSet.getString("c.email")
                        ),
                        new User(
                                resultSet.getInt("u.id"),
                                resultSet.getString("u.email")
                        ),
                        resultSet.getInt("note")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) score : " + exception.getMessage());
        }
        return listScore;
    }

    public boolean add(Score score) {

        String request = "INSERT INTO score`(coach_id`, user_id, note) VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, score.getCoach().getId());
            preparedStatement.setInt(2, score.getUser().getId());
            preparedStatement.setInt(3, score.getNote());

            preparedStatement.executeUpdate();
            System.out.println("Score added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) score : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Score score) {

        String request = "UPDATE score SET coach_id = ?, user_id = ?, note = ? WHERE `id`=" + score.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, score.getCoach().getId());
            preparedStatement.setInt(2, score.getUser().getId());
            preparedStatement.setInt(3, score.getNote());

            preparedStatement.executeUpdate();
            System.out.println("Score edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) score : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM score WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Score deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) score : " + exception.getMessage());
        }
        return false;
    }
}