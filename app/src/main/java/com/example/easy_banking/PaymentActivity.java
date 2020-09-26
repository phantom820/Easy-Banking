package com.example.easy_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import models.Account;
import presenters.PaymentActivityPresenter;

public class PaymentActivity extends AppCompatActivity implements PaymentActivityPresenter.View {

    private List<Account>activeAccounts;
    private EditText amount;
    private EditText payeeReference;
    private EditText payerReference;
    private EditText payeeAccount;
    private TextView availableBalance;
    private  Spinner accountSpinner;
    private Spinner descriptionSpinner;
    private PaymentActivityPresenter presenter;
    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        //set activity title
        setTitle("Payment");

        //retrive extras
        activeAccounts=(List<Account>)getIntent().getSerializableExtra("activeAccounts");


        presenter=new PaymentActivityPresenter(this,activeAccounts);
        //views
        accountSpinner=(Spinner)findViewById(R.id.account_spinner);
        descriptionSpinner=(Spinner)findViewById(R.id.description_spinner);

        amount=(EditText) findViewById(R.id.amount);
        payeeReference=(EditText) findViewById(R.id.their_reference);
        payerReference=(EditText) findViewById(R.id.my_reference);
        payeeAccount=(EditText)findViewById(R.id.payee_account);
        availableBalance=(TextView)findViewById(R.id.available_balance);

        initAccountSpinner();
        initProgressBar();

        accountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.updatePayerAccount(accountSpinner.getSelectedItem().toString());
                presenter.updateActiveAccount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        initDescriptionSpinner();

        descriptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.updatePayerDescription(descriptionSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        payeeReference.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.updatePayeeReference(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        payerReference.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.updatePayerReference(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        payeeAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.updatePayeeAccount(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.updateAmount(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void initProgressBar(){
        progressBarHandler=new ProgressBarHandler(this);
        hideProgressBar();
    }


    public void initAccountSpinner(){

        String activeAccountsNumbers[]=new String[activeAccounts.size()];
        for(int i=0;i<activeAccountsNumbers.length;++i){
            activeAccountsNumbers[i]=activeAccounts.get(i).getAccountNumber();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PaymentActivity.this,
                android.R.layout.simple_spinner_item,activeAccountsNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountSpinner.setAdapter(adapter);

    }

    public void initDescriptionSpinner(){
        String descriptions[]={"Rent","Electricity","Water","Groceries","Phone","Stokvel","Settlement"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PaymentActivity.this,
                android.R.layout.simple_spinner_item,descriptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        descriptionSpinner.setAdapter(adapter);
    }


    @Override
    public void setPayerAccountError(String error) {
    }

    @Override
    public void setPayeeAccountError(String error) {
        payeeAccount.setError(error);
    }

    @Override
    public void setPayerReferenceError(String error) {
        payerReference.setError(error);
    }

    @Override
    public void setPayeeReferenceError(String error) {
        payeeReference.setError(error);
    }

    @Override
    public void setPayerDescriptionError(String error) {

    }

    @Override
    public void setAmountError(String error) {
        amount.setError(error);
    }

    @Override
    public void updateAvailableBalance(String balance) {
        availableBalance.setText(balance);
    }

    @Override
    public void showProgressBar() {
        progressBarHandler.show();
    }

    @Override
    public void hideProgressBar() {
        progressBarHandler.hide();
    }

    @Override
    public void exit() {
        Toast.makeText(getApplicationContext(),"Payment successful",Toast.LENGTH_SHORT).show();
        finish();


    }

    public void stuff(View v){
        presenter.makePayment();
    }
}
