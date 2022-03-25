/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.entities;

/**
 *
 * @author hp
 */
public class Coach {
    private int id;
     private String username,description,gamename;
     
     public Coach(){
     }
     
     public Coach(int id, String description, String gamename, String username) {
        this.id = id;
        this.description = description;
        this.gamename = gamename;
        this.username = username;
    }
     
     @Override
    public String toString() {
        return "Coach{" + "id=" + id + ", coachname=" + username + ", gamename=" + gamename + ", description=" + description + '}';
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getGamename() {
        return gamename;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
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


    
}
