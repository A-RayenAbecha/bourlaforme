package com.bourlaforme.entities;


public class User {

    private int id;
    private String email;
    private String roles;
    private String password;
    private String nom;
    private String prenom;
    private String image;
    private String certificates;
    private String specialite;
    private String experiance;
    private String description;
    private boolean isCoach;
    private boolean approved;
    private String likes;
    private float moyenne;

    public User(int id, String email, String roles, String password, String nom, String prenom, String image, String certificates, String specialite, String experiance, String description, boolean isCoach, boolean approved, String likes, float moyenne) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.image = image;
        this.certificates = certificates;
        this.specialite = specialite;
        this.experiance = experiance;
        this.description = description;
        this.isCoach = isCoach;
        this.approved = approved;
        this.likes = likes;
        this.moyenne = moyenne;
    }

    public User(String email, String roles, String password, String nom, String prenom, String image, String certificates, String specialite, String experiance, String description, boolean isCoach, boolean approved, String likes, float moyenne) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.image = image;
        this.certificates = certificates;
        this.specialite = specialite;
        this.experiance = experiance;
        this.description = description;
        this.isCoach = isCoach;
        this.approved = approved;
        this.likes = likes;
        this.moyenne = moyenne;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCertificates() {
        return certificates;
    }

    public void setCertificates(String certificates) {
        this.certificates = certificates;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getExperiance() {
        return experiance;
    }

    public void setExperiance(String experiance) {
        this.experiance = experiance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsCoach() {
        return isCoach;
    }

    public void setIsCoach(boolean isCoach) {
        this.isCoach = isCoach;
    }

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(float moyenne) {
        this.moyenne = moyenne;
    }

    @Override
    public String toString() {
        return email;
    }
}