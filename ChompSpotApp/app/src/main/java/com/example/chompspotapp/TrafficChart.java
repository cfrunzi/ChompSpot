package com.example.chompspotapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrafficChart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrafficChart extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private Business business;
    static BarChart chart;
    int[] traffic = new int[24];
    public TrafficChart() {
        // Required empty public constructor
    }


    public static TrafficChart newInstance(Business param1) {
        TrafficChart fragment = new TrafficChart();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            business = getArguments().getParcelable(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_traffic_chart, container, false);
        business = MainActivity.tempBus.copy();
        for(int i = 0; i < 24; i++){
            int index = (int) (MainActivity.dayValue - 1) * 24 + i;

            //TODO make business not null
            if (business != null){
                traffic[i] = business.getTrafficNode(index);
            }
        }

        chart = (BarChart) view.findViewById(R.id.bar_chart);
        ArrayList<BarEntry> values = new ArrayList<>();
        int[] colorCodes = new int[24];
        for(int i = 0; i < 24; i++){
            values.add(new BarEntry((float)i, traffic[i]));
            if (traffic[i] > 66){
                colorCodes[i] = Color.RED;
            } else if (traffic[i] > 33) {
                colorCodes[i] = Color.YELLOW;
            } else {
                colorCodes[i] = Color.GREEN;
            }
        }
        BarDataSet dataSet1 = new BarDataSet(values, "Expected Traffic Today");
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSet1.setColors(colorCodes);
        dataSets.add(dataSet1);


        BarData data = new BarData(dataSets);
        data.setDrawValues(false);
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        
        // Inflate the layout for this fragment]
        return view;

    }
}