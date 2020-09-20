package presenters;

import android.view.View;

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

    public HomeFragmentPresenter(View view,Client client){
        this.client=client;
        this.view=view;
    }

    public void getAccounts(){

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Account>> call = service.getAccounts(client.getIdentityNumber());

        call.enqueue(new Callback<List<Account>>() {
            @Override
            public void onResponse(Call<List<Account>> call, Response<List<Account>> response) {
                List<Account>accounts=response.body();
                view.displayAccounts(accounts);
            }

            @Override
            public void onFailure(Call<List<Account>> call, Throwable t) {
                System.out.println("fail "+t.toString());
            }
        });

    }

    public interface View{
        void getAccounts();
        void displayAccounts(List<Account>accounts);
    }
}
