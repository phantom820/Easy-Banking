package com.example.easy_banking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.List;

import models.Account;

public class TransfersActivity extends AppCompatActivity {

    private List<Account> activeAccounts;
    private List<Account> accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfers);

        //retrive extras
        activeAccounts=(List<Account>)getIntent().getSerializableExtra("activeAccounts");
        accounts=(List<Account>)getIntent().getSerializableExtra("accounts");
    }
}
