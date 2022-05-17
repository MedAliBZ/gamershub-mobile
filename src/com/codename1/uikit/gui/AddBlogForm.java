/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
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
import com.codename1.uikit.entities.Categories;
import com.codename1.uikit.entities.Blog;
import com.codename1.uikit.services.ServiceCategories;
import com.codename1.uikit.services.ServiceBlog;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author amira
 */
public class AddBlogForm extends Form{
    
     String ch;

    public AddBlogForm(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Add Blog");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField tfTitle = new TextField("", "Blog Title");
        TextField tfDes = new TextField("", "Blog Description");
        //TextField tfCatID = new TextField("", "Category ID");
//combo 

       
        //upload image
        Label imageLabel = new Label("Image");
        Button selectImage = new Button("Select");
        TextField imageField = new TextField("", "Select picture", 10, TextArea.ANY);
        imageField.setEditable(false);

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
                if ((tfTitle.getText().length() == 0) || (tfDes.getText().length() == 0)) {
                    Dialog.show("Alert", "please fill all the Fields", new Command("ok"));
                } else {

                    Blog c = new Blog();
                    /*String catId = categoryBox.getSelectedItem().toString();
                    StringTokenizer st = new StringTokenizer(catId,",");

                    c.setCategoryId(Integer.parseInt(st.nextToken()));*/
                    c.setDescription(tfDes.getText());
                    //c.setImage(ch);
                    c.setTitle(tfTitle.getText());
                   /* c.setPrice(Double.parseDouble(tfPrice.getText()));
                    c.setQuantityStocked(Integer.parseInt(tfQuantityStocked.getText()));*/
                   
                   
                    if (ServiceBlog.getInstance().addBlog(c)) {
                        new ListBlogForm().show();
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        }
        );
        addAll(tfTitle, tfDes, btnSubmit);

    }
    
}
