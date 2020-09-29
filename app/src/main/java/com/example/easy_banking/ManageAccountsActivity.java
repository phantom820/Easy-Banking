package com.example.easy_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.List;

import models.Account;
import models.Client;
import presenters.ManageAccountsActivityPresenter;

public class ManageAccountsActivity extends AppCompatActivity implements ManageAccountsActivityPresenter.View, View.OnClickListener{

    private List<Account>accounts;
    private Client client;
    private ManageAccountsActivityPresenter presenter;
    private LinearLayout accountItems;
    private FloatingActionButton fab;
    private Dialog createAccountDialog;
    private Button createAccount;
    private ProgressBarHandler progressBarHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);

        accounts=(List<Account>)getIntent().getSerializableExtra("accounts");
        client=(Client)getIntent().getSerializableExtra("client");

        presenter=new ManageAccountsActivityPresenter(this,accounts,client);

        accountItems=(LinearLayout)findViewById(R.id.account_items);

        fab=(FloatingActionButton)findViewById(R.id.create_account);
        fab.setOnClickListener(this);

        displayAccounts(presenter.getAccounts());
        initCreateAccountDialog();
        initProgressBar();

    }

    @Override
    public void displayAccounts(List<Account> accounts) {
        accountItems.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i=0;i<accounts.size();++i){
            Account account=accounts.get(i);

            View spaceView = inflater.inflate(R.layout.card_spacing,null);
            View cardView = inflater.inflate(R.layout.account_card, null);

//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent nextPage=new Intent(getContext(),TransactionsActivity.class);
//                    TextView accountNumber=(TextView)v.findViewById(R.id.account_number);
//                    nextPage.putExtra("selectedAccount",(Serializable) presenter.getSelectedAccount(accountNumber.getText().toString()));
//                    startActivity(nextPage);
//                }
//            });

            TextView accountType=(TextView)cardView.findViewById(R.id.account_type);
            TextView accountNumber=(TextView)cardView.findViewById(R.id.account_number);
            TextView accountBalance=(TextView)cardView.findViewById(R.id.money);

            accountType.setText(account.isActive()?"Active "+account.getType().toLowerCase()+" account":account.getType()+" account");
            accountNumber.setText(account.getAccountNumber());
            accountBalance.setText(account.getBalanceFormated());


            accountItems.addView(spaceView);
            accountItems.addView(cardView);
        }
    }

    @Override
    public void showCreateAccountsDialog() {
        createAccountDialog.show();
    }

    @Override
    public void hideCreateAccountsDialog() {
        createAccountDialog.dismiss();
    }

    @Override
    public void showProgressBar() {
        progressBarHandler.show();
    }

    @Override
    public void hideProgressBar() {
        progressBarHandler.hide();
    }

    public void initCreateAccountDialog(){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        createAccountDialog  =new Dialog(this);
        createAccountDialog.setContentView(R.layout.create_account_popup);
        createAccountDialog.getWindow().setLayout((6 * width)/7, LinearLayout.LayoutParams.WRAP_CONTENT);

        //spinner
        Spinner spinner=(Spinner)createAccountDialog.findViewById(R.id.spinner);
        final String accountTypes[]={"Savings","Notice deposit"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ManageAccountsActivity.this,
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

        createAccount=(Button)createAccountDialog.findViewById(R.id.account_creation);
        createAccount.setOnClickListener(this);
    }

    public void initProgressBar(){
        progressBarHandler=new ProgressBarHandler(this);
        hideProgressBar();
    }

    @Override
    public void showErrorSnackBar(String error) {
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.RED);
        snackbar.show();
    }

    @Override
    public void showSuccessSnackBar(String error) {
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View sbView = snackbar.getView();
//        sbView.setBackgroundColor(Color.RED);
        snackbar.show();
    }



    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.create_account){
            showCreateAccountsDialog();
        }

        else if(v.getId()==R.id.account_creation){
            presenter.createAccount();
        }
    }
}
