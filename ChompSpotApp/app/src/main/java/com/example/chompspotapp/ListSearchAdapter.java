package com.example.chompspotapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ListSearchAdapter extends Fragment {

    private ArrayList<Business> businesses = new ArrayList<>();
    private ListView listView;
    private BusinessAdapter adapter;

    private static final String ARG_PARAM1 = "param1";
    private String businessFilter;

    private boolean adjustEditText = true;

    public ListSearchAdapter() {
        // Required empty public constructor
    }

    public static ListSearchAdapter newInstance(String param1) {
        ListSearchAdapter fragment = new ListSearchAdapter();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            businessFilter = getArguments().getString(ARG_PARAM1);
        }

        // temporary variables until maps implemented
        businesses.add(new Business("Mel's Diner","American","5 AM-6 PM", "0.6", "busy", "651 N. Ave", "(704) 655-6281", "1", "1"));
        businesses.add(new Business("Taco Shack","Mexican","8 AM-9 PM","2.3","slow", "331 5th St", "(704) 635-5175", "1", "1"));
        businesses.add(new Business("Chx Coop","American","12 PM-10 PM","5.3","moderate", "221 Kentucky Blvd", "(704) 625-1331", "1", "1"));
        businesses.add(new Business("Bob's Burgers","Canadian","6 AM-10 PM","7.3","slow", "9201 Hamster Lane", "(704) 644-4098", "1", "1"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_search_adapter, container, false);
        getActivity().setTitle(getResources().getString(R.string.list_search));

        listView = view.findViewById(R.id.listview);
        adapter = new BusinessAdapter(this.getContext(), R.layout.business_list_layout, businesses);
        listView.setAdapter(adapter);

        EditText searchField = view.findViewById(R.id.searchEdittingText);
        if (adjustEditText) {
            System.out.println(businessFilter);
            searchField.setText(businessFilter);
            adapter.getFilter().filter(businessFilter);
            adjustEditText = false;
        }
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        view.findViewById(R.id.menu_button_ltsrch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adjustEditText = true;
                lvfListener.goToMenu();
            }
        });

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
    }
}