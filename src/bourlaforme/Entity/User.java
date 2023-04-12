/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bourlaforme.Entity;

import java.lang.reflect.Array;

/**
 *
 * @author aziz3
 */
public class User {
    private int id;
    private int is_coach;
    private int approved;
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private Array roles;
    private String role;
    private String certificates;
    private String specialite;
    private String experiance;
    private String description;
    

    public User(int id, String email, String nom, String prenom, String certificates, String specialite, String experiance, String description) {
        this.id = id;
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.certificates = certificates;
        this.specialite = specialite;
        this.experiance = experiance;
        this.description = description;
    }

    public User() {
    }
    

    

    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }

    public int getIs_coach() {
        return is_coach;
    }

    public int getApproved() {
        return approved;
    }

    

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Array getRoles() {
        return roles;
    }

    public String getCertificates() {
        return certificates;
    }

    public String getSpecialite() {
        return specialite;
    }

    public String getExperiance() {
        return experiance;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setIs_coach(int is_coach) {
        this.is_coach = is_coach;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }

    

    public void setRole(String role) {
        this.role = role;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setRoles(Array roles) {
        this.roles = roles;
    }

    public void setCertificates(String certificates) {
        this.certificates = certificates;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

   

    public void setExperiance(String experiance) {
        this.experiance = experiance;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    
    
}
