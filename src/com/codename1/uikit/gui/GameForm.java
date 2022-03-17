/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.entities.Game;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import com.codename1.ui.URLImage;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.services.ServiceGames;

/**
 *
 * @author MAB
 */
public class GameForm extends Form {

    private Game game;

    public GameForm(Game game, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle(game.getName());
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");
        game = ServiceGames.getInstance().getGame(MyApplication.loggedUser.getUsername(), game.getName());
        this.game = game;
        try {
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container imageContainer = new Container(BoxLayout.xCenter());
            Container descriptionContainer = new Container(BoxLayout.xCenter());
            String url = Statics.BASE_URL + "/games/images/" + game.getImage();
            Image gameImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);

            gameImage = gameImage.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
            ImageViewer image = new ImageViewer(gameImage);
            imageContainer.setHeight(Display.getInstance().getDisplayHeight() / 3);
            TextArea descriptionTA = new TextArea(game.getDescription());
            descriptionTA.setEditable(false);
            descriptionTA.setFocusable(false);
            descriptionTA.setUIID("Label");
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);
            descriptionContainer.add(descriptionTA);
            imageContainer.add(image);

            Container likeCtn = new Container(BoxLayout.xCenter());
            CheckBox heart = new CheckBox();
            if (game.isIsLiked()) {
                heart = CheckBox.createToggle(MyApplication.theme.getImage("heart-on.png").scaled(200, 200));
                heart.setPressedIcon(MyApplication.theme.getImage("heart-off.png").scaled(200, 200));

            } else {
                heart = CheckBox.createToggle(MyApplication.theme.getImage("heart-off.png").scaled(200, 200));
                heart.setPressedIcon(MyApplication.theme.getImage("heart-on.png").scaled(200, 200));
            }
            heart.setUIID("Label");
            likeCtn.add(heart);
            heart.addActionListener(l -> {
                if (this.game.isIsLiked()) {
                    ServiceGames.getInstance().unlikeGame(MyApplication.loggedUser.getUsername(), this.game.getName());
                    this.game.setIsLiked(false);
                }
                else{
                    ServiceGames.getInstance().likeGame(MyApplication.loggedUser.getUsername(), this.game.getName());
                    this.game.setIsLiked(true);
                }

            });
            this.addAll(imageContainer, descriptionContainer, likeCtn);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
