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
import com.codename1.uikit.entities.Rewards;
import com.codename1.uikit.services.ServiceRewards;

/**
 *
 * @author amira
 */
public class RewardsEditForm extends BaseForm{
    
    public RewardsEditForm(Rewards reward, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Edit Rewards");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField tfType = new TextField("", "Rewards Type");
        TextField tfQuantity = new TextField("", "Quantity in stock");


      

        tfType.setText(reward.getType());
        tfQuantity.setText(reward.getQuantity() + "");



        Button btnSubmit = new Button("Edit Rewards");

        btnSubmit.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfType.getText().length() == 0)) {
                    Dialog.show("Alert", "please fill all the Fields", new Command("ok"));
                } else {
                   
                    reward.setType(tfType.getText());
                    reward.setQuantity(Integer.parseInt(tfQuantity.getText()));

                    if (ServiceRewards.getInstance().EditRewards(reward)) {
                        new ListRewardsForm().show();
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        }
        );
        addAll(tfType, tfQuantity, btnSubmit);

    }
}
