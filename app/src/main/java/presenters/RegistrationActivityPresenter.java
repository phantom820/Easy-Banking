package presenters;

import android.telephony.PhoneNumberUtils;

import models.Client;
import network.GetDataService;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivityPresenter {

    private Client client;
    private View view;

    public RegistrationActivityPresenter(View view){
        this.client=new Client();
        this.view=view;

    }

    public void updateIdentityNumber(String identityNumber){
        client.setIdentityNumber(identityNumber);
    }

    public void updateEmail(String email){
        client.setEmail(email);
    }

    public void updateName(String name){
        client.setName(name);
    }

    public void  updateSurname(String surname){
        client.setSurname(surname);
    }

//    public  void updateNumber(String number){
//        client.setNumber(number);
//    }

    public void updatePassword(String password){
        client.setPassword(password);
    }

    public void updateAccountType(String accountType){
        client.setAccountType(accountType);
    }

    public boolean validateCredentials(String confirmPassword){

        boolean state=true;

        if(client.getIdentityNumber()==null || client.getIdentityNumber().equals("")){
            view.setIdentityNumberError("Field cannot be empty");
            state=false;
        }

        else if(!client.getIdentityNumber().matches("(([0-9][0-9][0-1][0-9][0-3][0-9])([0-9][0-9][0-9][0-9])([0-1])([0-9])([0-9]))")){
            view.setIdentityNumberError("Invalid identity number");
            state=false;
        }

        if(client.getEmail()==null || client.getEmail().equals("")){
            view.setEmailError("Field cannot be empty");
            state=false;
        }

        else if(!client.getEmail().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            view.setEmailError("Enter a valid email");
            state=false;
        }

        if(client.getName()==null || client.getName().equals("")){
            view.setNameError("Field cannot be empty");
            state=false;
        }

        if(client.getSurname()==null || client.getSurname().equals("")){
            view.setSurnameError("Field cannot be empty");
            state=false;
        }


      /*  if(client.getNumber()==null || client.getNumber().equals("")){
            view.setNumberError("Field cannot be empty");
            state=false;
        }

        else if(client.getNumber().length()!=10 || client.getNumber().charAt(0)!='0' || !PhoneNumberUtils.isGlobalPhoneNumber(client.getNumber())){
            view.setNumberError("Enter a valid number");
            state=false;
        }

        else if(!client.getNumber().equals(confirmNumber)){
            view.setConfirmNumberError("Numbers do not match");
            state=false;
        }*/

        if(client.getPassword()==null || client.getPassword().equals("")){
            view.setPasswordError("Field cannot be empty");
            state=false;
        }

        else if(client.getPassword().length()<6){
            view.setPasswordError("Password must be at least six characters long");
            state=false;
        }

        else if(!client.getPassword().equals(confirmPassword)){
            view.setConfirmPasswordError("Passwords do not match");
            state=false;
        }

        return state;
    }

    public boolean registerUser(String confirmPassword){

        if(!validateCredentials(confirmPassword)){
            return  false;
        }

        else{
            view.showProgressBar();
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<Client> call = service.addClient(client);

            call.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    System.out.println(response.body());

                    Client c=response.body();

                    view.hideProgressBar();

                    if(c!=null)
                    view.navigateToHome(c);

                    return;
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    System.out.println("fails "+t.toString());
                    view.hideProgressBar();
                }
            });
        }

        return  true;
    }

    public  interface  View{
        void showProgressBar();
        void hideProgressBar();
        void setIdentityNumberError(String error);
        void setEmailError(String error);
        void setNameError(String error);
        void setSurnameError(String error);
//        void setNumberError(String error);
//        void setConfirmNumberError(String error);
        void setConfirmPasswordError(String error);
        void setPasswordError(String error);
        void navigateToHome(Client client);

    }
}
