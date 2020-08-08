package com.example.covidinfrectedmap;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class UpdateInfectedList {
    Context mContext;
    ListView mListView;
    public UpdateInfectedList(Context context, ListView listView){
        mContext = context;
        mListView = listView;
    }

    public void storeData(HashSet<String> buildingNames, String fileName){
        try {
            FileOutputStream fos = mContext.openFileOutput(fileName, mContext.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(buildingNames);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void readData(String fileName) {
        try {
            FileInputStream fis = mContext.openFileInput(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            HashSet<String> buildingNames = (HashSet<String>) ois.readObject();
            return buildingNames
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Object does not exist");
        }
    }

    public List<String> TransformResults(List<String> results){

    }

    public HashSet<String> makeRequest(){
        // Instantiate the RequestQueue.
        RequestFuture<String> future = RequestFuture.newFuture();
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://api.data.gov.hk/v2/filter?q=%7B%22resource%22%3A%22http%3A%2F%2F" +
                "www.chp.gov.hk%2Ffiles%2Fmisc%2Fbuilding_list_eng.csv%22%2C%22section%22%3A1%2" +
                "C%22format%22%3A%22json%22%2C%22sorts%22%3A%5B%5B2%2C%22desc%22%5D%5D%7D";

//        final List<String> buildingArray = new ArrayList<String>();
        final HashSet<String> buildingArray = new HashSet<String>();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonobject = jsonarray.getJSONObject(i);
                                String buildingName = jsonobject.getString("Building name");
                                Log.d("Building", buildingName);
                                buildingArray.add(buildingName);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "That didn't work!",
                        Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return buildingArray;
    }
}