
package com.codename1.uikit.services;

import com.codename1.components.ToastBar;
import com.codename1.uikit.entities.Blog;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.FontImage;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.uikit.MyApplication;
import com.codename1.uikit.entities.Blog;
import com.codename1.uikit.entities.User;
import com.codename1.uikit.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.StringTokenizer;

public class ServiceBlog {
     public Boolean resultOK;

    public ArrayList<Blog> BlogList;
    public static ServiceBlog instance;
    private ConnectionRequest req;
    public Blog blog;

    private ServiceBlog() {
        req = new ConnectionRequest();
    }

    public static ServiceBlog getInstance() {
        if (instance == null) {
            instance = new ServiceBlog();
        }
        return instance;
    }

    public ArrayList<Blog> parseBlog(String jsonText) {

        try {
            BlogList = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> blogListJson;

            blogListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) blogListJson.get("root");
                                        System.out.println("parseBlog works");

            for (Map<String, Object> obj : list) {
                            System.out.println("parseBlog works");
                Blog c = new Blog();
                int id = (int) Float.parseFloat(obj.get("id").toString());
                c.setId((int) id);
                //System.out.println(st3.nextToken());
                String title = obj.get("Title").toString();
                String image = obj.get("image").toString();
                String Description = obj.get("description").toString();


                c.setId(id);
                c.setDescription(Description);
                c.setTitle(title);
                c.setImage(image);
                BlogList.add(c);

            }

        } catch (IOException ex) {
            System.out.println(ex);
        }

        return BlogList;

    }

    public ArrayList<Blog> getAllBlog() {
        String url = Statics.BASE_URL + "/api/AllBlog";
        req.removeAllArguments();
        req.setUrl(url);
        req.setPost(false);
        req.setHttpMethod("GET");
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent ev) {
                BlogList = parseBlog(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        }
        );
        NetworkManager.getInstance().addToQueueAndWait(req);
        return BlogList;
    }

    public void deleteBlog(int id) {
        String url = Statics.BASE_URL + "/api/deleteBlog/" + id;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
        // System.out.println("category deleted");

    }

    public Boolean addBlog(Blog c) {

        String url = Statics.BASE_URL + "/api/createBlog?Title=" + c.getTitle() + "&description=" + c.getDescription()
                 ;
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

    public Boolean EditBlog(Blog c) {
        System.out.println("this :"+c);
        String url = Statics.BASE_URL + "/api/updateBlog/" + c.getId() + "?Title=" + c.getTitle() + "&description=" + c.getDescription()
                ;
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