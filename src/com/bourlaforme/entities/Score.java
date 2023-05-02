package com.bourlaforme.entities;

public class Score {

    private int id;
    private User coach;
    private User user;
    private int note;

    public Score(int id, User coach, User user, int note) {
        this.id = id;
        this.coach = coach;
        this.user = user;
        this.note = note;
    }

    public Score(User coach, User user, int note) {
        this.coach = coach;
        this.user = user;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getCoach() {
        return coach;
    }

    public void setCoach(User coach) {
        this.coach = coach;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }


}