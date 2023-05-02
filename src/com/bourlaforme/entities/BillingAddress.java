package com.bourlaforme.entities;



import java.time.LocalDate;

public class BillingAddress {
    
    private int id;
    private String nom;
    private String email;
    private String address;
    private int phone;
    private String description;
    
    public BillingAddress(int id, String nom, String email, String address, int phone, String description) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.description = description;
    }

    public BillingAddress(String nom, String email, String address, int phone, String description) {
        this.nom = nom;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.description = description;
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
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    

    
}