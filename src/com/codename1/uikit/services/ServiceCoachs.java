/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Coach;
import com.codename1.uikit.entities.Game;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author hp
 */
public class ServiceCoachs {
    
     public ArrayList<Coach> coachs;
    public Coach coach;

    public static ServiceCoachs instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceCoachs() {
        req = new ConnectionRequest();
    }

    public static ServiceCoachs getInstance() {
        if (instance == null) {
            instance = new ServiceCoachs();
        }
        return instance;
    }

    public ArrayList<Coach> parseCoachs(String jsonText) {
        try {
            coachs = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> coachsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) coachsListJson.get("root");
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Coach g = new Coach();
                float id = Float.parseFloat(obj.get("id").toString());
                g.setId((int) id);
                //get username
                String usernameText = obj.get("user").toString();
                StringTokenizer usernameTokenizer = new StringTokenizer(usernameText, "=");
                usernameTokenizer.nextElement();
                String username = usernameTokenizer.nextElement().toString();
                g.setUsername(username.substring(0, username.length() - 1));
                //get game name
                String gameText = obj.get("game").toString();
                StringTokenizer gameTokenizer = new StringTokenizer(gameText, "=");
                gameTokenizer.nextElement();
                String gamename = gameTokenizer.nextElement().toString();
                g.setGamename(gamename.substring(0, gamename.length() - 1));
                
                g.setDescription(obj.get("description").toString());
                System.out.print(g);
                //Ajouter la tâche extraite de la réponse Json à la liste
                coachs.add(g);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return coachs;
    }
    
    

    public ArrayList<Coach> getAllCoachs() {
        String url = Statics.BASE_URL + "/api/coachs";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                coachs = parseCoachs(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return coachs;
    }
    
    public void deleteCoach() {
        String url = Statics.BASE_URL + "/api/coach/delete";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
        req.addArgument("username", MyApplication.loggedUser.getUsername());
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public void addCoach(String description,String gameName) {
        String url = Statics.BASE_URL + "/api/coach/add";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(true);
        req.setHttpMethod("POST");
        req.addArgument("username", MyApplication.loggedUser.getUsername());
        req.addArgument("description", description);
        req.addArgument("game", gameName);
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public void updateCoach(String description,String gameName) {
        String url = Statics.BASE_URL + "/api/coach/update";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(true);
        req.setHttpMethod("POST");
        req.addArgument("username", MyApplication.loggedUser.getUsername());
        req.addArgument("description", description);
        req.addArgument("game", gameName);
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
}
