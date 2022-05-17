/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Player;
import com.codename1.uikit.services.ServicePlayer;


/**
 *
 * @author amira
 */
public class BecomePlayerForm extends BaseForm{
         String ch;

    public BecomePlayerForm() {
        super.addSideMenu();
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> new ProfileForm().show());
        setTitle("Become Player");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField tfRank = new TextField("", "Player Title");
        //TextField tfCatID = new TextField("", "Category ID");

        

        Button btnSubmit = new Button("Submit");

        btnSubmit.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfRank.getText().length() == 0)) {
                    Dialog.show("Alert", "please fill all the Fields", new Command("ok"));
                } else {

                    Player c = new Player();
                    /*String catId = categoryBox.getSelectedItem().toString();
                    StringTokenizer st = new StringTokenizer(catId,",");

                    c.setCategoryId(Integer.parseInt(st.nextToken()));*/
                    //c.setImage(ch);
                    c.setRank(tfRank.getText());
                   /* c.setPrice(Double.parseDouble(tfPrice.getText()));
                    c.setQuantityStocked(Integer.parseInt(tfQuantityStocked.getText()));*/
                   c.setUser(MyApplication.loggedUser.getId());
                   
                    if (ServicePlayer.getInstance().addPlayer(c)) {
                        new ProfileForm().show();
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        }
        );
        addAll(tfRank,btnSubmit);

    }
}
