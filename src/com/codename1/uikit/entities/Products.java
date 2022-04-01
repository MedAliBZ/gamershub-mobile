/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.entities;

/**
 *
 * @author meriam
 */
public class Products {
   private int id,categoryId,quantityStocked;
    private String nameProduct, image;
    private String Description;
    private Double price;

    public Products(int id, int categoryId, int quantityStocked, String nameProduct, String image, String Description, Double price) {
        this.id = id;
        this.categoryId = categoryId;
        this.quantityStocked = quantityStocked;
        this.nameProduct = nameProduct;
        this.image = image;
        this.Description = Description;
        this.price = price;
    }
    

   

    public Products() {

    }

    @Override
    public String toString() {
        return "product{" + "id=" + id + ",categoryId=" + categoryId + ",image=" + image + ",Description=" + Description +",quantityStocked=" + quantityStocked + ",nameProduct=" + nameProduct + ",price=" + price + "}";
    } 

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getQuantityStocked() {
        return quantityStocked;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return Description;
    }

    public Double getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setQuantityStocked(int quantityStocked) {
        this.quantityStocked = quantityStocked;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
 
}
