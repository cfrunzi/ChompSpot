package com.example.chompspotapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 *
 */
public class MapView extends Fragment {

    ImageButton searchButton;
    EditText searchText;

    public MapView() {
        // Required empty public constructor
    }

    public static MapView newInstance(String param1, String param2) {
        MapView fragment = new MapView();
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
        View view = inflater.inflate(R.layout.fragment_map_view, container, false);
        getActivity().setTitle(getResources().getString(R.string.map_view));

        searchButton = view.findViewById(R.id.search_button);
        searchText = view.findViewById(R.id.editTextSearch);


        view.findViewById(R.id.menu_button_adb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvfListener.goToMenu();
            }
        });

        view.findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mvfListener.goToListSearch(searchText.getText().toString());
            }
        });

//
//        view.findViewById(R.id.busyMarker).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mvfListener.goToTemp();
//            }
//        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof mapViewFragmentListener)
            mvfListener = (mapViewFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement mapViewFragmentListener.");
    }

    mapViewFragmentListener mvfListener;

    interface mapViewFragmentListener {
        void goToMenu();
        void goToListSearch(String s);
    }
}