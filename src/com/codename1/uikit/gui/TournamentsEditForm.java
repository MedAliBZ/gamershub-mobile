/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.entities.Tournaments;
import com.codename1.uikit.services.ServiceTournaments;

/**
 *
 * @author amira
 */
public class TournamentsEditForm extends Form{
    
    public TournamentsEditForm(Tournaments tournaments, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Edit Tournaments");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField tfName = new TextField("", "Tournaments Name");
        TextField tfDes = new TextField("", "Tournaments Description");
        TextField tfTeamSize = new TextField("", "Quantity in stock");


      

        tfName.setText(tournaments.getName());
        tfTeamSize.setText(tournaments.getTeamSize() + "");
        tfDes.setText(tournaments.getDescription());



        Button btnSubmit = new Button("Edit Tournaments");

        btnSubmit.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (tfDes.getText().length() == 0)
                        || (tfTeamSize.getText().length() == 0)) {
                    Dialog.show("Alert", "please fill all the Fields", new Command("ok"));
                } else {
                   
                    tournaments.setDescription(tfDes.getText());
                    tournaments.setName(tfName.getText());
                    tournaments.setTeamSize(Integer.parseInt(tfTeamSize.getText()));

                    if (ServiceTournaments.getInstance().EditTournaments(tournaments)) {
                        new ListTournamentsForm().show();
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        }
        );
        addAll(tfName, tfDes, tfTeamSize, btnSubmit);

    }
    
}
