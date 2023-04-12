/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Reponse;
import edu.esprit.utils.DataSource;
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
 
/**
 *
 * @author HP
 */
public class Service implements IService<Reponse> {

    Connection cnx ;

    public Service() {
        cnx= DataSource.getInstance().getCnx();
    }
    

    @Override
    public void ajouter(Reponse t) {
//       

        try {
            String req = "INSERT INTO reponse( `sujet`  , `motif` , `avis` , `date` , `equipement_s` , `commentaire_s`) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = cnx.prepareStatement(req);
           
            ps.setString(1, t.getSujet());
            ps.setString(2, t.getMotif());
            ps.setString(3, t.getAvis());
            ps.setDate(4, (Date) t.getDate());
            
            //ps.setInt(7, t.getreclamation().get());
            //ps.setInt(7,23);
            ps.executeUpdate();
            System.out.println("reponse added ");
              SystemTray tray = SystemTray.getSystemTray();
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage("icon.png"); // chemin vers une icône pour la notification
            TrayIcon trayIcon = new TrayIcon(image, "Nouvelle reponse");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("Nouvelle reponse Ajouter");
//        try {
//            tray.add(trayIcon);
//        } catch (AWTException ex) {
//            System.err.println(ex.getMessage());
//        }
            trayIcon.displayMessage("Nouvelle reponse ajoutée", "Une nouvelle reponse a été ajoutée pour vous , veillez consulter ceci.", TrayIcon.MessageType.INFO);

  
                // Send SMS to user
        
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
            // Afficher une notification système pour l'administrateur
          
    
    }
    

    @Override
    public void supprimer(int idr) {
        try {
            String req = "DELETE FROM reponse WHERE idr = ? ";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, idr);
            st.executeUpdate();
            System.out.println("reponse deleted");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }      

    @Override
    public void modifier(Reponse t) {
        try{

String req = "UPDATE reponse SET idr = '" + t.getIdr() + "', sujet = '" + t.getSujet() + "', motif = '" + t.getMotif() + "', avis = '" + t.getAvis() + "',date  = '" + t.getDate() + "' WHERE station.`idr` = " + t.getIdr();

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Reponse updated ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
        @Override
    public List<Reponse> getAll() {
        List<Reponse> list = new ArrayList<>();
        try {
            String req = "Select * from Reponse";
//            String req = "Select   s.sujet, s.motif , s.avis , s. , s.equipement_s , s.commentaire_s ";
//             from reclamation c "+"JOIN reponse 
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            
            while (rs.next()) {
                Reponse t = new Reponse();
                t.setSujet(rs.getString("sujet"));
                t.setMotif(rs.getString("motif"));
                t.setAvis(rs.getString("avis"));
                t.setDate(rs.getDate("date"));
                
                //reclamation c = new Reclamation(rs.getString(""));
                ///t.setreclamation(c);
                list.add(t);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return list;
    }

    public void supprimerReponse(Reponse reponse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
