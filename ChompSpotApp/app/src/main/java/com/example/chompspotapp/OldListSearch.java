package com.example.chompspotapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OldListSearch extends Fragment {

    public OldListSearch() {
        // Required empty public constructor
    }

//    public static OldListSearch newInstance(String param1, String param2) {
//        OldListSearch fragment = new OldListSearch();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
//        getActivity().setTitle("");
//        view.findViewById(R.id.menu_button_lsts).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                lvfListener.goToMenu();
//            }
//        });
//        view.findViewById(R.id.mel_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                lvfListener.goToTemp();
//            }
//        });
//        // Inflate the layout for this fragment
//        return view;
//    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof listViewFragmentListener)
//            lvfListener = (listViewFragmentListener) context;
//        else
//            throw new RuntimeException(context.toString() + " must implement listViewFragmentListener.");
//    }
//
//    listViewFragmentListener lvfListener;
//
//    interface listViewFragmentListener {
//        void goToMenu();
//        void goToTemp();
//    }
}