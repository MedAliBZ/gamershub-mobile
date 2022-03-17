/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.entities;

/**
 *
 * @author MAB
 */
public class Game {
    private int id;
    private String image,name,description;
    private boolean isLiked;

    public void setIsLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean isIsLiked() {
        return isLiked;
    }

    public Game() {
    }

    public Game(int id, String image, String name, String description) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Game{" + "id=" + id + ", image=" + image + ", name=" + name + ", description=" + description + '}';
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
