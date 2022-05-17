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
import com.codename1.uikit.entities.Player;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author amira
 */
public class ServicePlayer {
     public Boolean resultOK;

    public ArrayList<Player> PlayerList;
    public static ServicePlayer instance;
    private ConnectionRequest req;
    public Player player;

    private ServicePlayer() {
        req = new ConnectionRequest();
    }

    public static ServicePlayer getInstance() {
        if (instance == null) {
            instance = new ServicePlayer();
        }
        return instance;
    }

    public ArrayList<Player> parsePlayer(String jsonText) {

        try {
            PlayerList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> blogListJson;

            blogListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) blogListJson.get("root");
                                        System.out.println("parsePlayer works");

            for (Map<String, Object> obj : list) {
                            System.out.println("parsePlayer works");
                Player c = new Player();
                int id = (int) Float.parseFloat(obj.get("id").toString());
                c.setId((int) id);
                String userText = obj.get("user").toString();
                StringTokenizer st = new StringTokenizer(userText, "=");
                st.nextToken();
                int userId = (int) Float.parseFloat(st.nextToken());
                //System.out.println(st3.nextToken());
                String rank = obj.get("rank").toString();


                c.setId(id);
                c.setRank(rank);
                c.setUser(userId);
                PlayerList.add(c);

            }

        } catch (IOException ex) {
            System.out.println(ex);
        }

        return PlayerList;

    }

    public ArrayList<Player> getAllPlayer() {
        String url = Statics.BASE_URL + "/api/AllPlayer";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent ev) {
                PlayerList = parsePlayer(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return PlayerList;
    }

    public void deletePlayer(int id) {
        String url = Statics.BASE_URL + "/api/deletePlayer/" + id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        // System.out.println("category deleted");

    }

    public Boolean addPlayer(Player c) {

        String url = Statics.BASE_URL + "/api/createPlayer?user=" + c.getUser() + "& rank=" + c.getRank();
                
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" blog is added", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }

    public Boolean EditPlayer(Player c) {
        System.out.println("this :"+c);
        String url = Statics.BASE_URL + "/api/updatePlayer/" + c.getId() + "?rank=" + c.getRank();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" blog is updated", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }
    
}
