/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.entities;

/**
 *
 * @author LENOVO
 */
public class Teams {
    private int id;
    private String TeamName,image;
    private int GamersNb , Rank;
    private boolean Verified; 


    public Teams(int id, String TeamName, int GamersNb, int Rank, boolean Verified , String image) {
        this.id = id;
        this.TeamName = TeamName;
        this.GamersNb = GamersNb;
        this.Rank = Rank;
        this.Verified = Verified;
        this.image = image;
    }

    public Teams(String TeamName, String image) {
        this.TeamName = TeamName;
        this.image = image;
       
    }

    public Teams() {
       
    }

    public Teams(String text, int text0, int text1, String text2) {
        this.TeamName = text;
        
        this.Rank = text0;
        this.GamersNb = text1;
        this.image = text2;

       
    }

    



    


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return TeamName;
    }
public void setTeamName(String TeamName) {
        this.TeamName=TeamName;
    }

    public int getGamersNb() {
        return GamersNb;
    }

    public void setGamersNb(int GamersNb) {
        this.GamersNb = GamersNb;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int Rank) {
        this.Rank = Rank;
    }

    public boolean isVerified() {
        return Verified;
    }

    public void setVerified(boolean Verified) {
        this.Verified = Verified;
    }
public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

   @Override
    public String toString() {
        return "Teams{" + "id=" + id + ", TeamName=" + TeamName + ", GamersNb=" + GamersNb + ", Rank=" + Rank +", Verified=" + Verified +", image=" + image + '}';
    }

  

}
