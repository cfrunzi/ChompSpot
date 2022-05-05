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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrafficChart.
     */

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

        for(int i = 0; i < 24; i++)+{
            int index = (int) (MainActivity.dayValue - 1) * 24 + i;

            //TODO make business not null
            if (business != null){
                traffic[i] = business.getTrafficNode(index);
            }
        }
        
        chart = (BarChart) getActivity().findViewById(R.id.bar_chart);
        ArrayList<BarEntry> values = new ArrayList<>();
        for(int i = 0; i < 24; i++){
            values.add(new BarEntry(0, traffic[i]));
        }
        BarDataSet dataSet1 = new BarDataSet(values, "Expected Traffic Today");
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet1);
        
        BarData data = new BarData(dataSets);
        

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MapOverlay.TempFragmentListener)
            tfListener = (MapOverlay.TempFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement TempFragmentListener.");
    }

    MapOverlay.TempFragmentListener tfListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        // Inflate the layout for this fragment]
        return inflater.inflate(R.layout.fragment_traffic_chart, container, false);

    }
}