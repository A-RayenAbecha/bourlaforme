package com.bourlaforme.services;

import com.bourlaforme.entities.Commentaire;
import com.bourlaforme.utils.DatabaseConnection;
import com.bourlaforme.utils.RelationObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class CommentaireService {

    private static CommentaireService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public CommentaireService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static CommentaireService getInstance() {
        if (instance == null) {
            instance = new CommentaireService();
        }
        return instance;
    }

    public List<Commentaire> getAll() {
        List<Commentaire> listCommentaire = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `commentaire` AS x RIGHT JOIN `article` AS y ON x.article_id = y.id WHERE x.article_id = y.id ");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listCommentaire.add(new Commentaire(
                        resultSet.getInt("id"),
                        new RelationObject(resultSet.getInt("article_id"), resultSet.getString("y.nom")),
                        resultSet.getString("auteur"),
                        resultSet.getString("contenu"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("date")))

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) commentaire : " + exception.getMessage());
        }
        return listCommentaire;
    }

    public List<Commentaire> getByArticle(int idArticle) {
        List<Commentaire> listCommentaire = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM `commentaire` " +
                            "AS x RIGHT JOIN `article` " +
                            "AS y ON x.article_id = y.id " +
                            "WHERE x.article_id = y.id " +
                            "AND x.article_id = " + idArticle
            );

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listCommentaire.add(new Commentaire(
                        resultSet.getInt("id"),
                        new RelationObject(resultSet.getInt("article_id"), resultSet.getString("y.nom")),
                        resultSet.getString("auteur"),
                        resultSet.getString("contenu"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("date")))

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) commentaire : " + exception.getMessage());
        }
        return listCommentaire;
    }

    public List<RelationObject> getAllArticles() {
        List<RelationObject> listArticles = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `article` where etat='desarchiver'");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listArticles.add(new RelationObject(resultSet.getInt("id"), resultSet.getString("nom")));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) articles : " + exception.getMessage());
        }
        return listArticles;
    }

/*
    public boolean add(Commentaire commentaire) {

        String request = "INSERT INTO `commentaire`(`article_id`, `auteur`, `contenu`, `date`) VALUES(?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, commentaire.getArticle().getId());
            preparedStatement.setString(2, commentaire.getAuteur());
            preparedStatement.setString(3, commentaire.getContenu());
            preparedStatement.setDate(4, Date.valueOf(commentaire.getDate()));

            preparedStatement.executeUpdate();
            System.out.println("Commentaire added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) commentaire : " + exception.getMessage());
        }
        return false;
    }
*/
    
    
//C:/Users/Administrateur/OneDrive/Bureau/bourlaforme/bad.txt
    public boolean add(Commentaire commentaire) {
        String request = "INSERT INTO `commentaire`(`article_id`, `auteur`, `contenu`, `date`) VALUES(?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, commentaire.getArticle().getId());
            preparedStatement.setString(2, commentaire.getAuteur());
            String cleanedContent = replaceBadWords(commentaire.getContenu(), "C:/Users/aziz3/OneDrive/Bureau/projet/bourlaforme/bad.txt");
            preparedStatement.setString(3, cleanedContent);
            preparedStatement.setDate(4, Date.valueOf(commentaire.getDate()));

            preparedStatement.executeUpdate();
            System.out.println("Commentaire ajouté !");
            return true;
        } catch (SQLException exception) {
            System.out.println("Erreur lors de l'ajout du commentaire : " + exception.getMessage());
        }
        return false;
    }

   public static String replaceBadWords(String originalString, String badWordsFilePath) {
        String cleanedString = originalString;
        try {
            // Read bad words from file
            List<String> badWords = Files.readAllLines(Paths.get(badWordsFilePath));

            // Replace bad words with asterisks
            for (String badWord : badWords) {
                if (badWord != null && !badWord.isEmpty()) {
                   StringBuilder asterisksBuilder = new StringBuilder();
          for (int i = 0; i < badWord.length(); i++) {
    asterisksBuilder.append("*");
}
String asterisks = asterisksBuilder.toString();
                    cleanedString = cleanedString.replaceAll("(?i)\\b" + badWord + "\\b", asterisks);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading bad words file: " + e.getMessage());
        }
        return cleanedString;
    }
    
    
    
    public boolean edit(Commentaire commentaire) {

        String request = "UPDATE `commentaire` SET `article_id` = ?, `auteur` = ?, `contenu` = ?, `date` = ? WHERE `id`=" + commentaire.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, commentaire.getArticle().getId());
            preparedStatement.setString(2, commentaire.getAuteur());
            preparedStatement.setString(3, commentaire.getContenu());
            preparedStatement.setDate(4, Date.valueOf(commentaire.getDate()));

            preparedStatement.executeUpdate();
            System.out.println("Commentaire edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) commentaire : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `commentaire` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Commentaire deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) commentaire : " + exception.getMessage());
        }
        return false;
    }
}
