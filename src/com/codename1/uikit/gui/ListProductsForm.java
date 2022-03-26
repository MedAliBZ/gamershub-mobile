/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Products;
import com.codename1.uikit.services.ServiceProducts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.codename1.uikit.utils.Statics;

/**
 *
 * @author meriam
 */
public class ListProductsForm extends BaseForm{
      public ListProductsForm() {
        super.addSideMenu();
        setTitle("Products List");
        setUIID("Activate");
            getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ADD, e -> {
           
                   Form addForm = new AddProductForm(this);
                   addForm.show();
        });

        List<Products> product = new ArrayList();
        product = ServiceProducts.getInstance().getAllProducts();

        for (int i = 0; i < product.size(); i++) {
              //System.out.println(product.get(i));
            this.add(listOfProducts(product.get(i)));
        }

    }
     public Container listOfProducts(Products c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnCat = new Container(BoxLayout.y());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            Label lbNameProduct = new Label();
            Label lbPrice = new Label();

            lbNameProduct.setText(c.getNameProduct().toLowerCase());
            lbPrice.setText(c.getPrice()+"");

            ctninfo.addAll(lbNameProduct, lbPrice);
            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;
            try {
                enc = EncodedImage.create("/spinner.png");
               //String pic =c.getImage().substring(0,c.getImage().length()-1);
                String url = Statics​.BASE_URL + "/shop/images/"+c.getImage() ;

                //System.out.println(url);
                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

                imgv = new ImageViewer(img);

        
                    imgv.addPointerReleasedListener((evnt) -> {
                        Form details = new ProductForm(c, this);
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
