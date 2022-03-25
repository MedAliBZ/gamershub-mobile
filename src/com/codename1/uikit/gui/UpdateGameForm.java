/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Game;
import com.codename1.uikit.services.ServiceGames;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;

/**
 *
 * @author MAB
 */
public class UpdateGameForm extends Form {

    private Game game;
    private String fileSavePath;

    public UpdateGameForm(Game game, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        if (game.getName() != null) {
            this.getToolbar().setTitle("Update a game");
            game = ServiceGames.getInstance().getGame(MyApplication.loggedUser.getUsername(), game.getName());
            this.game = game;
            Container descriptionContainer = new Container(BoxLayout.y());
            Container titleContainer = new Container(BoxLayout.y());

            TextArea descriptionTA = new TextArea(game.getDescription(), 9999);
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);

            TextArea titleTA = new TextArea(game.getName(), 200);
            titleTA.getAllStyles().setAlignment(TextArea.CENTER);

            Button submitBtn = new Button("Submit");

            Button selectImage = new Button("Update Image");
            TextField imageField = new TextField("", "", 10, TextArea.ANY);
            imageField.setEditable(false);
            selectImage.addActionListener((evt) -> {
                Display.getInstance().openGallery((e) -> {
                    if (e != null && e.getSource() != null) {
                        String filePath = (String) e.getSource();
                        imageField.setText(filePath.substring(filePath.lastIndexOf('/') + 1));
                        this.fileSavePath = (String) e.getSource();
                    }
                }, Display.GALLERY_IMAGE
                );

            });

            descriptionContainer.addAll(new Label("Description"), descriptionTA);
            titleContainer.addAll(new Label("Title"), titleTA);
            this.addAll(imageField, selectImage, titleContainer, descriptionContainer, submitBtn);

        } else {
            this.getToolbar().setTitle("Add a game");

            Container descriptionContainer = new Container(BoxLayout.y());
            Container titleContainer = new Container(BoxLayout.y());

            TextArea descriptionTA = new TextArea("", 10,100);
            descriptionTA.setMaxSize(9999);
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);

            TextArea titleTA = new TextArea("", 200);
            titleTA.getAllStyles().setAlignment(TextArea.CENTER);

            Button submitBtn = new Button("Submit");

            Button selectImage = new Button("Add Image");
            TextField imageField = new TextField("", "", 10, TextArea.ANY);
            imageField.setEditable(false);
            selectImage.addActionListener((evt) -> {
                Display.getInstance().openGallery((e) -> {
                    if (e != null && e.getSource() != null) {
                        String filePath = (String) e.getSource();
                        imageField.setText(filePath.substring(filePath.lastIndexOf('/') + 1));
                        this.fileSavePath = (String) e.getSource();
                    }
                }, Display.GALLERY_IMAGE
                );
            });

            descriptionContainer.addAll(new Label("Description"), descriptionTA);
            titleContainer.addAll(new Label("Title"), titleTA);
            
            submitBtn.addActionListener(e->{
                ServiceGames.getInstance().addGame(titleTA.getText(), fileSavePath, descriptionTA.getText());
                new ListGamesForm().show();
            });
            this.addAll(imageField, selectImage, titleContainer, descriptionContainer, submitBtn);
        }
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");

    }
}
