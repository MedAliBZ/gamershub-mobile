/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.entities.Blog;
import com.codename1.uikit.services.ServiceBlog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.codename1.uikit.utils.Statics;


/**
 *
 * @author amira
 */
public class ListBlogForm extends BaseForm{
    
    public ListBlogForm() {
        super.addSideMenu();
        setTitle("Blog List");
        setUIID("Activate");


        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {

            Form addForm = new AddBlogForm(this);
            addForm.show();
        });

        List<Blog> blog = new ArrayList();
        blog = ServiceBlog.getInstance().getAllBlog();

        for (int i = 0; i < blog.size(); i++) {
            //System.out.println(blog.get(i));
            this.add(listOfBlog(blog.get(i)));
        }

    }

    public Container listOfBlog(Blog c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnCat = new Container(BoxLayout.y());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            Label lbTilte = new Label();
            Label lbDescription = new Label();

            lbTilte.setText(c.getTitle().toUpperCase());
            lbDescription.setText(c.getDescription() + "");

            ctninfo.addAll(lbTilte, lbDescription);
            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;
            try {
                enc = EncodedImage.create("/spinner.png");
                //String pic =c.getImage().substring(0,c.getImage().length()-1);
                String url = Statics​.BASE_URL + "/img/blog/" + c.getImage();

                //System.out.println(url);
                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

                imgv = new ImageViewer(img);

                imgv.addPointerReleasedListener((evnt) -> {
                    Form details = new BlogForm(c, this);
                    details.show();
                });

                ctn.addAll(imgv, ctninfo);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        return ctn;
    }
}
