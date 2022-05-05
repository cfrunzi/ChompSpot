package com.example.chompspotapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chompspotapp.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    final String TAG = "demo";
    EditText editTextEmailAddress, editTextPassword;
    FirebaseAuth mAuth;

    FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Login");

        // find each field's ID for use later
        editTextEmailAddress = view.findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = view.findViewById(R.id.editTextTextPassword);

        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = editTextEmailAddress.getText().toString();
                String password = editTextPassword.getText().toString();

                if(emailAddress.isEmpty()){
                    Toast.makeText(getContext(), "Please enter a valid email address.",
                            Toast.LENGTH_SHORT).show();
                    // build alert dialog
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                    builder1.setMessage("Please enter a valid email address").setCancelable(true)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    // create the alert dialog using the builder and show it
                    AlertDialog emailInvalid = builder1.create();
                    emailInvalid.show();
                } else if(password.isEmpty()){
                    // display alert dialog
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                    builder2.setMessage("Please enter a valid password").setCancelable(true)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    AlertDialog passwordInvalid = builder2.create();
                    passwordInvalid.show();
                }else{
                    // check if the credentials match
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                // retrieve the current User Id

                                Log.d(TAG, "onComplete: Logged In Successfully");
                                Log.d(TAG, "onComplete: " + mAuth.getCurrentUser().getUid());

                                mListener.submitLogin(); // go to the Forums fragment
                            }else{
                                Log.d(TAG, "onComplete: Error!!");

                                // display alert dialog indicating reason
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Error: " + task.getException().getMessage())
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                builder.create().show();

                            }
                        }
                    });
                }
            }
        });
        binding.buttonNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.goToSignup();
            }
        });


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (LoginFragmentListener) context;
    }

    LoginFragmentListener mListener;

    interface LoginFragmentListener{
        void submitLogin();
        void goToSignup();
    }
}