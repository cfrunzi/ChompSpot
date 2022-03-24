package com.example.chompspotapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Temp extends Fragment {

    public Temp() {
        // Required empty public constructor
    }

    public static Temp newInstance(String param1, String param2) {
        Temp fragment = new Temp();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temp, container, false);
        view.findViewById(R.id.back_button_tmp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tfListener.goToMapView();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TempFragmentListener)
            tfListener = (TempFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement TempFragmentListener.");
    }

    TempFragmentListener tfListener;

    interface TempFragmentListener {
        void goToMapView();
    }
}