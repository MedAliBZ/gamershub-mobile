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
import com.codename1.uikit.entities.Sessioncoaching;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 *
 * @author hp
 */
public class ServiceSessions {
    
    public ArrayList<Sessioncoaching> sessions;
    public Sessioncoaching session;

    public static ServiceSessions instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceSessions() {
        req = new ConnectionRequest();
    }

    public static ServiceSessions getInstance() {
        if (instance == null) {
            instance = new ServiceSessions();
        }
        return instance;
    }

    public ArrayList<Sessioncoaching> parseSessions(String jsonText) {
        try {
            sessions = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> sessionsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) sessionsListJson.get("root");
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Sessioncoaching g = new Sessioncoaching();
                float id = Float.parseFloat(obj.get("id").toString());
                g.setId((int) id);
                //get username
                String usernameText = obj.get("user").toString();
                StringTokenizer usernameTokenizer = new StringTokenizer(usernameText, "=");
                usernameTokenizer.nextElement();
                String username = usernameTokenizer.nextElement().toString();
                g.setUsername(username.substring(0, username.length() - 1));
                //get game name
                String coachText = obj.get("coach").toString();
                StringTokenizer coachTokenizer = new StringTokenizer(coachText, "=");
                coachTokenizer.nextElement();
                coachTokenizer.nextElement();
                String coachname = coachTokenizer.nextElement().toString();
                g.setCoach(coachname.substring(0, coachname.length() - 2));
                
                g.setDescription(obj.get("description").toString());
                g.setDate_debut(obj.get("date_debut").toString());
                g.setDate_fin(obj.get("date_fin").toString());
                g.setPrix(obj.get("prix").toString());
                System.out.print(g);
                //Ajouter la tâche extraite de la réponse Json à la liste
                sessions.add(g);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return sessions;
    }
    
    
    public ArrayList<Sessioncoaching> getAllSessions() {
        String url = Statics.BASE_URL + "/api/sessions";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                sessions = parseSessions(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return sessions;
    }
    
    public void deleteSession(int id) {
        String url = Statics.BASE_URL + "/api/session/delete";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
        req.addArgument("id",String.valueOf(id));
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public void addSession(String description,String username,String date_debut, String date_fin, String prix) {
        String url = Statics.BASE_URL + "/api/session/add";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(true);
        req.setHttpMethod("POST");
        req.addArgument("username",username );
        req.addArgument("description", description);
        req.addArgument("coachname",MyApplication.loggedUser.getUsername());
        req.addArgument("date_debut",date_debut);
        req.addArgument("date_fin",date_fin );
        req.addArgument("prix",prix);
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public void updateSession(String description,String username, String date_debut, String date_fin, String prix,int id) {
        String url = Statics.BASE_URL + "/api/session/update";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(true);
        req.setHttpMethod("POST");
        req.addArgument("username",username );
        req.addArgument("description", description);
        req.addArgument("date_debut",date_debut);
        req.addArgument("date_fin",date_fin );
        req.addArgument("prix",prix);
        req.addArgument("id",Integer.toString(id));
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
}
