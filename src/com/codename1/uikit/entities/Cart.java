/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.entities;

/**
 *
 * @author meriam
 */
public class Cart {
    private int id,quantity,orderId;
    private Products product;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }


    public Cart() {
    }

    public Cart(Products product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public Products getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct(Products product) {
        this.product= product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
     
 @Override
    public String toString() {
        return "cart{" + "id=" + id + ",product=" + product + ",quantity=" + quantity + "}";
    }

}
