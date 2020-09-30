package com.example.easy_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import models.Account;
import presenters.TransfersActivityPresenter;

public class TransfersActivity extends AppCompatActivity implements TransfersActivityPresenter.View {

    private List<Account> activeAccounts;
    private List<Account> accounts;
    private TransfersActivityPresenter presenter;
    private Spinner payAccount;
    private Spinner payeeAccount;
    private ProgressBarHandler progressBarHandler;
    private EditText amount;
    private EditText payeeReference;
    private EditText payerReference;
    private TextView availableBalance;
    private Spinner descriptionSpinner;
    private Button transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfers);

        setTitle("Transfers");

        //retrive extras
        activeAccounts=(List<Account>)getIntent().getSerializableExtra("activeAccounts");
        accounts=(List<Account>)getIntent().getSerializableExtra("accounts");

        //
        payAccount=(Spinner)findViewById(R.id.account_spinner);
        payeeAccount=(Spinner)findViewById(R.id.payee_account);
        amount=(EditText)findViewById(R.id.amount);
        payeeReference=(EditText)findViewById(R.id.their_reference);
        payerReference=(EditText)findViewById(R.id.my_reference);
        availableBalance=(TextView)findViewById(R.id.available_balance);
        transfer=(Button)findViewById(R.id.transfer);


        descriptionSpinner=(Spinner)findViewById(R.id.description_spinner);

        presenter=new TransfersActivityPresenter(this,accounts);

        initAccountSpinner();
        initDescriptionSpinner();
        initProgressBar();


        payAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.updatePayerAccount(payAccount.getSelectedItem().toString());
                presenter.updateActiveAccount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        payeeAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.updatePayeeAccount(payeeAccount.getSelectedItem().toString());
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


        availableBalance.setText(activeAccounts.get(0).getBalanceFormated());


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


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TransfersActivity.this,
                android.R.layout.simple_spinner_item,activeAccountsNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payAccount.setAdapter(adapter);

        ArrayList<String>otherAccounts=new ArrayList<>();
        for(int i=0;i<accounts.size();++i){
            if(!accounts.get(i).isActive()){
                otherAccounts.add(accounts.get(i).getAccountNumber());
            }
        }

//        String secondaryAccounts[]=(String [])otherAccounts.toArray();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(TransfersActivity.this,
                android.R.layout.simple_spinner_item,otherAccounts);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        payeeAccount.setAdapter(adapter2);

    }

    public void initDescriptionSpinner(){
        String descriptions[]={"Savings"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TransfersActivity.this,
                android.R.layout.simple_spinner_item,descriptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        descriptionSpinner.setAdapter(adapter);
    }


    @Override
    public void setPayerAccountError(String error) {

    }

    @Override
    public void setPayeeAccountError(String error) {

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
    public void showSuccessSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();
        snackbar.show();
    }

    @Override
    public void showErrorSnackBar(String err) {
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), err, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.RED);
        snackbar.show();
    }
    @Override
    public void exit() {
        Toast.makeText(getApplicationContext(),"Transfer successful",Toast.LENGTH_LONG).show();
        finish();
    }

    public void transfer(View v){
        presenter.makePayment();
    }
}
