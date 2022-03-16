/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.gui;

import com.codename1.ui.Form;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.User;

/**
 *
 * @author MAB
 */
public class HomeTemplate extends Form{
    public HomeTemplate(Form page, String pageTitle){
        page.setTitle(pageTitle);
        page.getToolbar().addCommandToLeftSideMenu("Profile", null, (e)->{
            Form p1 = new ProfileForm();
            p1.show();
        });
        page.getToolbar().addCommandToLeftSideMenu("Games", null, (e)->{
            Form p1 = new ListGamesForm();
            p1.show();
        });
        page.getToolbar().addCommandToLeftSideMenu("Logout", null, (e)->{
            MyApplication.loggedUser = new User();
            Form p1 = new LoginForm();
            p1.show();
        });
    }
    
}
