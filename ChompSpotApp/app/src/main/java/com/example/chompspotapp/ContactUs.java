package com.example.chompspotapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactUs extends Fragment {

    public ContactUs() {
        // Required empty public constructor
    }

    public static ContactUs newInstance(String param1, String param2) {
        ContactUs fragment = new ContactUs();
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
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        getActivity().setTitle("");
        view.findViewById(R.id.menu_button_cntc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cfListener.goToMenu();
            }
        });

        view.findViewById(R.id.submit_button_ctct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.checkBoxIssue).setVisibility(View.GONE);
                view.findViewById(R.id.checkBoxSugg).setVisibility(View.GONE);
                view.findViewById(R.id.titleTextView).setVisibility(View.GONE);
                view.findViewById(R.id.titleEditText).setVisibility(View.GONE);
                view.findViewById(R.id.descriptionTextView).setVisibility(View.GONE);
                view.findViewById(R.id.descriptionEditText).setVisibility(View.GONE);
                view.findViewById(R.id.submit_button_ctct).setVisibility(View.GONE);
                view.findViewById(R.id.feedbackTextView).setVisibility(View.VISIBLE);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof contactFragmentListener)
            cfListener = (contactFragmentListener) context;
        else
            throw new RuntimeException(context.toString() + " must implement contactFragmentListener.");
    }

    contactFragmentListener cfListener;

    interface contactFragmentListener {
        void goToMenu();
    }
}