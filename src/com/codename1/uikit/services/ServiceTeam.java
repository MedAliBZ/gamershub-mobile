package com.codename1.uikit.services;

import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import com.codename1.uikit.entities.Teams;
import com.codename1.uikit.MyApplication;

import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fadhel
 */
public class ServiceTeam {

    public Boolean resultOK;

    public ArrayList<Teams> TeamsList;
    public static ServiceTeam instance;
    private ConnectionRequest req;
    public Teams Teams;

    private ServiceTeam() {
        req = new ConnectionRequest();
    }

    public static ServiceTeam getInstance() {
        if (instance == null) {
            instance = new ServiceTeam();
        }
        return instance;
    }

 

    public ArrayList<Teams> parseTeams(String jsonText) {

        try {
            TeamsList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> TeamsListJson;

            TeamsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) TeamsListJson.get("root");
            for (Map<String, Object> obj : list) {
                Teams c = new Teams();
                int id = (int) Float.parseFloat(obj.get("id").toString());
                String TeamName = obj.get("Team_name").toString();
                int rank = (int) Float.parseFloat(obj.get("rank").toString());
//                String image = obj.get("image").toString();
              
              /* int GamersNb = (int) Float.parseFloat(obj.get("GamersNb").toString());
               int Rank = (int) Float.parseFloat(obj.get("Rank").toString());*/

                c.setId(id);
            
                c.setTeamName(TeamName);
                c.setRank(rank);
               // c.setImage("image");
                //c.setImage(Image);
                
                

              // System.out.println("i'm here "+c);
                /*c.setImage(obj.get("image").toString());
                c.setGamersNb(GamersNb);
                c.setRank(Rank);*/
                TeamsList.add(c);

            }

        } catch (IOException ex) {

        }

        return TeamsList;

    }

    public ArrayList<Teams> getAllTeams() {
        String url = Statics.BASE_URL + "/api/teams/allteams";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent ev) {
                TeamsList = parseTeams(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }

           
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return TeamsList;
    }

    public Boolean addTeam(Teams c) {

        String url = Statics.BASE_URL + "/api/teams/addteam?TeamName=" + c.getTeamName() + "&gamersNb=" + c.getGamersNb() + "&rank=" + c.getRank()+"&image="+c.getImage()+"&verified="+1;
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        ToastBar.showMessage(" Team is added", FontImage.MATERIAL_ACCESS_TIME);

        return resultOK;

    }



    public void deleteTeam(int id) {
        String url = Statics.BASE_URL + "/api/teams/delete?id=" + id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        // System.out.println("category deleted");

    }

    public Boolean updateTeam(Teams c) {
        String url = Statics.BASE_URL + "/api/teams/updateteam/{id}" + c.getId() + "?TeamName=" + c.getTeamName() + "&GamersNb=" + c.getGamersNb() + "&rank=" + c.getRank()+"&image="+c.getImage();
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