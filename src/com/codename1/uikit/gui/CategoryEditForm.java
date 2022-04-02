/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Categories;
import com.codename1.uikit.services.ServiceCategories;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;

/**
 *
 * @author meriam
 */
public class CategoryEditForm extends Form {

    private Categories category;

    public CategoryEditForm(Categories category, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle("Category info");
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");
        //System.out.println("this is the detail category"+category);

        this.category = category;

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_DELETE, e -> {
            ServiceCategories.getInstance().deleteCategory(this.category.getId());
            new ListCategoriesForm().show();
        });

        try {

            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container imageContainer = new Container(BoxLayout.xCenter());
            Container descriptionContainer = new Container(BoxLayout.xCenter());
            Container NameContainer = new Container(BoxLayout.xCenter());
            Container FromContainer = new Container(BoxLayout.y());

            Label nameLabel = new Label("Category Name: ");
            Label descLabel = new Label("Description : ");
            String url = Statics.BASE_URL + "/shop/images/" + category.getImage();
            Image categoryImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);

            categoryImage = categoryImage.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
            ImageViewer image = new ImageViewer(categoryImage);
            imageContainer.setHeight(Display.getInstance().getDisplayHeight() / 3);

            TextField categoryNameTA = new TextField(category.getNameCategory(), "Category Name");
            TextField descriptionTA = new TextField(category.getDescription(), "Description");

            NameContainer.addAll(nameLabel, categoryNameTA);
            descriptionContainer.addAll(descLabel, descriptionTA);
            FromContainer.addAll(NameContainer, descriptionContainer);
            imageContainer.add(image);

            Button EditBt = new Button("Edit Category");

            EditBt.addActionListener(
                    new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if ((categoryNameTA.getText().length() == 0) || (descriptionTA.getText().length() == 0)) {
                        Dialog.show("Alert", "Fill all the fields", new Command("ok"));
                    } else {

                        category.setNameCategory(categoryNameTA.getText());
                        category.setDescription(descriptionTA.getText());

                        if (ServiceCategories.getInstance().updateCategory(category)) {
                             new ListCategoriesForm().show();

                        } else {
                            Dialog.show("erreur", "connection Failed", new Command("ok"));

                        }

                    }
                }

            }
            );
            this.addAll(imageContainer, FromContainer, EditBt);

        } catch (IOException e) {
            System.err.println(e);
        }

    }

}
