package com.example.covidinfrectedmap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MapFragment extends Fragment implements
        OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mMapFrag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);

        mMapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mMapFrag.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.d("onmapready", "was called");
        mMap = map;
        if (ActivityCompat.checkSelfPermission(getActivity(),
    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }
        map.addMarker(new MarkerOptions().position(new LatLng(0, 0)));
        map.setMyLocationEnabled(true);
}

    public List<LatLng> getLocationsLatlng(List<String> locations){
        String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?" +
                "input=%s" +
                "&inputtype=textquery" +
                "&fields=formatted_address,name,geometry" +
                "&key=" + getString(R.string.maps_api_key);

        final List<LatLng> locationLatlng = new ArrayList<LatLng>();

        // Request a string response from the provided URL.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        for (int i=0; i<2; i++) {
            String location = locations.get(i);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, String.format(url, location),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonobject = new JSONObject(response).getJSONObject("geometry").getJSONObject("location");

                                float lat = Float.parseFloat(jsonobject.getString("lat"));
                                float lng = Float.parseFloat(jsonobject.getString("lng"));
                                LatLng latlng = new LatLng(lat, lng);
                                locationLatlng.add(latlng);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(getActivity(), "That didn't work!",
//                            Toast.LENGTH_LONG).show();
                }
            }
            );
            queue.add(stringRequest);
        }
        return locationLatlng;
    }

    public void markLocations(List<String> locations){
        List<LatLng> locationsLatlng = getLocationsLatlng(locations);
        for (int i=0; i<2; i++){
            LatLng latlng = locationsLatlng.get(i);
            mMap.addMarker(new MarkerOptions().position(latlng));
        }
    }
}