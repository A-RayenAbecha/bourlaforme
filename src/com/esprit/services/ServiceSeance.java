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

    
}