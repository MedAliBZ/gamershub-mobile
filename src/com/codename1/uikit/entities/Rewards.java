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
public class Rewards {
    
    private int id, quantity;
    private String type;

    public Rewards() {
    }

    public Rewards(int id, int quantity, String type) {
        this.id = id;
        this.quantity = quantity;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Rewards{" + "id=" + id + ", quantity=" + quantity + ", type=" + type + '}';
    }
    
    
    
}
