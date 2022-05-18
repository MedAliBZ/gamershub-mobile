/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.entities.Tournaments;
import com.codename1.uikit.services.ServiceTournaments;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.codename1.uikit.utils.Statics;


/**
 *
 * @author amira
 */
public class ListTournamentsForm extends BaseForm{
    
    public ListTournamentsForm() {
        super.addSideMenu();
        setTitle("Tournaments List");
        setUIID("Activate");

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_SHOPPING_CART, e -> {

            Form cartsForm = new ListCartsForm(this);
            cartsForm.show();
        });

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {

            Form addForm = new AddTournamentsForm(this);
            addForm.show();
        });

        List<Tournaments> tournaments = new ArrayList();
        tournaments = ServiceTournaments.getInstance().getAllTournaments();

        for (int i = 0; i < tournaments.size(); i++) {
            //System.out.println(tournaments.get(i));
            this.add(listOfTournaments(tournaments.get(i)));
        }

    }

    public Container listOfTournaments(Tournaments c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnCat = new Container(BoxLayout.y());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            Label lbName = new Label();
            Label lbTeamSize = new Label();
            Label lbDescription = new Label();

            lbName.setText(c.getName().toLowerCase());
            lbTeamSize.setText(c.getTeamSize() + "");
            lbDescription.setText(c.getName().toLowerCase());

            ctninfo.addAll(lbName, lbTeamSize, lbDescription);
            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;
            try {
                enc = EncodedImage.create("/spinner.png");
                //String pic =c.getImage().substring(0,c.getImage().length()-1);
                String url = Statics​.BASE_URL + "/shop/images/" + c.getImage();

                //System.out.println(url);
                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

                imgv = new ImageViewer(img);

                imgv.addPointerReleasedListener((evnt) -> {
                    Form details = new TournamentsForm(c, this);
                    details.show();
                });

                ctn.addAll(imgv, ctninfo);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        return ctn;
    }
    
}
