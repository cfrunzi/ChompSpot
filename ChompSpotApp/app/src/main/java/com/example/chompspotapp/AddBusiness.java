package com.example.chompspotapp;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddBusiness#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddBusiness extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public AddBusiness() {
        // Required empty public constructor
    }

    public static AddBusiness newInstance(String param1, String param2) {
        AddBusiness fragment = new AddBusiness();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View view = inflater.inflate(R.layout.fragment_add_business, container, false);
        getActivity().setTitle("");
        view.findViewById(R.id.menu_button_adb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bfListener.goToMenu();
            }
        });
        view.findViewById(R.id.submit_button_adb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.bnameTextView).setVisibility(View.GONE);
                view.findViewById(R.id.bNameEditText).setVisibility(View.GONE);
                view.findViewById(R.id.zipCodeEditText).setVisibility(View.GONE);
                view.findViewById(R.id.zipCodeTextView).setVisibility(View.GONE);
                view.findViewById(R.id.submit_button_adb).setVisibility(View.GONE);
                view.findViewById(R.id.busAdded).setVisibility(View.VISIBLE);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof businessFragmentListener)
            bfListener = (businessFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement businessFragmentListener.");
    }

    businessFragmentListener bfListener;

    interface businessFragmentListener {
        void goToMenu();
    }
}