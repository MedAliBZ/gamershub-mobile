/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Tournaments;
import com.codename1.uikit.services.ServiceTournaments;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;

/**
 *
 * @author amira
 */
public class TournamentsForm extends Form{
    
    private Tournaments tournaments;
 

    public TournamentsForm(Tournaments tournaments, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle(tournaments.getName());
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");
        //System.out.println("this is the detail category"+category);

        this.tournaments = tournaments;
       
        if (MyApplication.loggedUser.isIsAdmin()) {

            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_EDIT, e -> {
                Form editForm = new TournamentsEditForm(this.tournaments, this);
                editForm.show();
            });
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_DELETE, e -> {
                ServiceTournaments.getInstance().deleteTournaments(this.tournaments.getId());
                new ListTournamentsForm().show();
            });
        }
        try {
            //System.out.println(tournaments);
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container imageContainer = new Container(BoxLayout.xCenter());
            Container descriptionContainer = new Container(BoxLayout.xCenter());
            String url = Statics.BASE_URL + "/img/tournaments/" + tournaments.getImage();
            Image tournamentsImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);

            tournamentsImage = tournamentsImage.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
            ImageViewer image = new ImageViewer(tournamentsImage);
            imageContainer.setHeight(Display.getInstance().getDisplayHeight() / 3);

            TextArea tfName = new TextArea(tournaments.getName());
            TextArea descriptionTA = new TextArea(tournaments.getDescription());
            TextArea tfTeamSize = new TextArea(tournaments.getTeamSize() + "");

            
            //Categories c = ServiceCategories.getInstance().getCategoryById(tournaments.getCategoryId());
            tfName.setEditable(false);
            tfName.setFocusable(false);
            tfName.setUIID("Label");
            tfName.getAllStyles().setAlignment(TextArea.CENTER);
            
            descriptionTA.setEditable(false);
            descriptionTA.setFocusable(false);
            descriptionTA.setUIID("Label");
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);

            tfTeamSize.setEditable(false);
            tfTeamSize.setFocusable(false);
            tfTeamSize.setUIID("Label");
            tfTeamSize.getAllStyles().setAlignment(TextArea.CENTER);

            /*tfCatID.setEditable(false);
            tfCatID.setFocusable(false);
            tfCatID.setUIID("Label");
            tfCatID.getAllStyles().setAlignment(TextArea.CENTER);

            tfPrice.setEditable(false);
            tfPrice.setFocusable(false);
            tfPrice.setUIID("Label");
            tfPrice.getAllStyles().setAlignment(TextArea.CENTER);

            tfQuantityStocked.setEditable(false);
            tfQuantityStocked.setFocusable(false);
            tfQuantityStocked.setUIID("Label");
            tfQuantityStocked.getAllStyles().setAlignment(TextArea.CENTER);*/

            descriptionContainer.add(descriptionTA);
            imageContainer.add(image);
            /* btnCart.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                     Cart cart = new Cart();
                         cart.setTournaments(tournaments);
                    if (ServiceCart.getInstance().addCart(cart)) {
                        
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        
        );*/
            this.addAll(imageContainer, descriptionContainer, tfName, tfTeamSize);
        } catch (IOException e) {
            System.err.println(e);
        }

    }
    
}
