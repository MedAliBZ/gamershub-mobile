/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.entities;

import java.util.Date;

/**
 *
 * @author hp
 */
public class Sessioncoaching {
    private int id;
    private String username,description,coach;
    private String prix;
    private String date_debut,date_fin;

    public Sessioncoaching() {
    }

    public Sessioncoaching(int id, String username, String description, String coach, String prix, String date_debut, String date_fin) {
        this.id = id;
        this.username = username;
        this.description = description;
        this.coach = coach;
        this.prix = prix;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "Sessioncoaching{" + "id=" + id + ", username=" + username + ", description=" + description + ", coach=" + coach + ", prix=" + prix + ", date_debut=" + date_debut + ", date_fin=" + date_fin + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }
    
    
        
    
}
