package com.example.chompspotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements MapView.mapViewFragmentListener,
    Menu.menuFragmentListener, AboutUs.aboutFragmentListener, ContactUs.contactFragmentListener,
    ListSearchAdapter.listViewFragmentListener, AddBusiness.businessFragmentListener,
        MapOverlay.TempFragmentListener,
    BusinessAdapter.businessAdapterListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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