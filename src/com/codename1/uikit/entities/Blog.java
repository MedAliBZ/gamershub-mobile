/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.entities;

/**
 *
 * @author amira
 */
public class Blog {
private int id;
  private String title;
  private String description;
  private String image;
  private int views;
  private int user;

    public Blog() {
    }

    public Blog(int id, String title, String description, String image, int views, int user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.views = views;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
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

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", title=" + title + ", description=" + description + ", image=" + image + ", views=" + views +  ", user=" + user + '}';
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
    
    
}
