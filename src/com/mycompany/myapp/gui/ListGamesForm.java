/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.entities.Game;
import com.mycompany.myapp.services.ServiceGames;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import com.codename1.ui.URLImage;

/**
 *
 * @author MAB
 */
public class ListGamesForm extends Form {

    public ListGamesForm() {
        new HomeTemplate(this, "Games List");
        ArrayList<Game> gamesList = ServiceGames.getInstance().getAllGames();
        for(Game g : gamesList){
            this.add(this.addGamesHolder(g));
        }
        //getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    private Container addGamesHolder(Game g) {
        try {
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container holderContainer = new Container(BoxLayout.x());
            Container detailsContainer = new Container(BoxLayout.y());
            Container titleContainer = new Container(BoxLayout.x());
            String url = Statics.BASE_URL+"/games/images/"+g.getImage();
            Image gameImage = URLImage.createToStorage(spinner, url, url,URLImage.RESIZE_SCALE);
            ImageViewer image = new ImageViewer(gameImage);
            Label lbTitle = new Label(g.getName());
            Label lDescription = new Label(g.getDescription());
            image.addPointerReleasedListener((evnt) -> {
                Form details = new GameForm(g, this);
                details.show();
            });
            titleContainer.add(lbTitle);
            detailsContainer.addAll(titleContainer, lDescription);
            holderContainer.addAll(image, detailsContainer);
            holderContainer.setLeadComponent(image);
            return holderContainer;
        } 
        catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
        catch (IOException e){
            System.err.println(e);
        }
        return new Container(BoxLayout.x());
    }
}
