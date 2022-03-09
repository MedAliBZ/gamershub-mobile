/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.WebBrowser;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Game;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import com.codename1.ui.URLImage;

/**
 *
 * @author MAB
 */
public class GameForm extends Form {

    public GameForm(Game game, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle(game.getName());
        this.setLayout(BoxLayout.y());
        try {
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container imageContainer = new Container(BoxLayout.xCenter());
            Container descriptionContainer = new Container(BoxLayout.xCenter());
            String url = Statics.BASE_URL + "/games/images/" + game.getImage();
            Image gameImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);
            ImageViewer image = new ImageViewer(gameImage);
            imageContainer.setHeight(500);
            TextArea descriptionTA = new TextArea(game.getDescription());
            descriptionTA.setEditable(false);
            descriptionTA.setFocusable(false);
            descriptionTA.setUIID("Label");
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);
            descriptionContainer.add(descriptionTA);
            imageContainer.add(image);
            this.addAll(imageContainer, descriptionContainer);
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
