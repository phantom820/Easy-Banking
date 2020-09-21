package presenters;

import java.util.List;

import models.Client;
import network.GetDataService;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter {

    private Client client;
    private View view;

    public MainActivityPresenter(View view){
        this.client=new Client();
        this.view=view;
    }

    public void updateIdentityNumber(String identityNumber){
        client.setIdentityNumber(identityNumber);
    }

    public void updatePassword(String password){
        client.setPassword(password);
    }

//    public void updateEmail(String email){
//        client.setEmail(email);
//    }

    public boolean validateCredentials(){

        if(client.getIdentityNumber()==null || client.getIdentityNumber().equals("")){
            view.setIdentityNumberError("Field cannot be empty");
            return  false;
        }

        else if(!client.getIdentityNumber().matches("(([0-9][0-9][0-1][0-9][0-3][0-9])([0-9][0-9][0-9][0-9])([0-1])([0-9])([0-9]))")){
            view.setIdentityNumberError("Invalid identity number");
            return  false;
        }

        else if(client.getPassword()==null || client.getPassword().equals("")){
            view.setPasswordError("Field cannot be empty");
            return  false;
        }

        return true;
    }

    public boolean validateClient(){

        if(!validateCredentials()){
            return  false;
        }

        else{
            view.showProgressBar();
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Client>> call = service.getUsers(client.getIdentityNumber(),client.getPassword());
            call.enqueue(new Callback<List<Client>>() {
                @Override
                public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {

                    List<Client>result=response.body();

                    if (result!=null && result.size()>0){
                        view.hideProgressBar();
                        view.navigateToHome(result.get(0));
                    }

                    else{
                        view.hideProgressBar();
                        view.status("not found");
                    }
                }

                @Override
                public void onFailure(Call<List<Client>> call, Throwable t) {
                    System.out.println("fails "+t.toString());
                    view.hideProgressBar();
                }


            });
        }

        return  true;
    }

    public interface View{

        void showProgressBar();
        void hideProgressBar();
        void setIdentityNumberError(String error);
//        void setEmailError(String error);
        void setPasswordError(String error);
        void navigateToHome(Client client);
        void status(String info);
    }


}
