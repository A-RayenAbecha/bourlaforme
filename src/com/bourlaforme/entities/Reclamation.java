package com.bourlaforme.entities;

import com.bourlaforme.utils.Constants;
import com.bourlaforme.utils.RelationObject;

import java.time.LocalDate;

public class Reclamation implements Comparable<Reclamation> {

    private int id;
    private User user;
    private User coach;
    private RelationObject club;
    private RelationObject article;
    private LocalDate dateReclamation;
    private String etat;
    private String reponse;
    private String type;
    private String message;
    
    public Reclamation(int id, User user, User coach, RelationObject club, RelationObject article, LocalDate dateReclamation, String etat, String reponse, String type, String message) {
        this.id = id;
        this.user = user;
        this.coach = coach;
        this.club = club;
        this.article = article;
        this.dateReclamation = dateReclamation;
        this.etat = etat;
        this.reponse = reponse;
        this.type = type;
        this.message = message;
    }

    public Reclamation(User user, User coach, RelationObject club, RelationObject article, LocalDate dateReclamation, String etat, String reponse, String type, String message) {
        this.user = user;
        this.coach = coach;
        this.club = club;
        this.article = article;
        this.dateReclamation = dateReclamation;
        this.etat = etat;
        this.reponse = reponse;
        this.type = type;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }
    
    public RelationObject getClub() {
        return club;
    }

    public void setClub(RelationObject club) {
        this.club = club;
    }
    
    public RelationObject getArticle() {
        return article;
    }

    public void setArticle(RelationObject article) {
        this.article = article;
    }
    
    public LocalDate getDateReclamation() {
        return dateReclamation;
    }

    public void setDateReclamation(LocalDate dateReclamation) {
        this.dateReclamation = dateReclamation;
    }
    
    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
    
    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    

    @Override
    public int compareTo(Reclamation reclamation) {
        switch (Constants.compareVar) {
            case "User":
                return Integer.compare(reclamation.getUser().getId(), this.getUser().getId());
            case "Coach":
                return Integer.compare(reclamation.getCoach().getId(), this.getCoach().getId());
            case "Club":
                return Integer.compare(reclamation.getClub().getId(), this.getClub().getId());
            case "Article":
                return Integer.compare(reclamation.getArticle().getId(), this.getArticle().getId());
            case "DateReclamation":
                 return reclamation.getDateReclamation().compareTo(this.getDateReclamation());
            case "Etat":
                 return reclamation.getEtat().compareTo(this.getEtat());
            case "Reponse":
                 return reclamation.getReponse().compareTo(this.getReponse());
            case "Type":
                 return reclamation.getType().compareTo(this.getType());
            case "Message":
                 return reclamation.getMessage().compareTo(this.getMessage());
            
            default:
                return 0;
        }
    }
    
}