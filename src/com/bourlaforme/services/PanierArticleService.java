package com.bourlaforme.services;

import com.bourlaforme.entities.Article;
import com.bourlaforme.entities.Panier;
import com.bourlaforme.entities.PanierArticle;
import com.bourlaforme.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PanierArticleService {

    private static PanierArticleService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public PanierArticleService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static PanierArticleService getInstance() {
        if (instance == null) {
            instance = new PanierArticleService();
        }
        return instance;
    }

    public List<PanierArticle> getAll() {
        List<PanierArticle> listPanierArticle = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM `panier_article` AS x " +
                    "RIGHT JOIN `article` AS y ON x.id_article_id = y.id " +
                    "RIGHT JOIN `panier` AS z ON x.id_panier_id = z.id " +
                    "WHERE x.id_article_id = y.id " +
                    "AND x.id_panier_id = z.id "
            );

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listPanierArticle.add(new PanierArticle(
                        resultSet.getInt("id"),
                        new Article(
                                resultSet.getInt("y.id"),
                                resultSet.getString("y.nom"),
                                resultSet.getString("y.description"),
                                resultSet.getString("y.image"),
                                resultSet.getInt("y.prix"),
                                resultSet.getString("y.etat")
                        ),
                        new Panier(
                                resultSet.getInt("z.id"),
                                null,
                                resultSet.getInt("z.prix"),
                                resultSet.getInt("z.quantity"),
                                resultSet.getBoolean("z.confirme")
                        ),
                        resultSet.getInt("quantity")

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) panierArticle : " + exception.getMessage());
        }
        return listPanierArticle;
    }

    public List<Article> getAllArticle() {
        List<Article> listArticle = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `article` ");

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
            System.out.println("Error (getAll) getAllArticle : " + exception.getMessage());
        }
        return listArticle;
    }


    public boolean add(PanierArticle panierArticle) {

        String request = "INSERT INTO `panier_article`(`id_article_id`, `id_panier_id`, `quantity`) VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, panierArticle.getArticle().getId());
            preparedStatement.setInt(2, panierArticle.getPanier().getId());
            preparedStatement.setInt(3, panierArticle.getQuantity());

            preparedStatement.executeUpdate();
            System.out.println("PanierArticle added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) panierArticle : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(PanierArticle panierArticle) {

        String request = "UPDATE `panier_article` SET `id_article_id` = ?, `id_panier_id` = ?, `quantity` = ? WHERE `id`=" + panierArticle.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, panierArticle.getArticle().getId());
            preparedStatement.setInt(2, panierArticle.getPanier().getId());
            preparedStatement.setInt(3, panierArticle.getQuantity());

            preparedStatement.executeUpdate();
            System.out.println("PanierArticle edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) panierArticle : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `panier_article` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("PanierArticle deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) panierArticle : " + exception.getMessage());
        }
        return false;
    }
 
public List<PanierArticle> searchByPanier(int panierId) {
    List<PanierArticle> panierArticles = new ArrayList<>();
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        // Prepare a statement to retrieve the PanierArticles associated with the given panierId
        String query = "SELECT * FROM  panier_article WHERE id_panier_id=?";
        stmt = connection.prepareStatement(query);
        stmt.setInt(1, panierId);
        rs = stmt.executeQuery();

        // Iterate over the PanierArticles and retrieve the corresponding Articles
        while (rs.next()) {
            int articleId = rs.getInt("id_article_id");
            int quantity = rs.getInt("quantity");

            // Prepare a statement to retrieve the Article associated with the current PanierArticle
            query = "SELECT * FROM Article WHERE id=?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, articleId);
            ResultSet articleRs = stmt.executeQuery();

            // Create a PanierArticle object from the result set and add it to the list of PanierArticles
            if (articleRs.next()) {
                Article article = new Article();
                article.setId(articleRs.getInt("id"));
                article.setNom(articleRs.getString("nom"));
                article.setDescription(articleRs.getString("description"));
                article.setImage(articleRs.getString("image"));
                article.setPrix(articleRs.getInt("prix"));
                article.setEtat(articleRs.getString("etat"));

                PanierArticle panierArticle = new PanierArticle();
                panierArticle.setArticle(article);
                panierArticle.setQuantity(quantity);

                panierArticles.add(panierArticle);
            }

            // Close the article result set
            if (articleRs != null) {
                articleRs.close();
            }
        }

        // Close the statement and result set
        if (stmt != null) {
            stmt.close();
        }
        if (rs != null) {
            rs.close();
        }

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    return panierArticles;
}





}
