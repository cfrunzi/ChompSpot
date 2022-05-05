package com.example.chompspotapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MainActivity extends AppCompatActivity implements MapView.mapViewFragmentListener,
    Menu.menuFragmentListener, AboutUs.aboutFragmentListener, ContactUs.contactFragmentListener,
    ListSearchAdapter.listViewFragmentListener, AddBusiness.businessFragmentListener,
        MapOverlay.TempFragmentListener, MapsFragment.mapFragListener,
    BusinessAdapter.businessAdapterListener, LoginFragment.LoginFragmentListener, RegisterFragment.SignupFragmentListener {

    public static Business[] cache= new Business[20];
    public static Business tempBus;
    public static int entries;
    public static int dayValue;

    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance(); // get current instance of the authenticator

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
                double placeLong  = placeJsonObject.optDouble("venue_lng");
                double placeLat  = placeJsonObject.optDouble("venue_lat");
                String phone = placeJsonObject.optString("phone_number");
                String log = name + " " + address + " " + placeLat + " " + placeLong;

                //TODO implement current location
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Location location = new Location(LocationManager.GPS_PROVIDER);
                double currentLat = location.getLatitude();
                double currentLong = location.getLongitude();
                double distanceUnits = distance(placeLat, placeLong, currentLat, currentLong, "M");
                //rounding
                distanceUnits = Math.round(distanceUnits*10)/10;
                String distance = String.valueOf(distanceUnits);

                int[] traffic = new int[168];
                for(int j = 0; j < timeArray.length(); j++){
                    traffic[j]= (int) timeArray.opt(j);
                }
                int currHour =LocalDateTime.now().getHour();
                int currMinute = LocalDateTime.now().getMinute();
                if(currMinute > 45) {
                    currHour++;
                }
                int currHourIndex;
                LocalDate today = LocalDate.now();
                //1 Monday - 7 Sunday
                DayOfWeek day = today.getDayOfWeek();
                dayValue = day.getValue();
                if(dayValue == 1 & currHour < 6) {
                    currHourIndex = traffic[(167 - (5 - currHour))];
                }else {
                    currHourIndex = traffic[((dayValue-1)*24)+(currHour-6)];
                }

                String busyLevel;
                if (currHourIndex < 35){
                    busyLevel = "Slow";
                } else if (35 < currHourIndex && currHourIndex < 70){
                    busyLevel = "Moderate";
                } else{
                    busyLevel = "Busy";

                }


                cache[i] = new Business(name,type, hours, distance, busyLevel, address, phone, placeLat, placeLong, traffic);
                entries = i;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // depending on whether the user is logged in or not (credentials match),
        // display a different fragment so that the user doesn't have to login every time

        // if there is no user logged in, pull up login fragment
        if(mAuth.getCurrentUser() == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentView, new LoginFragment()).commit();
        }
        else{

            // otherwise we pull up the forums fragment
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentView, new Menu(),
                            "menu").commit();
        }
        //getSupportFragmentManager().beginTransaction().add(R.id.fragmentView,
                //new MapView()).commit();
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
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentView, new LoginFragment()).commit();
    }

    @Override
    public void goToMap(Business bus) {
        tempBus = bus.copy();
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

    @Override
    public void submitLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentView, new Menu()).commit();
    }

    @Override
    public void goToSignup() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentView, new RegisterFragment()).commit();
    }

    @Override
    public void registerInfo() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentView, new Menu()).commit();
    }

    @Override
    public void returnToLogin() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentView, new LoginFragment()).commit();
    }
}