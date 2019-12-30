package com.example.fellowtraveller;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterStage1Fragment extends Fragment {

    private TextInputEditText name,email,password,password2;

    public RegisterStage1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_stage1, container, false);
        name = view.findViewById(R.id.RegisterStage1Fragment_editText_name1);
        email = view.findViewById(R.id.RegisterStage1Fragment_editText_email1);
        password = view.findViewById(R.id.RegisterStage1Fragment_editText_password1);
        password2 = view.findViewById(R.id.RegisterStage1Fragment_editText_password22);













        return view;
    }


    public String toString(){
        return "stage1";
    }


    public boolean Check(){
        if(CheckName() & CheckEmail() &  CheckPasswordS() ){
            return true;
        }else{
            return false;
        }
    }

    private boolean CheckEmail() {
        String emailInput = email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean CheckName() {
        String usernameInput = name.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            name.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }

    private boolean CheckPassword() {
        String passwordInput = password.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            password.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private boolean CheckPassword2() {
        String passwordInput = password2.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            password2.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            password2.setError(null);
            return true;
        }
    }


    public boolean CheckPasswordS(){
        if( password2.getText().toString().trim().equals( password.getText().toString().trim()) & CheckPassword() ) {
            password2.setError(null);
            password.setError(null);
            return true;
        }
        else {

            password.setError("Υποχρεωτικό Πεδίο!");
            password2.setError("Υποχρεωτικό Πεδίο!");
            return false;
        }
    }
    public String getName() {
        return name.getText().toString();
    }

    public String getEmail() {
        return email.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
    }


}
