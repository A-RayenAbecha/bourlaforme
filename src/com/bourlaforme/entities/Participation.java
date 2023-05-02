package com.bourlaforme.entities;

import java.util.Date;

public class Participation {
    private int id;
    private int idClub;
    private int idUser;
    private Date dateDebut;
    private Date dateFin;
    private boolean participated;

    public Participation(int id, int idClub, int idUser, Date dateDebut, Date dateFin, boolean participated) {
        this.id = id;
        this.idClub = idClub;
        this.idUser = idUser;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.participated = participated;
    }

    public Participation(int idClub, int idUser, Date dateDebut, Date dateFin, boolean participated) {
        this.idClub = idClub;
        this.idUser = idUser;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.participated = participated;
    }

    public Participation() {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isParticipated() {
        return participated;
    }

    public void setParticipated(boolean participated) {
        this.participated = participated;
    }
}
