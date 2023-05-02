package com.bourlaforme.entities;


import com.bourlaforme.utils.RelationObject;

import java.time.LocalDate;

public class Commentaire {

    private int id;
    private RelationObject article;
    private String auteur;
    private String contenu;
    private LocalDate date;

    public Commentaire(int id, RelationObject article, String auteur, String contenu, LocalDate date) {
        this.id = id;
        this.article = article;
        this.auteur = auteur;
        this.contenu = contenu;
        this.date = date;
    }

    public Commentaire(RelationObject article, String auteur, String contenu, LocalDate date) {
        this.article = article;
        this.auteur = auteur;
        this.contenu = contenu;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RelationObject getArticle() {
        return article;
    }

    public void setArticle(RelationObject article) {
        this.article = article;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}