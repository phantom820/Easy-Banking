package com.example.easy_banking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import models.Client;
import presenters.MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private MainActivityPresenter presenter;
    private EditText identityNumber;
//    private EditText email;
    private EditText password;
    private ProgressBar progressBar;
    private AppCompatButton login;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter=new MainActivityPresenter(this);
        initProgressBar();

        //views
        identityNumber=(EditText)findViewById(R.id.identity_number);
//        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login=(AppCompatButton)findViewById(R.id.login);
        register=(TextView)findViewById(R.id.register);

        identityNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.updateIdentityNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        email.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                presenter.updateEmail(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.updatePassword(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public void validateUser(View v){
        presenter.validateClient();
    }

    public void  registerUser(View v){
        Intent registrationIntent=new Intent(getApplicationContext(),RegistrationActivity.class);
        startActivity(registrationIntent);
    }

    private void initProgressBar(){
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);

        progressBar.setIndeterminate(true);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Resources.getSystem().getDisplayMetrics().widthPixels,
                250);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addContentView(progressBar, params);
        hideProgressBar();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setIdentityNumberError(String error) {
        identityNumber.setError(error);
    }

//    @Override
//    public void setEmailError(String error) {
//        email.setError(error);
//    }

    @Override
    public void setPasswordError(String error){
        password.setError(error);
    }

    @Override
    public void navigateToHome(Client client) {
        Intent homeIntent=new Intent(getApplicationContext(),HomeActivity.class);
        homeIntent.putExtra("client",client);
        startActivity(homeIntent);
    }

    /** this is just a debug function **/
    @Override
    public void status(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
    }
}

