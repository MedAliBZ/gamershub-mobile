/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
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
import com.codename1.uikit.entities.Cart;
import com.codename1.uikit.entities.Products;
import com.codename1.uikit.services.ServiceCart;
import com.codename1.uikit.services.ServiceProducts;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;

/**
 *
 * @author meriam
 */
public class ProductForm extends Form {

    private Products product;
 

    public ProductForm(Products product, Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        this.getToolbar().setTitle(product.getNameProduct());
        this.setLayout(BoxLayout.y());
        this.setUIID("Activate");
        //System.out.println("this is the detail category"+category);

        this.product = product;
       
        if (MyApplication.loggedUser.isIsAdmin()) {

            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_EDIT, e -> {
                Form editForm = new ProductEditForm(this.product, this);
                editForm.show();
            });
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_DELETE, e -> {
                ServiceProducts.getInstance().deleteProduct(this.product.getId());
                new ListProductsForm().show();
            });
        }
        try {
            //System.out.println(product);
            EncodedImage spinner = EncodedImage.create("/spinner.png");
            Container imageContainer = new Container(BoxLayout.xCenter());
            Container descriptionContainer = new Container(BoxLayout.xCenter());
            String url = Statics.BASE_URL + "/shop/images/" + product.getImage();
            Image gameImage = URLImage.createToStorage(spinner, url, url, URLImage.RESIZE_SCALE);

            gameImage = gameImage.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
            ImageViewer image = new ImageViewer(gameImage);
            imageContainer.setHeight(Display.getInstance().getDisplayHeight() / 3);

            TextArea descriptionTA = new TextArea(product.getDescription());
            TextArea tfName = new TextArea(product.getNameProduct());
            //Categories c = ServiceCategories.getInstance().getCategoryById(product.getCategoryId());
            TextArea tfCatID = new TextArea(product.getCategoryId()+"");
            TextArea tfPrice = new TextArea(product.getPrice() + "");
            TextArea tfQuantityStocked = new TextArea(product.getQuantityStocked() + "");

            descriptionTA.setEditable(false);
            descriptionTA.setFocusable(false);
            descriptionTA.setUIID("Label");
            descriptionTA.getAllStyles().setAlignment(TextArea.CENTER);

            tfName.setEditable(false);
            tfName.setFocusable(false);
            tfName.setUIID("Label");
            tfName.getAllStyles().setAlignment(TextArea.CENTER);

            tfCatID.setEditable(false);
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
            tfQuantityStocked.getAllStyles().setAlignment(TextArea.CENTER);

            descriptionContainer.add(descriptionTA);
            imageContainer.add(image);
            Button btnCart = new Button("Add to Cart");
             btnCart.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                     Cart cart = new Cart();
                         cart.setProduct(product);
                    if (ServiceCart.getInstance().addCart(cart)) {
                        
                    } else {
                        Dialog.show("erreur", "connection Failed", new Command("ok"));

                    }

                }
            }

        
        );
            this.addAll(imageContainer, descriptionContainer, tfName, tfCatID, tfPrice, tfQuantityStocked,btnCart);
        } catch (IOException e) {
            System.err.println(e);
        }

    }

}
