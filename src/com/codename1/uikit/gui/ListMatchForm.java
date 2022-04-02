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
import com.codename1.uikit.entities.Matchs;
import com.codename1.uikit.services.ServiceMatch;
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
public class ListMatchForm extends BaseForm {

    public ListMatchForm() {
        super.addSideMenu();
        setTitle("Match List");
        setUIID("Activate");
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
           
                   Form addForm = new MatchForm(this);
                   addForm.show();
        });

        List<Matchs> Matchs = ServiceMatch.getInstance().getAllMatchs();
        

        for (Matchs c : Matchs) {
            this.add(listOfMatchs(c));
            Button deleteBtn = new Button("delete");
            deleteBtn.addActionListener(e->{ 
            ServiceMatch.getInstance().deleteMatch(c.getId());
            ListMatchForm li = new ListMatchForm();
            li.show();
           
            });
           Button updateBtn = new Button("update");
            updateBtn.addActionListener(e->{ 
            ServiceMatch.getInstance().updateMatch(c);
            MatchEditForm li = new MatchEditForm(c,this);
            li.show();
           
            });
         this.add(deleteBtn);
         this.add(updateBtn);
        }

    }

    public Container listOfMatchs(Matchs c) {
         try {
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container holderContainer = new Container(BoxLayout.x());
            Container detailsContainer = new Container(BoxLayout.y());
            Container titleContainer = new Container(BoxLayout.x());
            //String url = Statics.BASE_URL + "/games/images/" + c.getImage();
            //Image gameImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);
            //ImageViewer image = new ImageViewer(gameImage);
            Label lbTitle = new Label(c.getMatchName());
            Label lDescription = new Label(c.getResult());
            Label lResult= new Label(c.getDate());
            titleContainer.add(lbTitle);
            detailsContainer.addAll(titleContainer, lDescription,lResult);
            holderContainer.addAll( detailsContainer);
            return holderContainer;
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e);
        }
        return new Container(BoxLayout.x());
    }
}
