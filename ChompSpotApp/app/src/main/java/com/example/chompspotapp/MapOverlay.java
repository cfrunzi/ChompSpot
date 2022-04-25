package com.example.chompspotapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MapOverlay extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private Business business;

    public MapOverlay() {
        // Required empty public constructor
    }

    public static MapOverlay newInstance(Business param1) {
        MapOverlay fragment = new MapOverlay();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            business = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_overlay, container, false);
        view.findViewById(R.id.back_button_tmp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tfListener.returnToMapView();
            }
        });

        TextView name = view.findViewById(R.id.nameOverlay);
        TextView address = view.findViewById(R.id.addressOverlay);
        TextView phone = view.findViewById(R.id.phoneOverlay);
        TextView hours = view.findViewById(R.id.hoursOverlay);
        TextView busy = view.findViewById(R.id.busyOverlay);
        TextView busyImg = view.findViewById(R.id.busyImageOverlay);

        name.setText(business.getName());
        address.setText(business.getAddress());
        phone.setText(business.getPhone());
        hours.setText(business.getHours());
        busy.setText(business.getBusy().toUpperCase());

        if (business.getBusy().equals("busy"))
            busyImg.setTextColor(Color.parseColor("#D12B1F"));
        else if (business.getBusy().equals("moderate"))
            busyImg.setTextColor(Color.parseColor("#E8D633"));
        else
            busyImg.setTextColor(Color.parseColor("#3CBD41"));

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
        void returnToMapView();
    }
}