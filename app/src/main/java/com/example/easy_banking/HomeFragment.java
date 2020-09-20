package com.example.easy_banking;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import models.Account;
import models.Client;
import presenters.HomeFragmentPresenter;
import presenters.MainActivityPresenter;

public class HomeFragment extends Fragment implements HomeFragmentPresenter.View {

    private HomeFragmentPresenter presenter;
    private Client client;
    private TextView initials;
    private TextView nameSurname;
    private LinearLayout fragmentItems;

    public HomeFragment(Client client){
        this.client=client;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view =  (View)inflater.inflate(R.layout.fragment_home,container,false);

        presenter = new HomeFragmentPresenter(this,client);
        initials=(TextView)view.findViewById(R.id.initials);
        nameSurname=(TextView)view.findViewById(R.id.name_surname);
        fragmentItems=(LinearLayout) view.findViewById(R.id.fragment_items);
        //set the name and surname stuff here
        initials.setText(client.getName().charAt(0)+""+client.getSurname().charAt(0));
        nameSurname.setText(client.getName().charAt(0)+" "+client.getSurname());

        //get accounts
        getAccounts();

        return  view;
    }



    @Override
    public void getAccounts() {
        presenter.getAccounts();
    }

    @Override
    public void displayAccounts(List<Account>accounts) {

        LayoutInflater inflater = (LayoutInflater) getView().getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i=0;i<accounts.size();++i){
            Account account=accounts.get(i);

            View spaceView = inflater.inflate(R.layout.card_spacing,null);
            View cardView = inflater.inflate(R.layout.account_card, null);

            TextView accountType=(TextView)cardView.findViewById(R.id.account_type);
            TextView accountNumber=(TextView)cardView.findViewById(R.id.account_number);
            TextView accountBalance=(TextView)cardView.findViewById(R.id.money);

            accountType.setText(account.isActive()?"Active "+account.getType().toLowerCase()+" account":account.getType()+" account");
            accountNumber.setText(account.getAccountNumber());
            accountBalance.setText(account.getBalanceFormated());


            fragmentItems.addView(spaceView);
            fragmentItems.addView(cardView);
        }

    }
}
