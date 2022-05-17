/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.TextArea;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Blog;
import com.codename1.uikit.services.ServiceBlog;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;

/**
 *
 * @author amira
 */
public class BlogForm extends Form{
    
     private Blog blog;
 

    public BlogForm(Blog blog, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle(blog.getTitle());
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");
        //System.out.println("this is the detail category"+category);

        this.blog = blog;
       
        if (MyApplication.loggedUser.isIsAdmin()) {

            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_EDIT, e -> {
                Form editForm = new BlogEditForm(this.blog, this);
                editForm.show();
            });
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_DELETE, e -> {
                ServiceBlog.getInstance().deleteBlog(this.blog.getId());
                new ListBlogForm().show();
            });
        }
        try {
            //System.out.println(blog);
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container imageContainer = new Container(BoxLayout.xCenter());
            Container descriptionContainer = new Container(BoxLayout.xCenter());
            String url = Statics.BASE_URL + "/img/blog/" + blog.getImage();
            Image blogImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);

            blogImage = blogImage.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
            ImageViewer image = new ImageViewer(blogImage);
            imageContainer.setHeight(Display.getInstance().getDisplayHeight() / 3);

            TextArea descriptionTA = new TextArea(blog.getDescription());
            TextArea tfTitle = new TextArea(blog.getTitle());
            //Categories c = ServiceCategories.getInstance().getCategoryById(blog.getCategoryId());
          
            descriptionTA.setEditable(false);
            descriptionTA.setFocusable(false);
            descriptionTA.setUIID("Label");
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);

            tfTitle.setEditable(false);
            tfTitle.setFocusable(false);
            tfTitle.setUIID("Label");
            tfTitle.getAllStyles().setAlignment(TextArea.CENTER);

            /*tfCatID.setEditable(false);
            tfCatID.setFocusable(false);
            tfCatID.setUIID("Label");
            tfCatID.getAllStyles().setAlignment(TextArea.CENTER);

            tfPrice.setEditable(false);
            tfPrice.setFocusable(false);
            tfPrice.setUIID("Label");
            tfPrice.getAllStyles().setAlignment(TextArea.CENTER);

            tfQuantityStocked.setEditable(false);
            tfQuantityStocked.setFocusable(false);
            tfQuantityStocked.setUIID("Label");
            tfQuantityStocked.getAllStyles().setAlignment(TextArea.CENTER);*/

            descriptionContainer.add(descriptionTA);
            imageContainer.add(image);
            /* btnCart.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                     Cart cart = new Cart();
                         cart.setBlog(blog);
                    if (ServiceCart.getInstance().addCart(cart)) {
                        
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        
        );*/
            this.addAll(imageContainer, descriptionContainer, tfTitle);
        } catch (IOException e) {
            System.err.println(e);
        }

    }

}
