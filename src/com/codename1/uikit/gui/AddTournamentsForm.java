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
import com.codename1.uikit.entities.Tournaments;
import com.codename1.uikit.services.ServiceTournaments;

/**
 *
 * @author amira
 */
public class AddTournamentsForm extends Form{
    
    String ch;

    public AddTournamentsForm(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setName("Add Tournaments");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField tfName = new TextField("", "Tournaments Name");
        TextField tfDes = new TextField("", "Tournaments Description");
        TextField tfTeamSize = new TextField("", "Tournaments Team Size");

        //TextField tfCatID = new TextField("", "Category ID");
//combo 

       
        
        Button btnSubmit = new Button("Submit");

        btnSubmit.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (tfDes.getText().length() == 0) ) {
                    Dialog.show("Alert", "please fill all the Fields", new Command("ok"));
                } else {

                    Tournaments c = new Tournaments();
                    /*String catId = categoryBox.getSelectedItem().toString();
                    StringTokenizer st = new StringTokenizer(catId,",");

                    c.setCategoryId(Integer.parseInt(st.nextToken()));*/
                    c.setDescription(tfDes.getText());
                    //c.setImage(ch);
                    c.setName(tfName.getText());
                   /* c.setPrice(Double.parseDouble(tfPrice.getText()));
                    c.setQuantityStocked(Integer.parseInt(tfQuantityStocked.getText()));*/
                    c.setTeamSize(Integer.parseInt(tfTeamSize.getText()));

                   
                    if (ServiceTournaments.getInstance().addTournaments(c)) {
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
