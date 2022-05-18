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
import com.codename1.uikit.entities.Rewards;
import com.codename1.uikit.services.ServiceRewards;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amira
 */
public class ListRewardsForm extends BaseForm{
    
    public ListRewardsForm() {
        super.addSideMenu();
        setTitle("Rewards List");
        setUIID("Activate");


        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {

            Form addForm = new AddRewardsForm(this);
            addForm.show();
        });

        List<Rewards> rewards = new ArrayList();
        rewards = ServiceRewards.getInstance().getAllRewards();

        for (int i = 0; i < rewards.size(); i++) {
            //System.out.println(rewards.get(i));
            this.add(listOfRewards(rewards.get(i)));
        }

    }

    public Container listOfRewards(Rewards c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnCat = new Container(BoxLayout.y());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            Label lbType = new Label();
            Label lbQuantity = new Label();

            lbType.setText("Reward type: " + c.getType().toUpperCase());
            lbQuantity.setText("Reward quantity: " + c.getQuantity() + "");

            ctninfo.addAll(lbType, lbQuantity);
            //img loading
            lbType.addPointerReleasedListener((evnt) -> {
                Form details = new RewardsForm(c, this);
                details.show();
            });
            ctn.addAll(ctninfo);

        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        return ctn;
    }
}
