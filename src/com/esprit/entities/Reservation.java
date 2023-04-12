/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;

/**
 *
 * @author hadil
 */
import java.time.LocalDate;

public class Reservation {
    private int id;
    private int user_id;
    private Seance seance;
    private LocalDate date;

    // Constructeur
    public Reservation(int id, int user_id, Seance seance, LocalDate date) {
        this.id = id;
        this.user_id = user_id;
        this.seance = seance;
        this.date = date;
    }

    public Reservation(int user_id, Seance seance, LocalDate date) {
        this.user_id = user_id;
        this.seance = seance;
        this.date = date;
    }
    

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Seance getSeance() {
        return seance;
    }

    public void setSeance(Seance seance) {
        this.seance = seance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Reservation() {
    }
}
