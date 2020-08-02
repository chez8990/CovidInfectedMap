package com.example.covidinfrectedmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(new HandleClick());

        // Remove title bar and notification bar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        String[] mobileArray = {"Android", "IPhone", "WindowsMobile", "Blackberry",
                "WebOS", "Ubuntu", "Windows7", "Max OS X"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, mobileArray);

        ListView listView = findViewById(R.id.infected_list);
        listView.setAdapter(adapter);
    }

    private class HandleClick implements View.OnClickListener {
        public void onClick(View arg0) {
            Button btn = (Button) arg0;  //cast view to a button
            final Context context = getApplicationContext();

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://api.data.gov.hk/v2/filter?q=%7B%22resource%22%3A%22http%3A%2F%2F" +
                    "www.chp.gov.hk%2Ffiles%2Fmisc%2Fbuilding_list_eng.csv%22%2C%22section%22%3A1%2" +
                    "C%22format%22%3A%22json%22%2C%22sorts%22%3A%5B%5B2%2C%22desc%22%5D%5D%7D";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            String[] buildingArray = new String [0];
                            List<String> buildingArray = new ArrayList<String>();
                            try {
                                JSONArray jsonarray = new JSONArray(response);

                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                                    String buildingName = jsonobject.getString("Building name");
                                    buildingArray.add(buildingName);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            Log.d("Buildings", buildingArray.toString());
                            ArrayAdapter adapter = new ArrayAdapter<String>(context,
                                    R.layout.activity_listview, buildingArray);
                            ListView listView = findViewById(R.id.infected_list);
                            listView.setAdapter(adapter);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "That didn't work!",
                            Toast.LENGTH_LONG).show();
//                    textView.setText("That didn't work!");
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }
}