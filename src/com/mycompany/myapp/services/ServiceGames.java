package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Game;
import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MAB
 */
public class ServiceGames {

    public ArrayList<Game> games;
    public Game game;

    public static ServiceGames instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceGames() {
        req = new ConnectionRequest();
    }

    public static ServiceGames getInstance() {
        if (instance == null) {
            instance = new ServiceGames();
        }
        return instance;
    }

    public ArrayList<Game> parseGames(String jsonText) {
        try {
            games = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> gamesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) gamesListJson.get("root");
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                Game g = new Game();
                float id = Float.parseFloat(obj.get("id").toString());
                g.setId((int) id);
                g.setName(obj.get("name").toString());
                g.setImage(obj.get("image").toString());
                g.setDescription(obj.get("description").toString());
                //Ajouter la tâche extraite de la réponse Json à la liste
                games.add(g);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return games;
    }

    public ArrayList<Game> getAllGames() {
        String url = Statics.BASE_URL + "/api/games";
        req.setUrl(url);
        req.setPost(false);
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                games = parseGames(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return games;
    }

}