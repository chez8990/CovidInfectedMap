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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UpdateInfectedList {
    Context mContext;
    ListView mListView;
    public UpdateInfectedList(Context context, ListView listView){
        mContext = context;
        mListView = listView;
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, future, future);
        queue.add(stringRequest);
        Log.d("Request", "Added");
        try{
            String response = future.get(10, TimeUnit.SECONDS);
//            Log.d("response", response);
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONArray jsonarray = new JSONArray(response);
//                            for (int i = 0; i < jsonarray.length(); i++) {
//                                JSONObject jsonobject = jsonarray.getJSONObject(i);
//                                String buildingName = jsonobject.getString("Building name");
//                                Log.d("Building", buildingName);
//                                buildingArray.add(buildingName);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(mContext, "That didn't work!",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
        return buildingArray;
    }
}