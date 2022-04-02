/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import com.codename1.uikit.entities.Categories;
import com.codename1.uikit.services.ServiceCategories;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author meriam
 */
public class AddCategoryForm extends Form {

    String ch;

    public AddCategoryForm(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Add Category");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField tfName = new TextField("", "category Name");
        TextField tfDes = new TextField("", "category Description");

        //upload image
        Label imageLabel = new Label("Image");
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
        photoContainer.add(selectImage);

        Button btnSubmit = new Button("Submit");

        btnSubmit.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (tfDes.getText().length() == 0) || (imageField.getText().length() == 0)) {
                    Dialog.show("Alert", "please", new Command("ok"));
                } else {

                    Categories c = new Categories(tfName.getText(), tfDes.getText(), ch);
                    if (ServiceCategories.getInstance().addCategory(c)) {
                        new ListCategoriesForm().show();
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        }
        );
        addAll(tfName, tfDes, photoContainer, btnSubmit);

    }
}
