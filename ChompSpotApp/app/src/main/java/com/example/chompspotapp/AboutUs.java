package com.example.chompspotapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutUs extends Fragment {

    public AboutUs() {
        // Required empty public constructor
    }

    public static AboutUs newInstance(String param1, String param2) {
        AboutUs fragment = new AboutUs();
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
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        getActivity().setTitle("");
        view.findViewById(R.id.menu_button_abtus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afListener.goToMenu();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof aboutFragmentListener)
            afListener = (aboutFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement mapViewFragmentListener.");
    }

    aboutFragmentListener afListener;

    interface aboutFragmentListener {
        void goToMenu();
    }
}