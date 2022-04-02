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
import com.codename1.uikit.entities.Cart;
import com.codename1.uikit.entities.Categories;
import com.codename1.uikit.entities.Order;
import com.codename1.uikit.entities.Products;
import com.codename1.uikit.services.ServiceCart;
import com.codename1.uikit.services.ServiceCategories;
import com.codename1.uikit.services.ServiceOrder;
import com.codename1.uikit.services.ServiceProducts;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.codename1.uikit.utils.Statics;
import java.util.StringTokenizer;

/**
 *
 * @author meriam
 */
public class ListCartsForm extends Form {

    Double orderTotalPrice;
    int orderId = 0;

    public ListCartsForm(Form previous) {
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("My Shopping Cart");
        setLayout(BoxLayout.y());
        setUIID("Activate");
        List<Cart> cart = new ArrayList();
        cart = ServiceCart.getInstance().getAllCarts();
        orderTotalPrice = 0.0;

        for (int i = 0; i < cart.size(); i++) {
            this.add(listOfCarts(cart.get(i)));
            orderTotalPrice += (cart.get(i).getProduct().getPrice() * cart.get(i).getQuantity());
            orderId = cart.get(i).getOrderId();
            //System.out.println("here again"+orderTotalPrice);

        }

        Button btnFinalizeOrder = new Button("Finalize Order");
        if (cart.size() != 0) {
            add(btnFinalizeOrder);
        }
        btnFinalizeOrder.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                Order o = new Order();
                o.setUserId(MyApplication.loggedUser.getId());
                o.setTotalprice(orderTotalPrice);
                o.setId(orderId);

                if (ServiceOrder.getInstance().updateOrder(o)) {
                    Form orderForm = new OrderForm(o);
                    orderForm.show();
                } else {
                    Dialog.show("erreur", "connection Failed", new Command("ok"));

                }
            }

        }
        );
        ;
    }

    public Container listOfCarts(Cart c) {
        Container ctn = new Container(BoxLayout.x());
        Container ctnQuantity = new Container(BoxLayout.x());
        Container ctninfo = new Container(BoxLayout.y());

        try {

            Label lbQuantity = new Label();
            Label lbQuantitylb = new Label("Quantity ordered : ");
            Label lbNameProduct = new Label();
            Label lbPrice = new Label();

            lbNameProduct.setText(c.getProduct().getNameProduct());
            lbPrice.setText(c.getProduct().getPrice() + "");
            lbQuantity.setText(c.getQuantity() + "");
            lbQuantitylb.getAllStyles().setFgColor(0x0ffff);
            ctnQuantity.addAll(lbQuantitylb, lbQuantity);
            ctninfo.addAll(lbNameProduct, lbPrice, ctnQuantity);

            //img loading
            Image img;
            ImageViewer imgv;
            EncodedImage enc;
            try {
                enc = EncodedImage.create("/spinner.png");

                String url = Statics​.BASE_URL + "/shop/images/" + c.getProduct().getImage();
                img = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE);

                imgv = new ImageViewer(img);

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
