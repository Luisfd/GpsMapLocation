package com.example.gpsmaplocation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {

    TextView backLogin;
    EditText email, password, password2;
    Button registerButton;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.gpsmaplocation.RegisterFragment newInstance(String param1, String param2) {
        com.example.gpsmaplocation.RegisterFragment fragment = new com.example.gpsmaplocation.RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        password2 = view.findViewById(R.id.password2);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            register();
        });
        backLogin = view.findViewById(R.id.backLogin);
        backLogin.setOnClickListener(v -> ((LoginActivity) getActivity()).viewPager.setCurrentItem(0));
        return view;
    }

    private void register(){
        String emailText, passwordText, password2Text, emailPattern;
        emailText = email.getText().toString();
        passwordText = password.getText().toString();
        password2Text = password2.getText().toString();

        emailPattern = "[a-zA-Z0-0._-]+@[a-z]+\\.+[a-z]+";

        if(!emailText.matches(emailPattern)) {
            email.setError("Wrong Email");
            email.requestFocus();
        }else if(passwordText.isEmpty() || passwordText.length()<4){
            password.setError("Password is too short");
            password.requestFocus();
        }else if(!passwordText.equals(password2Text)){
            password2.setError("Confirm the password again");
            password2.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(emailText,passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(),"Registration Complete", Toast.LENGTH_SHORT).show();
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
