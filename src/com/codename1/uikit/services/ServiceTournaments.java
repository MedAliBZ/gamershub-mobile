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
import com.codename1.uikit.entities.Tournaments;
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
public class ServiceTournaments {
    
    public Boolean resultOK;

    public ArrayList<Tournaments> TournamentsList;
    public static ServiceTournaments instance;
    private ConnectionRequest req;
    public Tournaments tournaments;

    private ServiceTournaments() {
        req = new ConnectionRequest();
    }

    public static ServiceTournaments getInstance() {
        if (instance == null) {
            instance = new ServiceTournaments();
        }
        return instance;
    }

    public ArrayList<Tournaments> parseTournaments(String jsonText) {

        try {
            TournamentsList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> tournamentsListJson;

            tournamentsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tournamentsListJson.get("root");
            for (Map<String, Object> obj : list) {
                Tournaments c = new Tournaments();
                int id = (int) Float.parseFloat(obj.get("id").toString());


                //System.out.println(st3.nextToken());
                int teamSize = (int) Float.parseFloat(obj.get("teamSize").toString());
                String name = obj.get("name").toString();
                String image = obj.get("images").toString();
                String Description = obj.get("decription").toString();

                c.setId(id);
                c.setDescription(Description);
                c.setImage(image);
                c.setName(name);
                c.setTeamSize(teamSize);
                TournamentsList.add(c);

            }

        } catch (IOException ex) {

        }

        return TournamentsList;

    }

    public ArrayList<Tournaments> getAllTournaments() {
        String url = Statics.BASE_URL + "/api/AllTournaments";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            public void actionPerformed(NetworkEvent ev) {
                TournamentsList = parseTournaments(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return TournamentsList;
    }

    public void deleteTournaments(int id) {
        String url = Statics.BASE_URL + "/api/deleteTournaments/" + id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        // System.out.println("category deleted");

    }

    public Boolean addTournaments(Tournaments c) {

        String url = Statics.BASE_URL + "/api/createTournaments?name=" + c.getName() + "&description=" + c.getDescription()
                + "&teamSize=" + c.getTeamSize();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" tournament is added", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }

    public Boolean EditTournaments(Tournaments c) {
        System.out.println("this :"+c);
        String url = Statics.BASE_URL + "/api/updateTournaments/" + c.getId() + "?name=" + c.getName() + "&description=" + c.getDescription()
                + "&teamSize=" + c.getTeamSize();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" tournament is updated", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }
    
}
