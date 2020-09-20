package com.example.easy_banking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import models.Client;
import presenters.RegistrationActivityPresenter;

public class RegistrationActivity extends AppCompatActivity implements RegistrationActivityPresenter.View {


    private RegistrationActivityPresenter presenter;
    private EditText identityNumber;
    private EditText email;
    private  EditText name;
    private  EditText surname;
//    private EditText number;
//    private EditText confirmNumber;
    private EditText password;
    private  EditText confirmPassword;
    private Spinner spinner;
    private AppCompatButton register;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        presenter=new RegistrationActivityPresenter(this);
        initProgressBar();

        //views
        identityNumber=(EditText)findViewById(R.id.identity_number);
        email=(EditText)findViewById(R.id.email);
        name=(EditText)findViewById(R.id.firstname);
        surname=(EditText)findViewById(R.id.surname);
//        number=(EditText)findViewById(R.id.number);
//        confirmNumber=(EditText)findViewById(R.id.confirmNumber);
        password=(EditText)findViewById(R.id.password);
        confirmPassword=(EditText)findViewById(R.id.confirmPassword);
        spinner = (Spinner)findViewById(R.id.spinner);
        register=(AppCompatButton) findViewById(R.id.register);

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

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.updateEmail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.updateName(s.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.updateSurname(s.toString().toUpperCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        /*number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.updateNumber(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

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

        //spinner
        final String accountTypes[]={"Savings","Cheque"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegistrationActivity.this,
                android.R.layout.simple_spinner_item,accountTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.updateAccountType(accountTypes[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                presenter.updateAccountType(accountTypes[0]);
            }
        });

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

    public void validateUser(View v){
        presenter.registerUser(confirmPassword.getText().toString());
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

    @Override
    public void setEmailError(String error) {
        email.setError(error);
    }

    @Override
    public void setNameError(String error) {
        name.setError(error);
    }

    @Override
    public void setSurnameError(String error) {
        surname.setError(error);
    }

   /* @Override
    public void setNumberError(String error) {
        number.setError(error);
    }

    @Override
    public void setConfirmNumberError(String error) {
        confirmNumber.setError(error);
    }
*/
    @Override
    public void setPasswordError(String error) {
        password.setError(error);
    }

    @Override
    public void setConfirmPasswordError(String error) {
        confirmPassword.setError(error);
    }

    @Override
    public void navigateToHome(Client client) {
        Intent homeIntent=new Intent(getApplicationContext(),HomeActivity.class);
        homeIntent.putExtra("client",client);
        startActivity(homeIntent);
        finish();
    }

}
