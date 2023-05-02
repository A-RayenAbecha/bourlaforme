/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bourlaforme.services;

/**
 *
 * @author hadil
 */
import com.bourlaforme.entities.Reservation;
import com.bourlaforme.entities.Seance;
import com.bourlaforme.entities.User;
import com.esprit.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationService implements IService<Reservation> {
    
    private Connection cnx = DataSource.getInstance().getCnx();
    private ServiceSeance seanceService = new ServiceSeance();

    @Override
    public void ajouter(Reservation r) {
        try {
            // Vérifier que le nombre de réservations pour cette séance ne dépasse pas nbr_grp
            int nbrReservations = getNombreReservations(r.getSeance().getId());
            Seance seance = seanceService.chercher(r.getSeance().getId());
            if (nbrReservations >= seance.getNbr_grp()) {
                System.out.println("Impossible d'ajouter la réservation : nombre maximum de réservations atteint pour cette séance.");
                return;
            }
            String requete = "INSERT INTO reservation (id_user_id, seance_id, date) VALUES (?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, r.getUser_id());
            pst.setInt(2, r.getSeance().getId());
            pst.setDate(3, java.sql.Date.valueOf(r.getDate()));
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                r.setId(rs.getInt(1));
            }
            System.out.println("Réservation ajoutée avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Reservation r) {
        try {
            String requete = "DELETE FROM reservation WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, r.getId());
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Réservation supprimée avec succès !");
            } else {
                System.out.println("Aucune réservation trouvée avec l'identifiant spécifié.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void incr_annulation(Reservation r) {
        try {
            String requete = "UPDATE user SET nbr_annulation = nbr_annulation + 1 WHERE user.id = (SELECT user.id FROM reservation r JOIN seance s ON r.seance_id = s.id JOIN user ON s.user_id = user.id WHERE r.id = ?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, r.getId());
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("Réservation supprimée avec succès !");
            } else {
                System.out.println("Aucune réservation trouvée avec l'identifiant spécifié.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
public int getnbr_reservationByUser(int user_id) {
    int nbr_reservation = 0;
    try {
        String requete = "SELECT nbr_annulation FROM user WHERE user_id=?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setInt(1, user_id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            nbr_reservation = rs.getInt("nbr_annulation");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return nbr_reservation;
}
    public int getNombreReservations(int seanceId) throws SQLException {
        int nbrReservations = 0;
        String requete = "SELECT COUNT(*) AS nb FROM reservation WHERE seance_id=?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setInt(1, seanceId);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            nbrReservations = rs.getInt("nb");
        }
        return nbrReservations;
    }

    @Override
    public void modifier(Reservation r)  {
        try {
            String requete = "UPDATE reservation SET id_user_id=?, seance_id=?, date=? WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, r.getUser_id());
            pst.setInt(2, r.getSeance().getId());
            pst.setDate(3, java.sql.Date.valueOf(r.getDate()));
            pst.setInt(4, r.getId());
            pst.executeUpdate();
            System.out.println("Réservation modifiée avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
}



    @Override
    public List<Reservation> afficher() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String requete = "SELECT * FROM reservation";
            PreparedStatement pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setUser_id(rs.getInt("id_user_id"));
                Seance seance = seanceService.chercher(rs.getInt("seance_id"));
                r.setSeance(seance);
                r.setDate(rs.getDate("date").toLocalDate());
                reservations.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reservations;
    }


    public List<Reservation> getReservationsByUser(User user) {
        List<Reservation> reservations = new ArrayList<>();
        try {
            String requete = "SELECT * FROM reservation WHERE id_user_id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, user.getId());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setId(rs.getInt("id"));
                r.setUser_id(rs.getInt("id_user_id"));
                Seance seance = seanceService.chercher(rs.getInt("seance_id"));
                r.setSeance(seance);
                r.setDate(rs.getDate("date").toLocalDate());
                reservations.add(r);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return reservations;
    }

    public boolean existeReservation(int idSeance, int idUser) {
        try {
            String requete = "SELECT COUNT(*) AS nb FROM reservation WHERE seance_id=? AND id_user_id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, idSeance);
            pst.setInt(2, idUser);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int nb = rs.getInt("nb");
                return nb > 0;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }
    
    
    public boolean existsRating(int idUser, int idSeance) {
        try {
            String requete = "SELECT COUNT(*) AS nb FROM rating WHERE id_user=? AND id_seance=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1, idUser);
            pst.setInt(2, idSeance);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int nb = rs.getInt("nb");
                return nb > 0;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public void ajouterRating(int idUser, int idSeance, int note) {
        try {
            if (existsRating(idUser, idSeance)) {
                String requete = "UPDATE rating SET note=? WHERE id_user=? AND id_seance=?";
                PreparedStatement pst = cnx.prepareStatement(requete);
                pst.setInt(1, note);
                pst.setInt(2, idUser);
                pst.setInt(3, idSeance);
                pst.executeUpdate();
            } else {
                String requete = "INSERT INTO rating(id_user, id_seance, note) VALUES (?, ?, ?)";
                PreparedStatement pst = cnx.prepareStatement(requete);
                pst.setInt(1, idUser);
                pst.setInt(2, idSeance);
                pst.setInt(3, note);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public int getNoteByUserAndSeance(int idUser, int idSeance) {
    try {
        String requete = "SELECT note FROM rating WHERE id_user=? AND id_seance=?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setInt(1, idUser);
        pst.setInt(2, idSeance);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            return rs.getInt("note");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return -1; // or any other default value to indicate that the rating was not found
}
    
    public boolean deleteRating(int idUser, int idSeance) {
    try {
        String requete = "DELETE FROM rating WHERE id_user=? AND id_seance=?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setInt(1, idUser);
        pst.setInt(2, idSeance);
        int nbRowsAffected = pst.executeUpdate();
        return nbRowsAffected > 0;
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return false;
}

    //yarjaa 0
    public void resetNbrAnnulation(User user) {
    try {
        String requete = "UPDATE user SET nbr_annulation = 0 WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(requete);
        pst.setInt(1, user.getId());
        pst.executeUpdate();
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    
    public int getNbrIncrementation(int userId) {
    int nbrIncrementation = 0;
    try {
        String query = "SELECT nbr_annulation FROM user WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            nbrIncrementation = rs.getInt("nbr_annulation");
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return nbrIncrementation;
}





}
