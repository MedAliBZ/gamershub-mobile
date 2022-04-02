package com.codename1.uikit.services;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.uikit.entities.Matchs;

import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author meriam
 */
public class ServiceMatch {

    public Boolean resultOK;

    public ArrayList<Matchs> MatchsList;
    public static ServiceMatch instance;
    private ConnectionRequest req;
    public Matchs Matchs;

    private ServiceMatch() {
        req = new ConnectionRequest();
    }

    public static ServiceMatch getInstance() {
        if (instance == null) {
            instance = new ServiceMatch();
        }
        return instance;
    }

    public ArrayList<Matchs> parseMatchs(String jsonText) {

        try {
            MatchsList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> MatchsListJson;

            MatchsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) MatchsListJson.get("root");
            for (Map<String, Object> obj : list) {

                Matchs c = new Matchs();
                int id = (int) Float.parseFloat(obj.get("id").toString());
                String MatchName = obj.get("MatchName").toString();

//                String Date = obj.get("match_date").toString();
                String Result = obj.get("result").toString();

                c.setId(id);
                c.setMatchName(MatchName);
              //  c.setDate(Date);
                c.setResult(Result);
                System.out.println("heeeere" + c);
                MatchsList.add(c);

            }

        } catch (IOException ex) {

        }

        return MatchsList;

    }

    public ArrayList<Matchs> getAllMatchs() {
        String url = Statics.BASE_URL + "/api/matchs/allmatchs";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent ev) {
                MatchsList = parseMatchs(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }

        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return MatchsList;
    }

    public Boolean addMatch(Matchs c) {

        String url = Statics.BASE_URL + "/api/matchs/addmatch?MatchName=" + c.getMatchName() + "&result=" + c.getResult();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" Match is added", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }

    public void deleteMatch(int id) {
        String url = Statics.BASE_URL + "/api/matchs/match/delete?id=" + id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        // System.out.println("category deleted");

    }

    public Boolean updateMatch(Matchs c) {
        String url = Statics.BASE_URL + "/api/matchs/updatematch/" + c.getId() + "?MatchName=" + c.getMatchName() + "&result=" + c.getResult();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOK;
    }

}
