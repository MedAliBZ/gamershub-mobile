/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.entities;

/**
 *
 * @author LENOVO
 */
public class Matchs {
private int id;
    private String MatchName , Date;
    private String Result ;

    public Matchs(int id, String MatchName, String Date, String Result) {
        this.id = id;
        this.MatchName = MatchName;
        this.Date = Date;
        this.Result = Result;
    }

  

    public Matchs() {
       
    }

    public Matchs(String text, String text0, String text1) {
      this.MatchName = text;
        this.Date = text0;
        this.Result = text1;
       
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatchName() {
        return MatchName;
    }

    public void setMatchName(String MatchName) {
        this.MatchName = MatchName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }
    
 @Override
    public String toString() {
        return "Matchs{" + "id=" + id + ", MatchName=" + MatchName + ", Date=" + Date + ", Result=" + Result +'}';
    }
}
