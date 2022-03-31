/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.services;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.uikit.entities.Cart;
import com.codename1.uikit.entities.Categories;
import com.codename1.uikit.entities.Products;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author meriam
 */
public class ServiceCart {

    public Boolean resultOK;
    private ConnectionRequest req;
    public static ServiceCart instance;
    public ArrayList<Cart> cartsList;

    private ServiceCart() {
        req = new ConnectionRequest();
    }

    public static ServiceCart getInstance() {
        if (instance == null) {
            instance = new ServiceCart();
        }
        return instance;
    }

    public Boolean addCart(Cart c) {

        String url = Statics.BASE_URL + "/api/addToCart?productId=" + c.getProduct().getId();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" item is added to cart", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }

    public ArrayList<Cart> parseCarts(String jsonText) {

        try {

            System.out.println("here " + jsonText);
            cartsList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> cartsListJson;

            cartsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) cartsListJson.get("root");
            for (Map<String, Object> obj : list) {
                Cart c = new Cart();

                String productInfo = obj.get("product").toString();
                StringTokenizer st = new StringTokenizer(productInfo, ",");
                StringTokenizer stID = new StringTokenizer(st.nextToken(), "=");
                stID.nextToken();
                int idProd = (int) Float.parseFloat(stID.nextToken());

                StringTokenizer stName = new StringTokenizer(st.nextToken(), "=");
                stName.nextToken();
                String nameProd = stName.nextToken();

                StringTokenizer stPrice = new StringTokenizer(st.nextToken(), "=");
                stPrice.nextToken();
                Double price = Double.parseDouble(stPrice.nextToken());

                st.nextToken();
                st.nextToken();
                st.nextToken();
                st.nextToken();
                st.nextToken();
                st.nextToken();

                StringTokenizer stImage = new StringTokenizer(st.nextToken(), "=");
                stImage.nextToken();
                String imageString = stImage.nextToken();
                String image = imageString.substring(0, imageString.length() - 1);

                Products product = new Products();
                product.setId(idProd);
                product.setImage(image);
                product.setNameProduct(nameProd);
                product.setPrice(price);
                //System.out.println(product);

                int quantity = (int) Float.parseFloat(obj.get("quantity").toString());

                String orderInfo = obj.get("myOrder").toString();
                StringTokenizer st1 = new StringTokenizer(orderInfo, ",");
                StringTokenizer stOrderID = new StringTokenizer(st1.nextToken(), "=");
                stOrderID.nextToken();

                int orderId = (int) Float.parseFloat(stOrderID.nextToken());

                c.setProduct(product);
                c.setQuantity(quantity);
                c.setOrderId(orderId);

                cartsList.add(c);

            }

        } catch (IOException ex) {

        }

        return cartsList;

    }

    public ArrayList<Cart> getAllCarts() {
        String url = Statics.BASE_URL + "/api/AllCarts";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            public void actionPerformed(NetworkEvent ev) {
                cartsList = parseCarts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return cartsList;
    }

}
