/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.codename1.uikit.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.codename1.uikit.entities.Cart;
import com.codename1.uikit.entities.Categories;
import com.codename1.uikit.entities.Order;
import com.codename1.uikit.utils.Statics;
import java.util.ArrayList;

/**
 *
 * @author meriam
 */
public class ServiceOrder {

    public Boolean resultOK;
    private ConnectionRequest req;
    public static ServiceOrder instance;

    private ServiceOrder() {
        req = new ConnectionRequest();
    }

    public static ServiceOrder getInstance() {
        if (instance == null) {
            instance = new ServiceOrder();
        }
        return instance;
    }

         public Boolean updateOrder(Order o) {
         String url = Statics.BASE_URL + "/api/updateOrder/" + o.getId() + "?userId=" + o.getUserId() + "&totalprice=" + o.getTotalprice();
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOK;
    }
}
