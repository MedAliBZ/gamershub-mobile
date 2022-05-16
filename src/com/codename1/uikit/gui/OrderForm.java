/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Order;
import java.io.IOException;

/**
 *
 * @author meriam
 */
public class OrderForm extends Form {

    public OrderForm(Order o) {

        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_CHECK, e -> {
            Form ListProductsForm = new ListProductsForm();
            ListProductsForm.show();
        });

        setTitle("My Order");
        setLayout(BoxLayout.y());
        setUIID("Activate");

        Label orderId = new Label("order Id :");
        Label totalPrice = new Label("Total Price : ");
        Label orderIdInfo = new Label(o.getId() + "");
        Label totalPriceInfo = new Label(o.getTotalprice() + "");

        orderId.getAllStyles().setFgColor(0x0ffff);
        totalPrice.getAllStyles().setFgColor(0x0ffff);

        Container ctnId = new Container(BoxLayout.x());
        Container ctnPrice = new Container(BoxLayout.x());
        ctnId.addAll(orderId, orderIdInfo);
        ctnPrice.addAll(totalPrice, totalPriceInfo);
        String data = "Order number : " + o.getId() + " And the Total Price is : " + o.getTotalprice() + " and the user is : " + MyApplication.loggedUser.getName() + " Tahnk you For the purchase ";

//img loading
        Image img;
        ImageViewer imgv;
        EncodedImage enc;
        Label imageLabel = new Label("Scan me : ");
        imageLabel.getAllStyles().setFgColor(0x0ffff);
        Container ctn = new Container(BoxLayout.x());
        try {
            enc = EncodedImage.create("/spinner.png");

            String url = "http://api.qrserver.com/v1/create-qr-code/?data=" +data;
            img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);
//            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
//            ctn.setHeight(Display.getInstance().getDisplayHeight() / 3);

            imgv = new ImageViewer(img);

            ctn.addAll(imageLabel, imgv);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        addAll(ctnId, ctnPrice, ctn);

    }

}
