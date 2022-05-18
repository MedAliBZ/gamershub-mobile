/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import com.codename1.uikit.entities.Rewards;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author amira
 */
public class ServiceRewards {
    
    public Boolean resultOK;

    public ArrayList<Rewards> RewardsList;
    public static ServiceRewards instance;
    private ConnectionRequest req;
    public Rewards rewards;

    private ServiceRewards() {
        req = new ConnectionRequest();
    }

    public static ServiceRewards getInstance() {
        if (instance == null) {
            instance = new ServiceRewards();
        }
        return instance;
    }

    public ArrayList<Rewards> parseRewards(String jsonText) {

        try {
            RewardsList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> rewardsListJson;

            rewardsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) rewardsListJson.get("root");
                                        System.out.println("parseRewards works");

            for (Map<String, Object> obj : list) {
                            System.out.println("parseRewards works");
                Rewards c = new Rewards();
                int id = (int) Float.parseFloat(obj.get("id").toString());
                c.setId((int) id);
                //System.out.println(st3.nextToken());
                String type = obj.get("type").toString();
                int quantity = (int) Float.parseFloat(obj.get("quantity").toString());

                c.setId(id);
                c.setType(type);
                c.setQuantity(quantity);
                RewardsList.add(c);

            }

        } catch (IOException ex) {
            System.out.println(ex);
        }

        return RewardsList;

    }

    public ArrayList<Rewards> getAllRewards() {
        String url = Statics.BASE_URL + "/api/AllRewards";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent ev) {
                RewardsList = parseRewards(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return RewardsList;
    }

    public void deleteRewards(int id) {
        String url = Statics.BASE_URL + "/api/deleteRewards/" + id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        // System.out.println("category deleted");

    }

    public Boolean addRewards(Rewards c) {

        String url = Statics.BASE_URL + "/api/createRewards?type=" + c.getType() + "&quantity=" + c.getQuantity()
                 ;
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" rewards is added", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }

    public Boolean EditRewards(Rewards c) {
        System.out.println("this :"+c);
        String url = Statics.BASE_URL + "/api/updateRewards/" + c.getId() + "?type=" + c.getType() + "&quantity=" + c.getQuantity()
                ;
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" rewards is updated", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }
}
