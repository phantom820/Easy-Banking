package presenters;

import com.example.easy_banking.ManageAccountsActivity;

import java.util.List;

import models.Account;
import models.Client;
import network.GetDataService;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageAccountsActivityPresenter {

    private View view;
    private List<Account>accounts;
    private Client client;
    private Account newAccount;

    public ManageAccountsActivityPresenter(View view,List<Account>accounts,Client client){
        this.view=view;
        this.accounts=accounts;
        this.client=client;
        this.newAccount=new Account();
    }

    public List<Account> getAccounts(){
        return  this.accounts;
    }

    public void updateAccountType(String type){
        newAccount.setType(type);
    }

    public void createAccount(){
        newAccount.setBalance(0);
        newAccount.setActive(false);
        newAccount.setIdentityNumber(client.getIdentityNumber());
        view.hideCreateAccountsDialog();
        view.showProgressBar();
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<String> call = service.addAccount(newAccount);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String result=response.body();

                if (result!=null){
//                    view.hideCreateAccountsDialog();
                    newAccount.setAccountNumber(result);
                    accounts.add(newAccount);

//                    view.hideCreateAccountsDialog();
                    view.displayAccounts(accounts);
                    view.hideProgressBar();
                    view.showSuccessSnackBar("New account created");
                }

                else{
                    /** Do stuff here*/
                    view.hideProgressBar();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                view.hideProgressBar();
                view.showErrorSnackBar("Unable to create account");
                System.out.println("fails "+t.toString());
            }


        });
    }

    public interface  View{
        void displayAccounts(List<Account>accounts);
        void showCreateAccountsDialog();
        void  hideCreateAccountsDialog();
        void showProgressBar();
        void  hideProgressBar();
        void showErrorSnackBar(String error);
        void showSuccessSnackBar(String msg);
    }
}
