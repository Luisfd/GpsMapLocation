package com.example.gpsmaplocation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    EditText email, password;
    Button loginButton, registerButton;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
                login();
        });
        registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> ((LoginActivity) getActivity()).viewPager.setCurrentItem(1));

        if(mAuth.getCurrentUser() != null)
            access();

        return view;
    }


    private void login(){
        String emailText, passwordText, emailPattern;
        emailText = email.getText().toString();
        passwordText = password.getText().toString();

        emailPattern = "[a-zA-Z0-0._-]+@[a-z]+\\.+[a-z]+";

        if(!emailText.matches(emailPattern)) {
            email.setError("Wrong Email");
            email.requestFocus();
        }else if(passwordText.isEmpty()){
            password.setError("Empty Password");
            password.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(),"Login", Toast.LENGTH_SHORT).show();
                        access();
                    }else{
                        Toast.makeText(getContext(),"Error" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void access(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.putExtra("username", username);
        startActivity(intent);
    }

}