/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bourlaforme.entities;

/**
 *
 * @author hadil
 */
public class Seance {
    private int id;
    private int nbr_grp;
    private String description;
    private int nbr_seance;
    private User user;
    private int total_reservations;
    private double avg_rating;

    public int getTotal_reservations() {
        return total_reservations;
    }

    public void setTotal_reservations(int total_reservations) {
        this.total_reservations = total_reservations;
    }

    public double getAvg_rating() {
        return avg_rating;
    }

    public void setAvg_rating(double avg_rating) {
        this.avg_rating = avg_rating;
    }
    

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String color;
    private String titre;


    
        public Seance(int nbr_grp, String description, int nbr_seance, String titre,User user) {
        this.nbr_grp = nbr_grp;
        this.description = description;
        this.nbr_seance = nbr_seance;
        this.titre = titre;
        this.user = user;
                
        
    }

    // Constructeur
    public Seance(int id, int nbr_grp, String description, int nbr_seance,User user, String color, String titre) {
        this.id = id;
        this.nbr_grp = nbr_grp;
        this.description = description;
        this.nbr_seance = nbr_seance;
        this.user = user;
        this.color = color;
        this.titre = titre;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbr_grp() {
        return nbr_grp;
    }

    public void setNbr_grp(int nbr_grp) {
        this.nbr_grp = nbr_grp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbr_seance() {
        return nbr_seance;
    }

    public void setNbr_seance(int nbr_seance) {
        this.nbr_seance = nbr_seance;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    @Override
    public String toString() {
        return "Seance{" + "id=" + id + ", nbr_grp=" + nbr_grp + ", description=" + description + ", nbr_seance=" + nbr_seance + ", user_id=" + user.getId() + ", color=" + color + ", titre=" + titre + '}';
    }
    
}

