package com.bourlaforme.entities;


import com.bourlaforme.utils.Constants;

import java.time.LocalDate;

public class Commande implements Comparable<Commande> {

    private int id;
    private int montant;
    private LocalDate date;
    private Panier panier;
    private BillingAddress address;
    private boolean confirmeAdmin;

    public Commande(int id) {
        this.id = id;
    }

    public Commande(int id, int montant, LocalDate date, Panier panier, BillingAddress address, boolean confirmeAdmin) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.panier = panier;
        this.address = address;
        this.confirmeAdmin = confirmeAdmin;
    }

    public Commande(int montant, LocalDate date, Panier panier, BillingAddress address, boolean confirmeAdmin) {
        this.montant = montant;
        this.date = date;
        this.panier = panier;
        this.address = address;
        this.confirmeAdmin = confirmeAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public BillingAddress getBillingAddress() {
        return address;
    }

    public void setBillingAddress(BillingAddress address) {
        this.address = address;
    }

    public boolean isConfirmeAdmin() {
        return confirmeAdmin;
    }

    public void setConfirmeAdmin(boolean confirmeAdmin) {
        this.confirmeAdmin = confirmeAdmin;
    }

    @Override
    public int compareTo(Commande commande) {
        switch (Constants.compareVar) {
            case "Montant":
                return Integer.compare(commande.getMontant(), this.getMontant());
            case "Date":
                return commande.getDate().compareTo(this.getDate());
            case "ConfirmeAdmin":
                return commande.isConfirmeAdmin() ? -1 : 1;

            default:
                return 0;
        }
    }
}