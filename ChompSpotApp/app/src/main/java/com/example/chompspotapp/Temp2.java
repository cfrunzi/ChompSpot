package com.example.chompspotapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Temp2 extends Fragment {

    public Temp2() {
        // Required empty public constructor
    }

    public static ListSearch newInstance(String param1, String param2) {
        ListSearch fragment = new ListSearch();
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
        View view = inflater.inflate(R.layout.fragment_temp2, container, false);
        getActivity().setTitle("");
        view.findViewById(R.id.menu_button_lsts).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvfListener.goToMenu();
            }
        });
        view.findViewById(R.id.mel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvfListener.goToTemp();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof listViewFragmentListener)
            lvfListener = (listViewFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement listViewFragmentListener.");
    }

    listViewFragmentListener lvfListener;

    interface listViewFragmentListener {
        void goToMenu();
        void goToTemp();
    }
}