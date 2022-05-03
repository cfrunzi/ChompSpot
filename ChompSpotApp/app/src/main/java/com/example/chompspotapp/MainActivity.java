package com.example.chompspotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements MapView.mapViewFragmentListener,
    Menu.menuFragmentListener, AboutUs.aboutFragmentListener, ContactUs.contactFragmentListener,
    ListSearchAdapter.listViewFragmentListener, AddBusiness.businessFragmentListener,
        MapOverlay.TempFragmentListener, MapsFragment.mapFragListener,
    BusinessAdapter.businessAdapterListener {

    public static Business[] cache= new Business[20];
    public static int entries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            JSONObject fileObject= new JSONObject(loadJsonFromAsset());
            JSONArray arrayOfPlaces = fileObject.getJSONArray("restaurants");

            int counter = 0;
            //process array
            for(int i = 0; i < arrayOfPlaces.length(); i++){

                JSONObject placeJsonObject = (JSONObject) arrayOfPlaces.get(i);
                JSONArray timeArray = placeJsonObject.getJSONArray("week_raw");

                //extract info
                String name = placeJsonObject.optString("venue_name");
                String type = placeJsonObject.optString("venue_type");
                String address = placeJsonObject.optString("venue_address");
                String hours = placeJsonObject.optString("hours");
                Double placeLong  = placeJsonObject.optDouble("venue_lng");
                Double placeLat  = placeJsonObject.optDouble("venue_lat");
                String phone = placeJsonObject.optString("phone_number");
                String log = name + " " + address + " " + placeLat + " " + placeLong;

                //TODO implement current location
                //String distance = distance(placeLat, placeLong, currLat, currLong, "M");
                //int[] traffic = new int[168];



                cache[i] = new Business(name,type, hours, "0.6", "busy", address, phone, placeLat, placeLong);
                //new Business("Sovi Marketplace","American","11 AM-8 PM", "0.6", "busy", "8917 Johnson Alumni Way", "(704) 687-8119", 35.30288798821443, -80.73500740370314);
                entries = i;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentView,
                new MapView()).commit();
    }

    @Override
    public void goToMenu() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new Menu()).commit();
    }    @Override
    public void goToMapView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new MapView()).commit();
    }

    @Override
    public void goToListSearch(String s) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                ListSearchAdapter.newInstance(s)).commit();
    }

    @Override
    public void goToAboutUs() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new AboutUs()).commit();
    }

    @Override
    public void goToContact() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new ContactUs()).commit();
    }

    @Override
    public void goToBusiness() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new AddBusiness()).commit();
    }

    @Override
    public void goToMap(Business bus) {
        System.out.println(bus);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new MapView()).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentView,
                MapOverlay.newInstance(bus)).addToBackStack(null).commit();
    }

    @Override
    public void returnToMapView() {
        getSupportFragmentManager().popBackStack();
    }

    //Sourced from https://www.geodatasource.com/developers/java to calculate distance between
    // current location and place location
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit.equals("K")) {
                dist = dist * 1.609344;
            } else if (unit.equals("N")) {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }

    private String loadJsonFromAsset(){
        String json = null;
        try{
            InputStream inputStream = getAssets().open("Business.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}