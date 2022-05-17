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
public class Player {
    private int id;
    private String rank;
    private int user;

    public Player() {
    }

    public Player(int id, String rank, int user) {
        this.id = id;
        this.rank = rank;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getRank() {
        return rank;
    }

    public int getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setUser(int user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Player{" + "id=" + id + ", rank=" + rank + ", user=" + user + '}';
    }
    
}
