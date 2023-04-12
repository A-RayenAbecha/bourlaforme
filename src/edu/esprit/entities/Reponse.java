/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import static java.sql.JDBCType.DATE;
import static java.sql.Types.DATE;
import java.time.Instant;
import static java.util.Calendar.DATE;
import java.util.Date;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author HP
 */
public class Reponse {

    private int idr;
    private String sujet;
    private String motif;
    private String avis;
    private Date date;
    
    private Reclamation reclamation ;

    public Reponse() {

    }

    public Reponse(String sujet, String motif, String avis, Date date) {
        this.sujet = sujet;
        this.motif = motif;
        this.avis = avis;
        this.date = date;
        
    }

    public Reponse(int idr, String sujet, String motif, String avis, Date date) {
        this.idr = idr;
        this.sujet = sujet;
        this.motif = motif;
        this.avis = avis;
        this.date = date;
       
    }

    @Override
    public String toString() {
        return "Reponse{" + "idr=" + idr + ", sujet=" + sujet + ", motif=" + motif + ", avis=" + avis + ", date=" + date + ", reclamation=" + reclamation + '}';
    }

   
    

    public Reponse(String sujet, String motif, String avis, Date date, Reclamation reclamation) {
        this.sujet = sujet;
        this.motif = motif;
        this.avis = avis;
        this.date = date;
        
        this.reclamation = reclamation;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }


    public int getIdr() {
        return idr;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

  
   public boolean isValid() {
        
        if (sujet == null || sujet.isEmpty()) {
            return false;
        }
        if (avis == null || avis.isEmpty()) {
            return false;
        }
        if (motif == null || motif.isEmpty()) {
            return false;
        }
        
        return true;
    }

    public void setIdr(int idr) {
        this.idr = idr;
    }
    public int getId(int id){
        return this.reclamation.getId();
    }
    
   

}
