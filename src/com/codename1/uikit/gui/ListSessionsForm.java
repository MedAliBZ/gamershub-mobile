/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Coach;
import com.codename1.uikit.entities.Sessioncoaching;
import com.codename1.uikit.services.ServiceCoachs;
import com.codename1.uikit.services.ServiceSessions;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class ListSessionsForm extends BaseForm {
    public ListSessionsForm(){
     super.addSideMenu();
     boolean isCoach = false;
     ArrayList<Coach> coachList = ServiceCoachs.getInstance().getAllCoachs();
        for (Coach g : coachList) {
            if(g.getUsername().equals(MyApplication.loggedUser.getUsername()))
                isCoach = true;
        }
        if(isCoach == true)
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
                new AddSessionForm(new Sessioncoaching(), this).show();
            });
        setTitle("Session List");
        setUIID("Activate");
        
        ArrayList<Sessioncoaching> sessionList = ServiceSessions.getInstance().getAllSessions();
        for (Sessioncoaching g : sessionList) {
            this.add(this.addSessionsHolder(g));
        }}
        private Container addSessionsHolder(Sessioncoaching g) {
        try {
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container holderContainer = new Container(BoxLayout.x());
            Container detailsContainer = new Container(BoxLayout.y());
            Container titleContainer = new Container(BoxLayout.x());
            String url = "https://avatars.dicebear.com/api/bottts/" + g.getCoach()+ ".png";
       
            Image gameImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);
            ImageViewer image = new ImageViewer(gameImage);
            Label lbTitle = new Label(g.getCoach());
            Label lUser = new Label(g.getUsername());
            Label lDescription = new Label(g.getDescription());
            Label lPrix = new Label(g.getPrix());
            Label lDate_debut = new Label(g.getDate_debut().toString());
            Label lDate_fin = new Label(g.getDate_fin().toString());
            Container buttonCtn = new Container(BoxLayout.x());
            Button update = new Button("Update");
            Button delete = new Button("Delete");
            delete.getAllStyles().setBorder(RoundBorder.create().
                    rectangle(true).
                    color(0xFF0000).
                    strokeColor(0).
                    strokeOpacity(120).
                    stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1)));
            delete.getAllStyles().setFgColor(0xffffff);
            buttonCtn.addAll(update, delete);
            titleContainer.add(lbTitle);
            detailsContainer.addAll(titleContainer, lUser, lDescription, lPrix, lDate_debut,lDate_fin);
            if (MyApplication.loggedUser.getUsername().equals(g.getCoach())) {
                detailsContainer.add(buttonCtn);
            }

            delete.addActionListener(e -> {
                ServiceSessions.getInstance().deleteSession(g.getId());
                new ListSessionsForm().show();
            });
            
            update.addActionListener(e -> {
                new AddSessionForm(g, this).show();
            });

            holderContainer.addAll(image, detailsContainer);
            return holderContainer;
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e);
        }
        return new Container(BoxLayout.x());
    }

     
    }
    
    
    

