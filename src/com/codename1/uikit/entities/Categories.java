/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.entities;

/**
 *
 * @author meriam
 */
public class Categories {

    private int id;
    private String nameCategory, image;
    private String Description;

    public Categories(String nameCategory, String Description, String image) {

        this.nameCategory = nameCategory;
        this.image = image;
        this.Description = Description;
    }

    public Categories() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "category{" + "id=" + id + ",nameCategory=" + nameCategory + ",image=" + image + ",Description=" + Description + "}";
    }

}
