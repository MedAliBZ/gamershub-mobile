/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.entities;

/**
 *
 * @author amira
 */
public class Tournaments {
    private int id, teamSize;
    private String name, description,image;
    
    public Tournaments() {
    }

    public Tournaments(int id, int teamSize, String name, String description) {
        this.id = id;
        this.teamSize = teamSize;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Tournaments{" + "id=" + id + ", teamSize=" + teamSize + ", name=" + name + ", description=" + description + '}';
    }
    
}
