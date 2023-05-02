package com.bourlaforme.services;

import com.bourlaforme.entities.Club;
import com.bourlaforme.entities.User;
import com.esprit.utils.DataSource;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceClub implements IService<Club> {

    private Connection cnx;

    // Constructeur
    public ServiceClub() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void ajouter(Club c) {
        try {
            String requete = "INSERT INTO club (id, nom, localisation, image, type_activite, id_club_owner_id, telephone, description, prix, longitude, latitude) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, c.getId());
            pst.setString(2, c.getNom());
            pst.setString(3, c.getLocalisation());
            pst.setString(4, c.getImage());
            pst.setString(5, c.getTypeActivite());
            pst.setInt(6, c.getIdClubOwnerId());
            pst.setString(7, c.getTelephone());
            pst.setString(8, c.getDescription());
            pst.setString(9, c.getPrix());
            pst.setDouble(10, c.getLongitude());
            pst.setDouble(11, c.getLatitude());
            pst.executeUpdate();
            System.out.println("Club ajouté avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Club c) {
        try {
            String requete = "UPDATE club SET nom=?, localisation=?, image=?, type_activite=?, id_club_owner_id=?, telephone=?, description=?, prix=?, longitude=?, latitude=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, c.getNom());
            pst.setString(2, c.getLocalisation());
            pst.setString(3, c.getImage());
            pst.setString(4, c.getTypeActivite());
            pst.setInt(5, c.getIdClubOwnerId());
            pst.setString(6, c.getTelephone());
            pst.setString(7, c.getDescription());
            pst.setString(8, c.getPrix());
            pst.setDouble(9, c.getLongitude());
            pst.setDouble(10, c.getLatitude());
            pst.setInt(11, c.getId());
            pst.executeUpdate();
            System.out.println("Club modifié avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Club c) {
        try {
            String requete = "DELETE FROM club WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, c.getId());
            pst.executeUpdate();
            System.out.println("Club supprimé avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Club> afficher() {
        List<Club> listClubs = new ArrayList<>();
        try {
            String requete = "SELECT * FROM club";
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(requete);
            while (rs.next()) {
                Club c = new Club(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("localisation"),
                        rs.getString("image"),
                        rs.getString("type_activite"),
                        rs.getInt("id_club_owner_id"),
                        rs.getString("telephone"),
                        rs.getString("description"),
                        rs.getString("prix"),
                        rs.getDouble("longitude"),
                        rs.getDouble("latitude")
                );
                listClubs.add(c);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listClubs;
    }

    public Club getClubById(int id) {
        Club club = null;
        try {
            String requete = "SELECT * FROM club WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                club = new Club(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("localisation"),
                        rs.getString("image"),
                        rs.getString("type_activite"),
                        rs.getInt("id_club_owner_id"),
                        rs.getString("telephone"),
                        rs.getString("description"),
                        rs.getString("prix"),
                        rs.getDouble("longitude"),
                        rs.getDouble("latitude")
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return club;
    }
    
    public List<Club> search(String clubName) {
    List<Club> listClubs = new ArrayList<>();
    try {
        String requete = "SELECT * FROM club WHERE nom LIKE ? OR localisation LIKE ?";
        PreparedStatement stm = cnx.prepareStatement(requete);
        stm.setString(1, "%" + clubName + "%");
        stm.setString(2, "%" + clubName + "%");
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            Club c = new Club(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("localisation"),
                    rs.getString("image"),
                    rs.getString("type_activite"),
                    rs.getInt("id_club_owner_id"),
                    rs.getString("telephone"),
                    rs.getString("description"),
                    rs.getString("prix"),
                    rs.getDouble("longitude"),
                    rs.getDouble("latitude")
            );
            listClubs.add(c);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return listClubs;
}




}

