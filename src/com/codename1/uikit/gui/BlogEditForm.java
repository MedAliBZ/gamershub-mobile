/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.entities.Blog;
import com.codename1.uikit.services.ServiceBlog;

/**
 *
 * @author amira
 */
public class BlogEditForm extends Form{
    
    public BlogEditForm(Blog blog, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Edit Blog");
        setLayout(BoxLayout.y());
        this.setUIID("Activate");
        TextField tfTitle = new TextField("", "Blog Title");
        TextField tfDes = new TextField("", "Blog Description");
        


      

        tfTitle.setText(blog.getTitle());
        tfDes.setText(blog.getDescription());



        Button btnSubmit = new Button("Edit Blog");

        btnSubmit.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfTitle.getText().length() == 0) || (tfDes.getText().length() == 0)) {
                    Dialog.show("Alert", "please fill all the Fields", new Command("ok"));
                } else {
                   
                    blog.setDescription(tfDes.getText());
                    blog.setTitle(tfTitle.getText());
                    
                    if (ServiceBlog.getInstance().EditBlog(blog)) {
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
