package com.codename1.uikit.gui;

import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import com.codename1.uikit.entities.Teams;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.services.ServiceTeam;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author fadhel
 */
public class TeamForm extends Form {

    String ch;
    String tfGamersnb;

    public TeamForm(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Add Team");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField tfName = new TextField("", "Team Name");
        TextField tfGamersnb = new TextField("", "Team Numbers");
         TextField tfRank = new TextField("", "Team Rank");
        
        TextField tfVerified = new TextField("", "Team Verification");
        

        //upload image
        /*Label imageLabel = new Label("Image");
        Button selectImage = new Button("Select");
        TextField imageField = new TextField("", "Select picture", 10, TextArea.ANY);
        imageField.setEditable(false);

        String h;
        selectImage.addActionListener((evt) -> {
            Display.getInstance().openGallery((e) -> {
                if (e != null && e.getSource() != null) {
                    String filePath = (String) e.getSource();
                    imageField.setText(filePath.substring(filePath.lastIndexOf('/') + 1));
                    ch = filePath;
                }
            }, Display.GALLERY_IMAGE
            );

        }
        );
       

        Container photoContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));

        photoContainer.add(imageLabel);
        photoContainer.add(imageField);
        photoContainer.add(selectImage); */

        Button btnSubmit = new Button("Submit");

        btnSubmit.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0)  ) {
                    Dialog.show("Alert", "please", new Command("ok"));
                } else {

                    Teams c = new Teams(tfName.getText(), Integer.parseInt(tfGamersnb.getText()),Integer.parseInt(tfRank.getText()),tfVerified.getText());
                    //System.out.println(c);
                    if (ServiceTeam.getInstance().addTeam(c)) {
                        new ListTeamForm().show();
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        }
        );
        addAll(tfName, tfGamersnb,tfRank,tfVerified ,btnSubmit);

    }

    

   

   
}