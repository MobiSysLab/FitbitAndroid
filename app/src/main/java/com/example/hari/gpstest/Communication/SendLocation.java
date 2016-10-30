package com.example.hari.gpstest.Communication;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.*;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 2016-10-17.
 */

public class SendLocation {
    double longititude;
    double latitude;
    SharedPreferences fitbitPreference;
    JSONObject jsonObj;

    final String GPS_URL = "http://10.0.2.2:8080/gps";

    public void sendToServer(Context context, Location location) {
        longititude = location.getLongitude();
        latitude = location.getLatitude();

        fitbitPreference = context.getSharedPreferences("fitbitPreference", MODE_PRIVATE);

        Coordinate currentPosition = new Coordinate(latitude,longititude,System.currentTimeMillis() / 1000);
        UserObject userObject = new UserObject(fitbitPreference.getString("userKey", ""), currentPosition);

        Gson gson = new Gson();
        String json = gson.toJson(userObject);
        final Map<String, String> httpHeader = new HashMap<>();
        httpHeader.put("Content-Type", "application/json");

        try {
            jsonObj = new JSONObject(json);
        } catch (JSONException e){
            Log.d("JSONEXCEPTION", e.toString());
        }

        Log.d("TAG", json);

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = GPS_URL;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
            }
        }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ERROR", error.toString());
                }
        }) {
            @Override
            public Map getHeaders() {
                return httpHeader;
            }
        };
        queue.add(jsObjRequest);
    }
}

class UserObject {
    String key;
    Coordinate data;

    public UserObject(String key, Coordinate coordinate) {
        this.key = key;
        this.data = coordinate;
    }
}

class Coordinate {
    double latitude;
    double longitude;
    long timestamp;

    public Coordinate(double latitude, double longititude, long timestamp) {
        this.latitude = latitude;
        this.longitude = longititude;
        this.timestamp = timestamp;
    }
}