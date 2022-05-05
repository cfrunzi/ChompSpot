package com.example.chompspotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainActivity extends AppCompatActivity implements MapView.mapViewFragmentListener,
    Menu.menuFragmentListener, AboutUs.aboutFragmentListener, ContactUs.contactFragmentListener,
    ListSearchAdapter.listViewFragmentListener, AddBusiness.businessFragmentListener,
        MapOverlay.TempFragmentListener, MapsFragment.mapFragListener,
    BusinessAdapter.businessAdapterListener {

    public static Business[] cache= new Business[20];
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance(); // get current instance of the authenticator

        // depending on whether the user is logged in or not (credentials match),
        // display a different fragment so that the user doesn't have to login every time

        // if there is no user logged in, pull up login fragment
        if(mAuth.getCurrentUser() == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentView, new LoginFragment()).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentView, new Menu()).commit();
        }
        //TODO Pull from json to fill out cache
        /*for(int i = 0; i < 1; i++){
            String name = "Mel's Diner";
            String type = "American";
            String hours = "9 to 10pm";
            String distance = "2 mi";
            String busy = "no";
            String address = "554 lex ave";
            String phone= "985-988-8966";
            double latitude = 47.5858;
            double longitude = -54.5641;

            cache[i] = new Business(name, type, hours, distance, busy, address, phone, latitude, longitude);
        }*/
        //TODO Delete after json implementation
        cache[0] = new Business("Sovi Marketplace","American","11 AM-8 PM", "0.6", "busy", "8917 Johnson Alumni Way", "(704) 687-8119", 35.30288798821443, -80.73500740370314);
        cache[1] = new Business("Peets Coffee and Tea","American","7:30AM–11PM","2.3","slow", "410 Library Ln", "(704) 687-1185", 35.30558342421959, -80.7321250016825);
        cache[2] = new Business("Dumpling Girls","American","11AM–8:30PM","5.3","moderate", "9330 Sandburg Ave", "(704) 421-4409", 35.300826960789486, -80.7283396434796);
        cache[3] = new Business("Subway","American","\t10:30AM–3PM","4.3","slow", "9025 University Rd", "(704) 687-0688", 35.3053975247239, -80.73344656941214);


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
    public void logout() {

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
}