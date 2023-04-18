/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.services;

import com.esprit.entities.Seance;
import com.esprit.entities.User;
import com.esprit.utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceSeance implements IService<Seance> {

    private Connection cnx;

    // Constructeur
    public ServiceSeance() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void ajouter(Seance s) {
        try {
            String requete = "INSERT INTO seance (id, nbr_grp, description, nbr_seance, user_id, color, titre) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, s.getId());
            pst.setInt(2, s.getNbr_grp());
            pst.setString(3, s.getDescription());
            pst.setInt(4, s.getNbr_seance());
            pst.setInt(5, s.getUser().getId());
            pst.setString(6, s.getColor());
            pst.setString(7, s.getTitre());
            pst.executeUpdate();
            System.out.println("Seance ajoutée avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Seance s) {
        try {
            String requete = "UPDATE seance SET nbr_grp=?, description=?, nbr_seance=?, user_id=?, color=?, titre=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, s.getNbr_grp());
            pst.setString(2, s.getDescription());
            pst.setInt(3, s.getNbr_seance());
            pst.setInt(4, s.getUser().getId());
            pst.setString(5, s.getColor());
            pst.setString(6, s.getTitre());
            pst.setInt(7, s.getId());
            pst.executeUpdate();
            System.out.println("Seance modifiée avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Seance s) {
        try {
            String requete = "DELETE FROM seance WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, s.getId());
            pst.executeUpdate();
            System.out.println("Seance supprimée avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Seance> afficher() {
        List<Seance> listSeances = new ArrayList<>();
        try {
            String requete = "SELECT * FROM seance";
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(requete);
            while (rs.next()) {
                Seance s = new Seance(
                        rs.getInt("id"),
                        rs.getInt("nbr_grp"),
                        rs.getString("description"),
                        rs.getInt("nbr_seance"),
                        new User(rs.getInt("user_id")),
                        rs.getString("color"),
                        rs.getString("titre")
                );
                listSeances.add(s);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listSeances;
    }
    
    public ArrayList<String> getEmailsForSeance(int seanceId) {
    ArrayList<String> emails = new ArrayList<>();
    try {
        String query = "select user.email FROM user JOIN reservation ON reservation.id_user_id=user.id join seance on reservation.seance_id = seance.id where seance.id = ?";
        PreparedStatement statement = cnx.prepareStatement(query);
        statement.setInt(1, seanceId);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            String email = rs.getString("email");
            emails.add(email);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return emails;
    }


    public Seance chercher(int id) {
    Seance s = null;
    try {
        String requete = "SELECT * FROM seance WHERE id=?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            s = new Seance(
                rs.getInt("id"),
                rs.getInt("nbr_grp"),
                rs.getString("description"),
                rs.getInt("nbr_seance"),
                new User(rs.getInt("user_id")),
                rs.getString("color"),
                rs.getString("titre")
            );
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return s;
}
public void modifierrating(Seance s, int nouveauRating) {
    try {
        // récupérer les informations de la séance existante
        String requeteSelect = "SELECT * FROM seance WHERE id=?";
        PreparedStatement pstSelect = cnx.prepareStatement(requeteSelect);
        pstSelect.setInt(1, s.getId());
        ResultSet rs = pstSelect.executeQuery();
        if (rs.next()) {
            // mettre à jour le nombre de ratings et le rating moyen
            int nbRatings = rs.getInt("nbrrating") + 1;
            float ancienMoyen = rs.getFloat("rating");
            float nouveauMoyen = ((ancienMoyen * rs.getInt("nbrrating")) + nouveauRating) / nbRatings;

            // mettre à jour les informations de la séance
            String requeteUpdate = "UPDATE seance SET   rating=?, nbrrating=? WHERE id=?";
            PreparedStatement pstUpdate = cnx.prepareStatement(requeteUpdate);
            
            pstUpdate.setFloat(1, nouveauMoyen);
            pstUpdate.setInt(2, nbRatings);
            pstUpdate.setInt(3, s.getId());
            pstUpdate.executeUpdate();
            System.out.println(nouveauMoyen);
        } else {
            System.err.println("Séance non trouvée !");
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
}
    
}