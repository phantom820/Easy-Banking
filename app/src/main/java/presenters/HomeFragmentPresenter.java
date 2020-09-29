package presenters;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import models.Account;
import models.Client;
import network.GetDataService;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentPresenter {

    private Client client;
    private View view;
    private List<Account>accounts;

    public HomeFragmentPresenter(View view,Client client){
        this.client=client;
        this.view=view;
    }


    public void getAccounts(){
        view.showProgressBar();
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Account>> call = service.getAccounts(client.getIdentityNumber());

        call.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                List<Account>retrievedAccounts=response.body();
                if(retrievedAccounts!=null) {
                    view.displayAccounts(retrievedAccounts);
                    accounts=retrievedAccounts;
                }

                view.hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                view.hideProgressBar();
                System.out.println("fail "+t.toString());
            }
        });

    }

    public List<Account> getActiveAccounts(){
        List<Account>activeAccounts=new ArrayList<>();
        for(int i=0;i<accounts.size();++i){
            Account account=accounts.get(i);
            if(account.isActive()){
                activeAccounts.add(account);
            }
        }

        return  activeAccounts;
    }

    public Account getSelectedAccount(String accountNumber){
        for(int i=0;i<accounts.size();++i){
            Account account=accounts.get(i);
            if(account.getAccountNumber().equals(accountNumber)){
                return  account;
            }
        }
        return  null;
    }

    public List<Account> getAccountsOffline(){
        return  this.accounts;
    }

    public interface View{
        void showProgressBar();
        void hideProgressBar();
        void getAccounts();
        void displayAccounts(List<Account>accounts);
    }
}
