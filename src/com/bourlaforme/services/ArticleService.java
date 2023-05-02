package com.bourlaforme.services;

import com.bourlaforme.entities.Article;
import com.bourlaforme.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleService {

    private static ArticleService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public ArticleService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ArticleService getInstance() {
        if (instance == null) {
            instance = new ArticleService();
        }
        return instance;
    }

    public List<Article> getAll() {
        List<Article> listArticle = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `article`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listArticle.add(new Article(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("description"),
                        resultSet.getString("image"),
                        resultSet.getInt("prix"),
                        resultSet.getString("etat")

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) article : " + exception.getMessage());
        }
        return listArticle;
    }


    public boolean add(Article article) {

        String request = "INSERT INTO `article`(`nom`, `description`, `image`, `prix`, `etat`) VALUES(?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, article.getNom());
            preparedStatement.setString(2, article.getDescription());
            preparedStatement.setString(3, article.getImage());
            preparedStatement.setInt(4, article.getPrix());
            preparedStatement.setString(5, article.getEtat());

            preparedStatement.executeUpdate();
            System.out.println("Article added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) article : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Article article) {

        String request = "UPDATE `article` SET `nom` = ?, `description` = ?, `image` = ?, `prix` = ?, `etat` = ? WHERE `id`=" + article.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, article.getNom());
            preparedStatement.setString(2, article.getDescription());
            preparedStatement.setString(3, article.getImage());
            preparedStatement.setInt(4, article.getPrix());
            preparedStatement.setString(5, article.getEtat());

            preparedStatement.executeUpdate();
            System.out.println("Article edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) article : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `article` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Article deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) article : " + exception.getMessage());
        }
        return false;
    }
}
