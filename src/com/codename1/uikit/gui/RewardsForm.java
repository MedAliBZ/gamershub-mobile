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
import com.codename1.uikit.entities.Rewards;
import com.codename1.uikit.services.ServiceRewards;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;

/**
 *
 * @author amira
 */
public class RewardsForm extends Form{
    
    private Rewards rewards;
 

    public RewardsForm(Rewards rewards, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle("Rewards");
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");
        //System.out.println("this is the detail category"+category);

        this.rewards = rewards;
       
        if (MyApplication.loggedUser.isIsAdmin()) {

            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_EDIT, e -> {
                Form editForm = new RewardsEditForm(this.rewards, this);
                editForm.show();
            });
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_DELETE, e -> {
                ServiceRewards.getInstance().deleteRewards(this.rewards.getId());
                new ListRewardsForm().show();
            });
        }
        //System.out.println(blog);
        
        Container quantityContainer = new Container(BoxLayout.xCenter());
        TextArea quantityTA = new TextArea("Reward quantity: " + rewards.getQuantity() + "");
        TextArea tfType = new TextArea("Reward type: " + rewards.getType());
        //Categories c = ServiceCategories.getInstance().getCategoryById(rewards.getCategoryId());
        quantityTA.setEditable(false);
        quantityTA.setFocusable(false);
        quantityTA.setUIID("Label");
        quantityTA.getAllStyles().setAlignment(TextArea.CENTER);
        tfType.setEditable(false);
        tfType.setFocusable(false);
        tfType.setUIID("Label");
        tfType.getAllStyles().setAlignment(TextArea.CENTER);
        /*tfCatID.setEditable(false);
        tfCatID.setFocusable(false);
        tfCatID.setUIID("Label");
        tfCatID.getAllStyles().setAlignment(TextArea.CENTER);
        
        tfPrice.setEditable(false);
        tfPrice.setFocusable(false);
        tfPrice.setUIID("Label");
        tfPrice.getAllStyles().setAlignment(TextArea.CENTER);*/
        quantityContainer.add(quantityTA);
        this.addAll(quantityContainer, tfType);

    }
}
