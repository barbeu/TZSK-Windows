package com.example.tzadmin.tzsk_windows.JsonModule;

import com.example.tzadmin.tzsk_windows.AuthModule.Auth;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseModels.Meas;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by tzadmin on 15.04.17.
 */

public class JSON {

    public static ArrayList<Meas> parse (String json) {
        ArrayList<Meas> meases = new ArrayList<>();
        try {
            JSONObject dataJsonObj = new JSONObject(json);
            JSONArray orders = dataJsonObj.getJSONArray("arrayOfOrders");
            for (int i=0; i< orders.length(); i++) {
                JSONObject order = orders.getJSONObject(i);
                Meas meas = new Meas();
                meas.idUser = Auth.id;
                meas.OrderID = order.getString("OrderID");
                meas.OrderNumber = order.getString("OrderNumber");
                meas.Client = order.getString("Client");
                meas.PhoneNumber = order.getString("PhoneNumber");
                meas.Date = order.getString("Date");
                meas.Address = order.getString("Address");
                meas.TimeStart = order.getString("TimeStart");
                meas.EndTime = order.getString("EndTime");
                meas.Comment = order.getString("Comment");
                meases.add(meas);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return meases;
    }
}
