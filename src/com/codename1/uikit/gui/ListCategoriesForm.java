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
import com.codename1.uikit.entities.Categories;
import com.codename1.uikit.services.ServiceCategories;
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
 * @author meriam
 */
public class ListCategoriesForm extends BaseForm {

    public ListCategoriesForm() {
        super.addSideMenu();
        setTitle("Categories List");
        setUIID("Activate");
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
           
                   Form addForm = new AddCategoryForm(this);
                   addForm.show();
        });

        List<Categories> category = new ArrayList();
        category = ServiceCategories.getInstance().getAllCategories();

        for (int i = 0; i < category.size(); i++) {
            this.add(listOfCategories(category.get(i)));
        }

    }

    public Container listOfCategories(Categories c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnCat = new Container(BoxLayout.y());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            Label lbNameCategory = new Label();
            Label lbDescription = new Label();
            Label lbId = new Label();

            lbNameCategory.setText(c.getNameCategory().toLowerCase());
            lbDescription.setText(c.getDescription().toLowerCase());

            ctninfo.addAll(lbNameCategory, lbDescription);
            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;
            try {
                enc = EncodedImage.create("/spinner.png");

                String url = Statics​.BASE_URL + "/shop/images/" + c.getImage();

                //System.out.println(url);
                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

                imgv = new ImageViewer(img);

                if (MyApplication.loggedUser.isIsAdmin()) {
                    imgv.addPointerReleasedListener((evnt) -> {
                        Form details = new CategoryEditForm(c, this);
                        details.show();
                    });
                }

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
