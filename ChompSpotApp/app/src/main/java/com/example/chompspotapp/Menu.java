package com.example.chompspotapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Menu extends Fragment {

    public Menu() {
        // Required empty public constructor
    }

    public static Menu newInstance(String param1, String param2) {
        Menu fragment = new Menu();
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
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        getActivity().setTitle(getResources().getString(R.string.menu));

        view.findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mfListener.goToMapView();
            }
        });

//        view.findViewById(R.id.map_search_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mfListener.goToMapSearch();
//            }
//        });

        view.findViewById(R.id.list_search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfListener.goToListSearch("");
            }
        });

        view.findViewById(R.id.about_us_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfListener.goToAboutUs();
            }
        });

        view.findViewById(R.id.contact_us_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfListener.goToContact();
            }
        });

        view.findViewById(R.id.add_business_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfListener.goToBusiness();
            }
        });

        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof menuFragmentListener)
            mfListener = (menuFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement menuFragmentListener.");
    }

    menuFragmentListener mfListener;

    interface menuFragmentListener {
        void goToMapView();
        void goToListSearch(String s);
        void goToAboutUs();
        void goToContact();
        void goToBusiness();
        void logout();
    }
}