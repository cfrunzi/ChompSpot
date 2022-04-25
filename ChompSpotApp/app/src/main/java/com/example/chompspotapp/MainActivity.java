package com.example.chompspotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements MapView.mapViewFragmentListener,
    Menu.menuFragmentListener, AboutUs.aboutFragmentListener, ContactUs.contactFragmentListener,
    ListSearch.listViewFragmentListener, AddBusiness.businessFragmentListener,
        Temp.TempFragmentListener, Temp2.listViewFragmentListener, LoginFragment.LoginFragmentListener {

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
    }

    @Override
    public void goToTemp() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new Temp()).commit();
    }

    @Override
    public void goToTemp2() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new Temp2()).commit();
    }

    @Override
    public void goToMapView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new MapView()).commit();
    }

    @Override
    public void goToMapSearch() {
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
        //        new MapSearch()).commit();
    }

    @Override
    public void goToListSearch() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new ListSearch()).commit();
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
    public void submitLogin() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new Menu()).commit();
    }

    @Override
    public void goToSignup() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView,
                new RegisterFragment()).commit();
    }
}