package com.example.chompspotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MapView.mapViewFragmentListener,
    Menu.menuFragmentListener, AboutUs.aboutFragmentListener, ContactUs.contactFragmentListener,
    ListSearchAdapter.listViewFragmentListener, AddBusiness.businessFragmentListener,
        MapOverlay.TempFragmentListener,
    BusinessAdapter.businessAdapterListener {

    public static Business[] cache= new Business[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        cache[0] = new Business("Mel's Diner","American","5 AM-6 PM", "0.6", "busy", "651 N. Ave", "(704) 655-6281", 1, 2);
        cache[1] = new Business("Taco Shack","Mexican","8 AM-9 PM","2.3","slow", "331 5th St", "(704) 635-5175", 3, 3);
        cache[2] = new Business("Chx Coop","American","12 PM-10 PM","5.3","moderate", "221 Kentucky Blvd", "(704) 625-1331", 5, 4);
        cache[3] = new Business("Bob's Burgers","Canadian","6 AM-10 PM","7.3","slow", "9201 Hamster Lane", "(704) 644-4098", 7, 5);

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
}