/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.codename1.uikit.entities.User;

/**
 *
 * @author MAB
 */
public class ServiceUser {

    public ArrayList<User> users;
    public User user;
    private boolean isRegistred;

    public static ServiceUser instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceUser() {
        req = new ConnectionRequest();
    }

    public static ServiceUser getInstance() {
        if (instance == null) {
            instance = new ServiceUser();
        }
        return instance;
    }

    public ArrayList<User> parseUsers(String jsonText) {
        try {
            users = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");
            System.out.println(list);
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                User u = new User();
                float id = Float.parseFloat(obj.get("id").toString());
                u.setId((int) id);
                u.setName(obj.get("name") == null ? "" : obj.get("name").toString());
                u.setUsername(obj.get("username").toString());
                u.setSecondName(obj.get("secondName") == null ? "" : obj.get("secondName").toString());
                u.setEmail(obj.get("email").toString());
                //Ajouter la tâche extraite de la réponse Json à la liste
                users.add(u);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return users;
    }

    public void login(String jsonText) {
        try {
            user = new User();
            JSONParser j = new JSONParser();

            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            float id = Float.parseFloat(usersListJson.get("id").toString());
            user.setId((int) id);
            user.setName(usersListJson.get("name") == null ? "" : usersListJson.get("name").toString());
            user.setUsername(usersListJson.get("username").toString());
            user.setSecondName(usersListJson.get("secondName") == null ? "" : usersListJson.get("secondName").toString());
            user.setEmail(usersListJson.get("email").toString());
            MyApplication.loggedUser = user;
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public boolean register(String jsonText, int code) {
        try {
            user = new User();
            JSONParser j = new JSONParser();

            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            System.out.println(usersListJson);
            if(code != 201){
                user.setError(usersListJson.get("error").toString());
                MyApplication.loggedUser = user;
                return false;
            }
            else{
                return true;
            }
        } catch (IOException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public ArrayList<User> getAllUsers() {
        String url = Statics.BASE_URL + "/api/users";
        req.setUrl(url);
        req.setPost(false);
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }

    public void loginUser(String username, String password) {
        String url = Statics.BASE_URL + "/api/login";
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("username", username);
        req.addArgument("password", password);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                if (req.getResponseCode() == 200) {
                    login(new String(req.getResponseData()));
                }
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public boolean registerUser(String username, String password, String confirmPassword, String email) {
        String url = Statics.BASE_URL + "/api/register";
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("username", username);
        req.addArgument("password", password);
        req.addArgument("confirmPassword", confirmPassword);
        req.addArgument("email", email);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                if (req.getResponseCode() == 201 || req.getResponseCode() == 400 || req.getResponseCode() == 401) {
                    isRegistred = register(new String(req.getResponseData()), req.getResponseCode());
                }
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return isRegistred;
    }
}
