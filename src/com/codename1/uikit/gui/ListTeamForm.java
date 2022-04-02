package com.codename1.uikit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Teams;
import com.codename1.uikit.services.ServiceMatch;
import com.codename1.uikit.services.ServiceTeam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.codename1.uikit.utils.Statics;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author fadhel
 */
public class ListTeamForm extends BaseForm {

    public ListTeamForm() {
        super.addSideMenu();
        setTitle("Team List");
        setUIID("Activate");
        setLayout(BoxLayout.y());
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
           
                   Form addForm = new TeamForm(this);
                   addForm.show();
        });

        List<Teams> teams = ServiceTeam.getInstance().getAllTeams();
        System.out.println(teams);
//System.out.println(Teams.sizse());
        for (Teams t : teams) {
            this.add(listOfTeams(t));
            Button deleteBtn = new Button("delete");
            deleteBtn.addActionListener(e->{ 
            ServiceTeam.getInstance().deleteTeam(t.getId());
            ListTeamForm li = new ListTeamForm();
            li.show();
            });
        this.add(deleteBtn);
        }

    }

  

    public Container listOfTeams(Teams c) {
        try {
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container holderContainer = new Container(BoxLayout.x());
            Container detailsContainer = new Container(BoxLayout.y());
            Container titleContainer = new Container(BoxLayout.x());
            String url = Statics.BASE_URL + "/games/images/" + c.getImage();
            Image gameImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);
            ImageViewer image = new ImageViewer(gameImage);
            Label lbTitle = new Label(c.getTeamName());
            Label lDescription = new Label(Integer.toString(c.getRank()));
            titleContainer.add(lbTitle);
            detailsContainer.addAll(titleContainer, lDescription);
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
