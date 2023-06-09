package com.bourlaforme.entities;

import com.bourlaforme.utils.Constants;

public class Article implements Comparable<Article> {

    private int id;
    private String nom;
    private String description;
    private String image;
    private int prix;
    private String etat;

    public Article(int id, String nom, String description, String image, int prix, String etat) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.etat = etat;
    }

    public Article(String nom, String description, String image, int prix, String etat) {
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix = prix;
        this.etat = etat;
    }

    public Article() {
    }

 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }


    @Override
    public int compareTo(Article article) {
        switch (Constants.compareVar) {
            case "Nom":
                return article.getNom().compareTo(this.getNom());
            case "Description":
                return article.getDescription().compareTo(this.getDescription());
            case "Image":
                return article.getImage().compareTo(this.getImage());
            case "Prix":
                return Integer.compare(article.getPrix(), this.getPrix());
            case "Etat":
                return article.getEtat().compareTo(this.getEtat());

            default:
                return 0;
        }
    }

}