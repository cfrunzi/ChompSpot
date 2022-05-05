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

import com.example.chompspotapp.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    FirebaseAuth mAuth;
    final String TAG = "demo";
    FragmentRegisterBinding binding;

    public RegisterFragment() {
        // Required empty public constructor
    }


    EditText editTextName, editTextEmail, editTextPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create New Account");

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.returnToLogin();
            }
        });

        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // convert the information into strings
                String name = binding.editTextPersonName.getText().toString();
                String email = binding.editEmailAddress.getText().toString();
                String password = binding.editTextPassword.getText().toString();


                // check the current thread
                Log.d(TAG, "onComplete: " + Thread.currentThread().getId());

                // introduce validation checks
                if (name.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Please enter a name").setCancelable(true)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog nameBlank = builder.create();
                    nameBlank.show();
                } else if (email.isEmpty()) {
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

                } else if (password.isEmpty()) {
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
                } else {
                    mAuth = FirebaseAuth.getInstance();

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "onComplete: " + Thread.currentThread().getId());

                            if (task.isSuccessful()) {
                                // update the profile
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(name).build();

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                                        // write to the document containing user information
                                        HashMap<String, Object> data = new HashMap<>();
                                        data.put("name", name);
                                        data.put("user", user.getUid());

                                        db.collection("users").document(user.getUid()).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    // go to the MainActivity
                                                    mListener.registerInfo();
                                                }else{
                                                    task.getException().getStackTrace();
                                                }

                                            }
                                        });
                                    }
                                });

                            } else {
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
                                task.getException().getStackTrace();
                            }
                        }
                    });
                }
            }
        });



    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SignupFragmentListener) context;
    }

    SignupFragmentListener mListener;

    interface SignupFragmentListener{
        void registerInfo();
        void returnToLogin();
    }
}