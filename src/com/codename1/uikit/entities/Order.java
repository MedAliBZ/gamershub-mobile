/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.entities;

/**
 *
 * @author meriam
 */
public class Order {
    private int id,userId;
    private Double totalprice;

    public Order() {
    }

    public Order(int userId, Double totalprice) {
        this.userId = userId;
        this.totalprice = totalprice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Double getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Double totalprice) {
        this.totalprice = totalprice;
    }


 @Override
    public String toString() {
        return "cart{" + "id=" + id + ",userId=" + userId + ",totalprice=" + totalprice + "}";
    }

    
}
