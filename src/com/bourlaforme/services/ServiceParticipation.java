package com.bourlaforme.services;

import com.bourlaforme.entities.Club;
import com.bourlaforme.entities.Participation;
import com.bourlaforme.entities.User;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class ServiceParticipation implements IService<Participation> {

    private Connection cnx;

    // Constructeur
    public ServiceParticipation() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void ajouter(Participation p) {
        try {
            String requete = "INSERT INTO participation (id, id_club_id, id_user_id, date_debut, date_fin, participated) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.setInt(2, p.getIdClub());
            pst.setInt(3, p.getIdUser());
            pst.setTimestamp(4, new Timestamp(p.getDateDebut().getTime()));
            pst.setTimestamp(5, new Timestamp(p.getDateFin().getTime()));
            pst.setInt(6, p.isParticipated() ? 1 : 0);
            pst.executeUpdate();
            System.out.println("Participation ajoutée avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Participation p) {
        try {
            String requete = "UPDATE participation SET id_club_id=?, id_user_id=?, date_debut=?, date_fin=?, participated=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, p.getIdClub());
            pst.setInt(2, p.getIdUser());
            pst.setTimestamp(3, new Timestamp(p.getDateDebut().getTime()));
            pst.setTimestamp(4, new Timestamp(p.getDateFin().getTime()));
            pst.setInt(5, p.isParticipated() ? 1 : 0);
            pst.setInt(6, p.getId());
            pst.executeUpdate();
            System.out.println("Participation modifiée avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void accepter(int id) {
        try {
            String requete = "UPDATE participation SET participated=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, 1);
            pst.setInt(2, id);
            pst.executeUpdate();
            System.out.println("Participation modifiée avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    @Override
    public void supprimer(Participation p) {
        try {
            String requete = "DELETE FROM participation WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, p.getId());
            pst.executeUpdate();
            System.out.println("Participation supprimée avec succès !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Participation> afficher() {
        List<Participation> listParticipations = new ArrayList<>();
        try {
            String requete = "SELECT * FROM participation";
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(requete);
            while (rs.next()) {
                Participation p = new Participation(
                        rs.getInt("id"),
                        rs.getInt("id_club_id"),
                        rs.getInt("id_user_id"),
                        new Date(rs.getTimestamp("date_debut").getTime()),
                        new Date(rs.getTimestamp("date_fin").getTime()),
                        rs.getInt("participated") == 1
                );
                listParticipations.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listParticipations;
    }

    public boolean participationExiste(Club club, User user) {
        try {
            String requete = "SELECT * FROM participation WHERE id_club_id=? AND id_user_id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, club.getId());
            pst.setInt(2, user.getId());
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    public List<Participation> getParticipationsByClub(int clubId) {
        List<Participation> listParticipations = new ArrayList<>();
        try {
            String requete = "SELECT * FROM participation WHERE id_club_id = ?";
            PreparedStatement stm = cnx.prepareStatement(requete);
            stm.setInt(1, clubId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Participation p = new Participation(
                        rs.getInt("id"),
                        rs.getInt("id_club_id"),
                        rs.getInt("id_user_id"),
                        new Date(rs.getTimestamp("date_debut").getTime()),
                        new Date(rs.getTimestamp("date_fin").getTime()),
                        rs.getInt("participated") == 1
                );
                listParticipations.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listParticipations;
    }


    public List<Participation> afficheReservationByUser(User user) {
        List<Participation> listReservations = new ArrayList<>();
        try {
            String requete = "SELECT * FROM participation WHERE id_user_id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, user.getId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Participation p = new Participation(
                        rs.getInt("id"),
                        rs.getInt("id_club_id"),
                        rs.getInt("id_user_id"),
                        new Date(rs.getTimestamp("date_debut").getTime()),
                        new Date(rs.getTimestamp("date_fin").getTime()),
                        rs.getInt("participated") == 1
                );
                listReservations.add(p);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listReservations;
    }

    
    public String getEmailForParticipation(int participationId) {
    String email = null;
    try {
        String query = "SELECT u.email FROM participation p INNER JOIN user u ON p.id_user_id = u.id WHERE p.id = ?";
        PreparedStatement statement = cnx.prepareStatement(query);
        statement.setInt(1, participationId);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            email = rs.getString("email");
            System.out.println(email);
        }
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());
    }
    return email;
}




}
