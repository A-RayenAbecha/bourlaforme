/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.entities;

import java.util.Date;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import static java.sql.JDBCType.DATE;
import static java.sql.Types.DATE;
import java.time.Instant;
import static java.util.Calendar.DATE;

import javafx.scene.shape.Rectangle;

/**
 *
 * @author HP
 */
public class Reclamation {
     private int id;
    private String nom;
    private String email;
    private String sujet;
    private String etat;
    private String description;
    
    
   public Reclamation() {
}

    public Reclamation(String nom, String email, String sujet, String etat, String description) {
        this.nom = nom;
        this.email = email;
        this.sujet = sujet;
        this.etat = etat;
        this.description = description;
    }

    public Reclamation(int id, String nom, String email, String sujet, String etat, String description) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.sujet = sujet;
        this.etat = etat;
        this.description = description;
    }

    public Reclamation(String string) {
    }

    

   
    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", nom=" + nom + ", email=" + email + ", sujet=" + sujet + ", etat=" + etat + ", description=" + description + '}';
    }

    public int getId() {
        return id;
    }

    

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

public boolean isValid() {
    if (nom == null || nom.isEmpty()) {
        return false;
    }
    
   
   
    return true;
}
    
  
   

   
   
    
    
}
