package com.bourlaforme.services;

import com.bourlaforme.entities.Reclamation;
import com.bourlaforme.entities.User;
import com.bourlaforme.utils.DatabaseConnection;
import com.bourlaforme.utils.RelationObject;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReclamationService {

    private static ReclamationService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public ReclamationService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ReclamationService getInstance() {
        if (instance == null) {
            instance = new ReclamationService();
        }
        return instance;
    }

    public List<Reclamation> getAll() {
        List<Reclamation> listReclamation = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM `reclamation` AS x " +
                            "LEFT JOIN `user` AS u ON x.user_id = u.id " +
                            "LEFT JOIN `user` AS co ON x.coach_id = co.id " +
                            "LEFT JOIN `club` AS cl ON x.club_id = cl.id " +
                            "LEFT JOIN `article` AS a ON x.article_id = a.id " +
                            "WHERE x.user_id = u.id " +
                            "OR x.coach_id = co.id " +
                            "OR x.club_id = cl.id " +
                            "OR x.article_id = a.id"
            );

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listReclamation.add(new Reclamation(
                        resultSet.getInt("id"),
                        new User(
                                resultSet.getInt("u.id"),
                                resultSet.getString("u.email"),
                                resultSet.getString("u.roles"),
                                resultSet.getString("u.password"),
                                resultSet.getString("u.nom"),
                                resultSet.getString("u.prenom"),
                                resultSet.getString("u.image"),
                                resultSet.getString("u.certificates"),
                                resultSet.getString("u.specialite"),
                                resultSet.getString("u.experiance"),
                                resultSet.getString("u.description"),
                                resultSet.getBoolean("u.is_coach"),
                                resultSet.getBoolean("u.approved"),
                                resultSet.getString("u.likes"),
                                resultSet.getFloat("u.moyenne")
                        ),
                        new User(
                                resultSet.getInt("co.id"),
                                resultSet.getString("co.email"),
                                resultSet.getString("co.roles"),
                                resultSet.getString("co.password"),
                                resultSet.getString("co.nom"),
                                resultSet.getString("co.prenom"),
                                resultSet.getString("co.image"),
                                resultSet.getString("co.certificates"),
                                resultSet.getString("co.specialite"),
                                resultSet.getString("co.experiance"),
                                resultSet.getString("co.description"),
                                resultSet.getBoolean("co.is_coach"),
                                resultSet.getBoolean("co.approved"),
                                resultSet.getString("co.likes"),
                                resultSet.getFloat("co.moyenne")
                        ),
                        new RelationObject(resultSet.getInt("cl.id"), resultSet.getString("cl.nom")),
                        new RelationObject(resultSet.getInt("a.id"), resultSet.getString("a.nom")),
                        LocalDate.parse(String.valueOf(resultSet.getDate("dateReclamation"))),
                        resultSet.getString("etat"),
                        resultSet.getString("reponse"),
                        resultSet.getString("type"),
                        resultSet.getString("message")

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) reclamation : " + exception.getMessage());
        }
        return listReclamation;
    }

    public List<RelationObject> getAllClubs() {
        List<RelationObject> clubList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `club`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clubList.add(
                        new RelationObject(
                                resultSet.getInt("id"),
                                resultSet.getString("nom")
                        )
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) clubList : " + exception.getMessage());
        }
        return clubList;
    }

    public List<RelationObject> getAllArticles() {
        List<RelationObject> articleList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `article`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                articleList.add(
                        new RelationObject(
                                resultSet.getInt("id"),
                                resultSet.getString("nom")
                        )
                );
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) articleList : " + exception.getMessage());
        }
        return articleList;
    }

    public boolean add(Reclamation reclamation, String reclamationType) {

        String request = "";

        switch (reclamationType) {
            case "Coach":
                request = "INSERT INTO `reclamation`(`user_id`, `coach_id`, `dateReclamation`, `etat`, `reponse`, `type`, `message`) VALUES(?, ?, ?, ?, ?, ?, ?)";
                break;
            case "Club":
                request = "INSERT INTO `reclamation`(`user_id`, `club_id`, `dateReclamation`, `etat`, `reponse`, `type`, `message`) VALUES(?, ?, ?, ?, ?, ?, ?)";
                break;
            case "Article":
                request = "INSERT INTO `reclamation`(`user_id`, `article_id`, `dateReclamation`, `etat`, `reponse`, `type`, `message`) VALUES(?, ?, ?, ?, ?, ?, ?)";
                break;
            default:
                break;
        }

        try {
            preparedStatement = connection.prepareStatement(request);


            preparedStatement.setInt(1, reclamation.getUser() == null ? -1 : reclamation.getUser().getId());

            switch (reclamationType) {
                case "Coach":
                    preparedStatement.setInt(2, reclamation.getCoach() == null ? -1 : reclamation.getCoach().getId());
                    break;
                case "Club":
                    preparedStatement.setInt(2, reclamation.getClub() == null ? -1 : reclamation.getClub().getId());
                    break;
                case "Article":
                    preparedStatement.setInt(2, reclamation.getArticle() == null ? -1 : reclamation.getArticle().getId());
                    break;
                default:
                    break;
            }

            preparedStatement.setDate(3, Date.valueOf(reclamation.getDateReclamation()));
            preparedStatement.setString(4, reclamation.getEtat());
            preparedStatement.setString(5, reclamation.getReponse());
            preparedStatement.setString(6, reclamation.getType());
            preparedStatement.setString(7, reclamation.getMessage());

            preparedStatement.executeUpdate();
            System.out.println("Reclamation added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) reclamation : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Reclamation reclamation) {

        String request = "UPDATE `reclamation` SET `dateReclamation` = ?, `etat` = ?, `reponse` = ? WHERE `id`=" + reclamation.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setDate(1, Date.valueOf(reclamation.getDateReclamation()));
            preparedStatement.setString(2, reclamation.getEtat());
            preparedStatement.setString(3, reclamation.getReponse());

            preparedStatement.executeUpdate();
            System.out.println("Reclamation edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) reclamation : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `reclamation` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Reclamation deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) reclamation : " + exception.getMessage());
        }
        return false;
    }
}
