package com.codename1.uikit.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Game;
import com.codename1.uikit.entities.User;
import com.codename1.uikit.utils.Statics;
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
    
    public Game checkLike(String jsonText) {
        try {
            game = new Game();
            JSONParser j = new JSONParser();

            Map<String, Object> gameJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            float id = Float.parseFloat(gameJson.get("id").toString());
            game.setId((int) id);
            game.setName(gameJson.get("name").toString());
            game.setImage(gameJson.get("image").toString());
            game.setDescription(gameJson.get("description").toString());
            game.setIsLiked(Boolean.parseBoolean(gameJson.get("isLiked").toString()));
            return game;
        } catch (IOException ex) {
            System.out.println(ex);
            return new Game();
        }
    }

    public ArrayList<Game> getAllGames() {
        String url = Statics.BASE_URL + "/api/games";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
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
    
    public Game getGame(String username, String gameName) {
        String url = Statics.BASE_URL + "/api/game";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
        req.addArgument("username", username);
        req.addArgument("gameName", gameName);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                if (req.getResponseCode() == 200) {
                    game = checkLike(new String(req.getResponseData()));
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return game;
    }
    
    public void likeGame(String username, String gameName) {
        String url = Statics.BASE_URL + "/api/game/like";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("username", username);
        req.addArgument("gameName", gameName);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    public void unlikeGame(String username, String gameName) {
        String url = Statics.BASE_URL + "/api/game/unlike";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(true);
        req.addArgument("username", username);
        req.addArgument("gameName", gameName);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public void deleteGame(String name) {
        String url = Statics.BASE_URL + "/api/game/delete";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("DELETE");
        req.addArgument("gameName", name);
        NetworkManager.getInstance().addToQueueAndWait(req);
    }

}