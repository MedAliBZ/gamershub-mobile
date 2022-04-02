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
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author meriam
 */
public class ServiceCategories {

    public Boolean resultOK;

    public ArrayList<Categories> categoriesList;
    public static ServiceCategories instance;
    private ConnectionRequest req;
    public Categories category;

    private ServiceCategories() {
        req = new ConnectionRequest();
    }

    public static ServiceCategories getInstance() {
        if (instance == null) {
            instance = new ServiceCategories();
        }
        return instance;
    }

    public ArrayList<Categories> parseCategories(String jsonText) {

        try {
            categoriesList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> categoriesListJson;

            categoriesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) categoriesListJson.get("root");
            for (Map<String, Object> obj : list) {
                Categories c = new Categories();
                int id = (int) Float.parseFloat(obj.get("id").toString());
                String nameCategory = obj.get("nameCategory").toString();
                String image = obj.get("image").toString();
                String Description = obj.get("Description").toString();

                c.setId(id);
                c.setDescription(Description);
                c.setImage(image);
                c.setNameCategory(nameCategory);
                categoriesList.add(c);

            }

        } catch (IOException ex) {

        }

        return categoriesList;

    }

    public ArrayList<Categories> getAllCategories() {
        String url = Statics.BASE_URL + "/api/AllCategories";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            public void actionPerformed(NetworkEvent ev) {
                categoriesList = parseCategories(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categoriesList;
    }

    public Boolean addCategory(Categories c) {

        String url = Statics.BASE_URL + "/api/createCategory?nameCategory=" + c.getNameCategory() + "&Description=" + c.getDescription() + "&image=" + c.getImage();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" Category is added", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }

//    public Categories getCategoryById(int id) {
//
//        String url = Statics.BASE_URL + "/api/getCategoryById/" + id;
//        req.setUrl(url);
//        category = new Categories();
//
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            public void actionPerformed(NetworkEvent evt) {
//                JSONParser json = new JSONParser();
//
//                try {
//                    String data = new String(req.getResponseData());
//                    System.out.println("data: " + data);
//
//                    Map<String, Object> obj = json.parseJSON(new CharArrayReader(new String(data).toCharArray()));
//
//                    category.setId(id);
//                    category.setNameCategory(obj.get("nameCategory").toString());
//                    category.setDescription(obj.get("Description").toString());
//                    category.setImage(obj.get("image").toString());
//                    // System.out.println("category in service: " + category);
//
//                } catch (IOException ex) {
//                    System.out.println("" + ex.getMessage());
//                }
//
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        //System.out.println("category in service before returning: " + category);
//        return category;
//    }

    public void deleteCategory(int id) {
        String url = Statics.BASE_URL + "/api/deleteCategory/" + id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        // System.out.println("category deleted");

    }

    public Boolean updateCategory(Categories c) {
        String url = Statics.BASE_URL + "/api/updateCategory/" + c.getId() + "?nameCategory=" + c.getNameCategory() + "&Description=" + c.getDescription() + "&image=" + c.getImage();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOK;
    }

}
