/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Stroke;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.services.ServiceUser;

/**
 *
 * @author MAB
 */
public class ProfileForm extends BaseForm {
    public ProfileForm(){
        super.addSideMenu();
        setTitle("Profile");
        setUIID("Activate");
        Image img = MyApplication.theme.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        img = img.scaledWidth(Display.getInstance().getDisplayWidth());
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        String url = "https://avatars.dicebear.com/api/bottts/" + MyApplication.loggedUser.getUsername() + ".png";
        try {
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Image userImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);
            add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(1, 
                            FlowLayout.encloseCenter(new Label(userImage, "PictureWhiteBackgrond"))
                    )
                )
        ));
        } catch (Exception e) {

        }
        TextField username = new TextField(MyApplication.loggedUser.getUsername());
        username.setEditable(false);
        addStringValue("Username", username);
        
        TextField name = new TextField(MyApplication.loggedUser.getName(), "Name", 20, TextField.EMAILADDR);
        addStringValue("Name", name);
        
        TextField secondName = new TextField(MyApplication.loggedUser.getSecondName(), "Second Name", 20, TextField.EMAILADDR);
        addStringValue("Second Name", secondName);

        TextField email = new TextField(MyApplication.loggedUser.getEmail(), "E-Mail", 20, TextField.EMAILADDR);
        addStringValue("E-Mail", email);
        
        Label updated = new Label();
        updated.getAllStyles().setFgColor(0x008000);
        
        Button submitBtn = new Button("Update");
        Button updatePassBtn = new Button("Update Password");
        Button deleteBtn = new Button("Delete");
        deleteBtn.getAllStyles().setBorder(RoundBorder.create().
        rectangle(true).
        color(0xFF0000).
        strokeColor(0).
        strokeOpacity(120).
        stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1)));
        deleteBtn.getAllStyles().setFgColor(0xffffff);
        submitBtn.getAllStyles().setBorder(RoundBorder.create().
        rectangle(true).
        color(0x228B22).
        strokeColor(0).
        strokeOpacity(120).
        stroke(new Stroke(2, Stroke.CAP_SQUARE, Stroke.JOIN_MITER, 1)));
        submitBtn.getAllStyles().setFgColor(0xffffff);
        Container passCn = new Container(BoxLayout.xCenter());
        passCn.addAll(submitBtn, updatePassBtn, deleteBtn);
        add(updated);
        add(passCn);
        
        deleteBtn.addActionListener(l->{
            ServiceUser.getInstance().deleteUser(MyApplication.loggedUser.getUsername());
        });
        
        submitBtn.addActionListener(l->{
            ServiceUser.getInstance().updateUser(MyApplication.loggedUser.getUsername(), email.getText(), name.getText(), secondName.getText());
            updated.setText("Updated Succesfully!");
            
        });
        
        updatePassBtn.addActionListener(l->{
            Form updat = new UpdatePasswordForm(this);
            updat.show();
        });
    }
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator());
    }
        
}
