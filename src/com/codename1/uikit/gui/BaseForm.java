/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.codename1.uikit.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.User;

/**
 * Base class for the forms with common functionality
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {

    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu() {
        Toolbar tb = getToolbar();
        tb.setSafeArea(false);
        String url = "https://avatars.dicebear.com/api/bottts/" + MyApplication.loggedUser.getUsername() + ".png";
        try {
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Image gameImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);
            tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                    FlowLayout.encloseCenterBottom(
                            new Label(gameImage, "PictureWhiteBackgrond"))
            ));
        } catch (Exception e) {

        }

        tb.addMaterialCommandToSideMenu("Games", FontImage.MATERIAL_SPORTS_ESPORTS, e -> new ListGamesForm().show());
        tb.addMaterialCommandToSideMenu("Categories", FontImage.MATERIAL_CATEGORY, e -> new ListCategoriesForm().show());
        tb.addMaterialCommandToSideMenu("Products", FontImage.MATERIAL_SHOPPING_BAG, e -> new ListProductsForm().show());
        tb.addMaterialCommandToSideMenu("Coachs", FontImage.MATERIAL_SPORTS, e -> new ListCoachsForm().show());
        tb.addMaterialCommandToSideMenu("Coaching Sessions", FontImage.MATERIAL_MEETING_ROOM, e -> new ListSessionsForm().show());
        tb.addMaterialCommandToSideMenu("Teams", FontImage.MATERIAL_PEOPLE, e -> new ListTeamForm().show());
        tb.addMaterialCommandToSideMenu("Matchs", FontImage.MATERIAL_VIDEOGAME_ASSET, e -> new ListMatchForm().show());
        tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm().show());
        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> {
            MyApplication.loggedUser = new User();
            Form p1 = new LoginForm();
            p1.show();
        });
    }
}
