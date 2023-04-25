package com.bourlaforme.entities;


public class PanierArticle {

    private int id;
    private Article article;
    private Panier panier;
    private int quantity;

    public PanierArticle(int id, Article article, Panier panier, int quantity) {
        this.id = id;
        this.article = article;
        this.panier = panier;
        this.quantity = quantity;
    }

    public PanierArticle(Article article, Panier panier, int quantity) {
        this.article = article;
        this.panier = panier;
        this.quantity = quantity;
    }

    public PanierArticle() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}