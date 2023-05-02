package com.bourlaforme.entities;

import com.bourlaforme.utils.Constants;

public class Panier implements Comparable<Panier> {

    private int id;
    private Commande commande;
    private int prix;
    private int quantity;
    private boolean confirme;

    public Panier() {
    }

    public Panier(int id, Commande commande, int prix, int quantity, boolean confirme) {
        this.id = id;
        this.commande = commande;
        this.prix = prix;
        this.quantity = quantity;
        this.confirme = confirme;
    }

    public Panier(Commande commande, int prix, int quantity, boolean confirme) {
        this.commande = commande;
        this.prix = prix;
        this.quantity = quantity;
        this.confirme = confirme;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean getConfirme() {
        return confirme;
    }

    public void setConfirme(boolean confirme) {
        this.confirme = confirme;
    }


    @Override
    public int compareTo(Panier panier) {
        switch (Constants.compareVar) {
            case "Prix":
                return Integer.compare(panier.getPrix(), this.getPrix());
            case "Quantity":
                return Integer.compare(panier.getQuantity(), this.getQuantity());
            default:
                return 0;
        }
    }

}