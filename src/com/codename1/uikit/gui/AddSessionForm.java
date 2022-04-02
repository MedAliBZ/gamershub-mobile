/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.gui;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Sessioncoaching;
import com.codename1.uikit.entities.User;
import com.codename1.uikit.services.ServiceSessions;
import com.codename1.uikit.services.ServiceUser;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author hp
 */
public class AddSessionForm extends Form{
    private Sessioncoaching session;

    public AddSessionForm(Sessioncoaching session, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        if (session.getId() != 0) {
            this.getToolbar().setTitle("Update session");
         
            this.session = session;
            Container descriptionContainer = new Container(BoxLayout.y());
            Container titleContainer = new Container(BoxLayout.x());
            Container prixContainer = new Container(BoxLayout.y());
            Container date_debutContainer = new Container(BoxLayout.y());
            Container date_finContainer = new Container(BoxLayout.y());
            Container userContainer = new Container(BoxLayout.y());
            
            TextArea prixTA = new TextArea("", 10);
            prixTA.setMaxSize(4);
            prixTA.getAllStyles().setAlignment(TextArea.CENTER);

            TextArea descriptionTA = new TextArea(session.getDescription(), 10,100);
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);

            TextArea titleTA = new TextArea(MyApplication.loggedUser.getUsername());
            titleTA.getAllStyles().setAlignment(TextArea.CENTER);
            titleTA.setEditable(false);
            titleTA.setUIID("Label");
            
            
            
            Picker date_debutTA = new Picker();
            Picker date_finTA = new Picker();
            
            
            ComboBox<String> usernameCB = new ComboBox<>();
            ArrayList<User> UsersList = ServiceUser.getInstance().getAllUsers();
            
            for (User g : UsersList) {
                usernameCB.addItem(g.getUsername());
            }
            
            usernameCB.setSelectedItem(session.getUsername());
            
            userContainer.addAll(new Label("User"),usernameCB);

            Button submitBtn = new Button("Submit");
            
            SimpleDateFormat dateD = new SimpleDateFormat();
            SimpleDateFormat dateF = new SimpleDateFormat();

            descriptionContainer.addAll(new Label("Description"), descriptionTA);
            titleContainer.addAll(new Label("Coach : "), titleTA);
            date_debutContainer.addAll(new Label("Start Date: "), date_debutTA);
            date_finContainer.addAll(new Label("End Date: "), date_finTA);
            prixContainer.addAll(new Label("Price: "), prixTA);
            
            this.addAll(titleContainer, descriptionContainer, userContainer,date_debutContainer,date_finContainer,prixContainer, submitBtn);
            submitBtn.addActionListener(e->{
                 ServiceSessions.getInstance().updateSession(descriptionTA.getText(), usernameCB.getSelectedItem(), dateD.format(date_debutTA.getDate()), dateF.format(date_finTA.getDate()), prixTA.getText(),this.session.getId());
                new ListSessionsForm().show();
            });

        } else {
            this.getToolbar().setTitle("Add a session");

            Container descriptionContainer = new Container(BoxLayout.y());
            Container titleContainer = new Container(BoxLayout.x());
            Container prixContainer = new Container(BoxLayout.y());
            Container date_debutContainer = new Container(BoxLayout.y());
            Container date_finContainer = new Container(BoxLayout.y());
            Container userContainer = new Container(BoxLayout.y());
            
            TextArea descriptionTA = new TextArea("", 10,100);
            descriptionTA.setMaxSize(9999);
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);
            
            TextArea prixTA = new TextArea("", 10);
            prixTA.setMaxSize(4);
            prixTA.getAllStyles().setAlignment(TextArea.CENTER);
            
            TextArea titleTA = new TextArea(MyApplication.loggedUser.getUsername());
            titleTA.getAllStyles().setAlignment(TextArea.CENTER);
            titleTA.setEditable(false);
            titleTA.setUIID("Label");
            
            Picker date_debutTA = new Picker();
            Picker date_finTA = new Picker();
            //PickerComponent date_debutTA = PickerComponent.createDate(new Date());
            //PickerComponent date_finTA = PickerComponent.createDate(new Date());

            Button submitBtn = new Button("Submit");

            descriptionContainer.addAll(new Label("Description"), descriptionTA);
            titleContainer.addAll(new Label("Name: "), titleTA);
            date_debutContainer.addAll(new Label("Start Date: "), date_debutTA);
            date_finContainer.addAll(new Label("End Date: "), date_finTA);
            prixContainer.addAll(new Label("Price: "), prixTA);
            
            ComboBox<String> usernameCB = new ComboBox<>();
            ArrayList<Sessioncoaching> sessionsList = ServiceSessions.getInstance().getAllSessions();
            for (Sessioncoaching g : sessionsList) {
                usernameCB.addItem(g.getUsername());
            }
            SimpleDateFormat dateD = new SimpleDateFormat();
            SimpleDateFormat dateF = new SimpleDateFormat();
            
            submitBtn.addActionListener(e->{
                ServiceSessions.getInstance().addSession(descriptionTA.getText(), usernameCB.getSelectedItem(), dateD.format(date_debutTA.getDate()), dateF.format(date_finTA.getDate()), prixTA.getText());
                new ListSessionsForm().show();
            });
            userContainer.addAll(new Label("User"),usernameCB);
            this.addAll(titleContainer, descriptionContainer, userContainer, date_debutContainer,date_finContainer,prixContainer, submitBtn );
        }
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");

    }

    
}
