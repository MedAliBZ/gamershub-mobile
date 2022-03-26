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
public class ServiceProducts {

    public Boolean resultOK;

    public ArrayList<Products> ProductsList;
    public static ServiceProducts instance;
    private ConnectionRequest req;
    public Products product;

    private ServiceProducts() {
        req = new ConnectionRequest();
    }

    public static ServiceProducts getInstance() {
        if (instance == null) {
            instance = new ServiceProducts();
        }
        return instance;
    }

    public ArrayList<Products> parseProducts(String jsonText) {

        try {
            ProductsList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> productsListJson;

            productsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) productsListJson.get("root");
            for (Map<String, Object> obj : list) {
                Products c = new Products();
                int id = (int) Float.parseFloat(obj.get("id").toString());

                String categoryItem1 = obj.get("category").toString();
                StringTokenizer st = new StringTokenizer(categoryItem1, ",");
                StringTokenizer st2 = new StringTokenizer(st.nextToken(), ":");
                StringTokenizer st3 = new StringTokenizer(st2.nextToken(), "=");
                st3.nextToken();
                //System.out.println(st3.nextToken());
                int categoryId = (int) Float.parseFloat(st3.nextToken());
                int quantityStocked = (int) Float.parseFloat(obj.get("quantityStocked").toString());
                String nameProduct = obj.get("nameProduct").toString();
                String image = obj.get("image").toString();
                String Description = obj.get("description").toString();
                double price = (double) Float.parseFloat(obj.get("price").toString());

                c.setId(id);
                c.setDescription(Description);
                c.setImage(image);
                c.setNameProduct(nameProduct);
                c.setCategoryId(categoryId);
                c.setPrice(price);
                c.setQuantityStocked(quantityStocked);
                ProductsList.add(c);

            }

        } catch (IOException ex) {

        }

        return ProductsList;

    }

    public ArrayList<Products> getAllProducts() {
        String url = Statics.BASE_URL + "/api/AllProducts";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            public void actionPerformed(NetworkEvent ev) {
                ProductsList = parseProducts(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return ProductsList;
    }

    public void deleteProduct(int id) {
        String url = Statics.BASE_URL + "/api/deleteProduct/" + id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        // System.out.println("category deleted");

    }

    public Boolean addProduct(Products c) {

        String url = Statics.BASE_URL + "/api/createProduct?nameProduct=" + c.getNameProduct() + "&description=" + c.getDescription()
                + "&image=" + c.getImage() + "&categoryId=" + c.getCategoryId() + "&price=" + c.getPrice() + "&quantityStocked=" + c.getQuantityStocked();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" product is added", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }

    public Boolean EditProduct(Products c) {
        System.out.println("this :"+c);
        String url = Statics.BASE_URL + "/api/updateProduct/" + c.getId() + "?nameProduct=" + c.getNameProduct() + "&description=" + c.getDescription()
                + "&image=" + c.getImage() + "&price=" + c.getPrice() + "&quantityStocked=" + c.getQuantityStocked();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" product is updated", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }
}
