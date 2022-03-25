/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Coach;
import com.codename1.uikit.entities.Game;
import com.codename1.uikit.services.ServiceCoachs;
import com.codename1.uikit.services.ServiceGames;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class AddCoachForm extends Form {
    private Coach coach;

    public AddCoachForm(Coach coach, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        if (coach.getUsername() != null) {
            this.getToolbar().setTitle("Update coach");
         
            this.coach = coach;
            Container descriptionContainer = new Container(BoxLayout.y());
            Container titleContainer = new Container(BoxLayout.x());
            Container gameContainer = new Container(BoxLayout.y());

            TextArea descriptionTA = new TextArea(coach.getDescription(), 10,100);
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);

            TextArea titleTA = new TextArea(coach.getUsername());
            titleTA.getAllStyles().setAlignment(TextArea.CENTER);
            titleTA.setEditable(false);
            titleTA.setUIID("Label");
            
            ComboBox<String> gamesCB = new ComboBox<>();
            ArrayList<Game> gamesList = ServiceGames.getInstance().getAllGames();
            for (Game g : gamesList) {
                gamesCB.addItem(g.getName());
            }
            
            gamesCB.setSelectedItem(coach.getGamename());
            
            gameContainer.addAll(new Label("Game"),gamesCB);

            Button submitBtn = new Button("Submit");

            descriptionContainer.addAll(new Label("Description"), descriptionTA);
            titleContainer.addAll(new Label("Name : "), titleTA);
            this.addAll(titleContainer, descriptionContainer, gameContainer, submitBtn);
            submitBtn.addActionListener(e->{
                ServiceCoachs.getInstance().updateCoach(descriptionTA.getText(), gamesCB.getSelectedItem());
                new ListCoachsForm().show();
            });

        } else {
            this.getToolbar().setTitle("Become a coach");

            Container descriptionContainer = new Container(BoxLayout.y());
            Container titleContainer = new Container(BoxLayout.x());
            Container gameContainer = new Container(BoxLayout.y());
            
            TextArea descriptionTA = new TextArea("", 10,100);
            descriptionTA.setMaxSize(9999);
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);

            TextArea titleTA = new TextArea(MyApplication.loggedUser.getUsername());
            titleTA.getAllStyles().setAlignment(TextArea.CENTER);
            titleTA.setEditable(false);
            titleTA.setUIID("Label");

            Button submitBtn = new Button("Submit");

            descriptionContainer.addAll(new Label("Description"), descriptionTA);
            titleContainer.addAll(new Label("Name: "), titleTA);
            
            ComboBox<String> gamesCB = new ComboBox<>();
            ArrayList<Game> gamesList = ServiceGames.getInstance().getAllGames();
            for (Game g : gamesList) {
                gamesCB.addItem(g.getName());
            }
            submitBtn.addActionListener(e->{
                ServiceCoachs.getInstance().addCoach(descriptionTA.getText(), gamesCB.getSelectedItem());
                new ListCoachsForm().show();
            });
            gameContainer.addAll(new Label("Game"),gamesCB);
            this.addAll(titleContainer, descriptionContainer, gameContainer, submitBtn);
        }
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");

    }

}
