/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Reclamation;

import edu.esprit.utils.DataSource;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class ServiceReclamation implements IService<Reclamation> {


    
     Connection cnx ;

    public ServiceReclamation() {
        cnx= DataSource.getInstance().getCnx();
    }
    

    @Override
    public void ajouter(Reclamation t) {
         try {
            String req = "INSERT INTO reclamation( `nom`  , `email` , `sujet` , `etat` , `description` ) VALUES (?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
           
            ps.setString(1, t.getNom());
          ps.setString(2, t.getEmail());
           ps.setString(3, t.getSujet());
            ps.setString(4, (String) t.getEtat());
            ps.setString(5, t.getDescription());
            
            //ps.setInt(7, t.reclamation().get());
            //ps.setInt(7,23);
            ps.executeUpdate();
            System.out.println("reclamation added ");
        
     SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png"); // chemin vers une icône pour la notification
            TrayIcon trayIcon = new TrayIcon(image, "Nouvelle reclamation");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Nouvelle reclamation Ajouter");
//        try {
//            tray.add(trayIcon);
//        } catch (AWTException ex) {
//            System.err.println(ex.getMessage());
//        }
            trayIcon.displayMessage("Nouvelle reclamation ajoutée", "Une nouvelle reclamation a été ajoutée pour vous , veillez consulter ceci.", TrayIcon.MessageType.INFO);

  
              
//        
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
       
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM reclamation WHERE id = ? ";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("reclamation deleted");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
            // Afficher une notification système pour l'administrateur
            SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png"); // chemin vers une icône pour la notification
            TrayIcon trayIcon = new TrayIcon(image, "Nouvelle Reclamation");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Nouvelle Reclamation Ajouter");
         try {
             tray.add(trayIcon);
         } catch (AWTException ex) {
             Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
         }
            trayIcon.displayMessage("Nouvelle reclamation ajoutée", "Une nouvelle reclamation a été ajoutée pour vous , veillez consulter ceci.", TrayIcon.MessageType.INFO);

    }

  

           

      @Override
    public void modifier(Reclamation t) {
        try{

           String req = "UPDATE reclamation SET id = '" + t.getId() + "', nom = '" + t.getNom() + "', email = '" + t.getEmail() + "', sujet = '" + t.getSujet() + "',etat  = '" + t.getEtat() + "',description   = '" + t.getDescription() + "' WHERE reclamation.`id` = " + t.getId();

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("reclamation updated ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    @Override
    public List<Reclamation> getAll() {
        List<Reclamation> list = new ArrayList<>();
        try {
            String req = "Select * FROM Reclamation";
            //String req = "SELECT *  FROM reponse s JOIN Reclamation ON 
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
              //  Reclamation t = new Reclamation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getDate(5), rs.getString(6));
                
                 while (rs.next()) {
                Reclamation t = new Reclamation();
                t.setNom(rs.getString("nom"));
                t.setEmail(rs.getString("email"));
                t.setSujet(rs.getString("sujet"));
                t.setEtat(rs.getString("etat"));
                t.setDescription(rs.getString("description"));
               
                list.add(t);
            }
                
                
             
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }

    public void update(Reclamation c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
