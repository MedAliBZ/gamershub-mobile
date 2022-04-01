/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.entities.Game;
import com.codename1.uikit.services.ServiceGames;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.uikit.MyApplication;

/**
 *
 * @author MAB
 */
public class ListGamesForm extends BaseForm {

    public ListGamesForm() {
        super.addSideMenu();
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_FAVORITE_OUTLINE, e -> {
            new ListGamesForm(ServiceGames.getInstance().getAllLikedGames()).show();
        });
        if (MyApplication.loggedUser.isIsAdmin()) {
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
                new UpdateGameForm(new Game(), this).show();
            });
        }
        setTitle("Games List");
        setUIID("Activate");
        this.setScrollable(false);
        this.setLayout(new BorderLayout());
        TextField searchBar = new TextField("", "Search");
        Button searchBtn = new Button("", FontImage.MATERIAL_SEARCH, "Link");
        Container searchCtn = new Container(BoxLayout.x());
        searchCtn.addAll(searchBar, searchBtn);
        this.add(BorderLayout.NORTH, searchCtn);
        ArrayList<Game> gamesList = ServiceGames.getInstance().getAllGames();
        Container gamesCtn = new Container();
        gamesCtn.setScrollableY(true);
        searchBar.addActionListener(e -> {
            gamesCtn.removeAll();
            for (Game g : gamesList) {
                if (g.getName().toLowerCase().contains(searchBar.getText().toLowerCase()) || searchBar.getText().equals("")) {
                    gamesCtn.add(this.addGamesHolder(g));
                    gamesCtn.add(createLineSeparator());
                }
            }
        });
        for (Game g : gamesList) {
            gamesCtn.add(this.addGamesHolder(g));
            gamesCtn.add(createLineSeparator());
        }
        this.add(BorderLayout.CENTER, gamesCtn);
        //getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }

    public ListGamesForm(ArrayList<Game> likedGames) {
        super.addSideMenu();
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_FAVORITE, e -> {
            new ListGamesForm().show();
        });
        setTitle("Liked Games List");
        setUIID("Activate");
        for (Game g : likedGames) {
            this.add(this.addGamesHolder(g));
        }
    }

    private Container addGamesHolder(Game g) {
        try {
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container holderContainer = new Container(BoxLayout.x());
            Container detailsContainer = new Container(BoxLayout.y());
            Container titleContainer = new Container(BoxLayout.x());
            String url = Statics.BASE_URL + "/games/images/" + g.getImage();
            Image gameImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);
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
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e);
        }
        return new Container(BoxLayout.x());
    }
}
