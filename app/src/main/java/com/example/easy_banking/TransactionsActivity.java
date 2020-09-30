package com.example.easy_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Account;
import models.Debit;
import presenters.PaymentActivityPresenter;
import presenters.TransactionsActivityPresenter;

public class TransactionsActivity extends AppCompatActivity implements TransactionsActivityPresenter.View, View.OnClickListener{

    private TransactionsActivityPresenter presenter;
    private TextView accountType;
    private TextView accountNumber;
    private  TextView balance;
    private   Account selectedAccount;
    private TextView startDate,endDate;
    private LinearLayout transactionItems;

    private  Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transacations);

        setTitle("Transactions");

        //retrive extras
        selectedAccount=(Account) getIntent().getSerializableExtra("selectedAccount");
        presenter=new TransactionsActivityPresenter(this,selectedAccount);

        accountType=(TextView)findViewById(R.id.account_type);
        accountNumber=(TextView)findViewById(R.id.account_number);
        balance=(TextView)findViewById(R.id.money);
        transactionItems=(LinearLayout)findViewById(R.id.transaction_items);
        startDate=(TextView)findViewById(R.id.start) ;
        endDate=(TextView)findViewById(R.id.end);

        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);

        accountType.setText(selectedAccount.getType());
        accountNumber.setText(selectedAccount.getAccountNumber());
        balance.setText(selectedAccount.getBalanceFormated());

        myCalendar = Calendar.getInstance();
        presenter.getTransactions();

    }


    @Override
    public void setStartDate(String date) {
        startDate.setText(date);
    }

    @Override
    public void setEndDate(String date) {
        endDate.setText(date);
    }

    @Override
    public void displayTransactions(List<Debit> transactions) {
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        transactionItems.removeAllViews();
        for(int i=0;i<transactions.size();++i){
            Debit debit=transactions.get(i);
            View spaceView = inflater.inflate(R.layout.card_spacing,null);
            View transactionView = inflater.inflate(R.layout.transaction_card, null);

//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent nextPage=new Intent(getContext(),TransactionsActivity.class);
//                    TextView accountNumber=(TextView)v.findViewById(R.id.account_number);
//                    nextPage.putExtra("selectedAccount",(Serializable) presenter.getSelectedAccount(accountNumber.getText().toString()));
//                    startActivity(nextPage);
//                }
//            });


            TextView date=(TextView)transactionView.findViewById(R.id.date);
            TextView description=(TextView)transactionView.findViewById(R.id.description);
            TextView reference=(TextView)transactionView.findViewById(R.id.reference);
            TextView amount=(TextView)transactionView.findViewById(R.id.money);
            TextView remaining=(TextView)transactionView.findViewById(R.id.available_balance);

            date.setText(DateFormat.format("dd/MM/yyyy", new Date((long)debit.getDate())).toString());
            description.setText("Description "+debit.getDescription());
            reference.setText("Reference "+debit.getReference());
            amount.setText(debit.getAmountFormated());
            remaining.setText(debit.getBalanceFormated());

            transactionItems.addView(spaceView);
            transactionItems.addView(transactionView);
        }
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
    public void onClick(View v) {

        if(v.getId()==R.id.start){

            myCalendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener date;date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    presenter.updateStartDate(year-1900,monthOfYear,dayOfMonth);
                    presenter.getTransactions();
                }

            };

            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }

        else if(v.getId()==R.id.end){

            myCalendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    presenter.updateEndDate(year-1900,monthOfYear,dayOfMonth);
                    presenter.getTransactions();

                }

            };

            new DatePickerDialog(this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }

    }
}
