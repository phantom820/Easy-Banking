package presenters;

import android.view.View;

import com.example.easy_banking.PaymentActivity;

import java.util.List;

import models.Account;
import models.Client;
import models.Debit;
import models.Transaction;
import network.GetDataService;
import network.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivityPresenter {

    private List<Account> accounts;
    private Debit payer;
    private  Debit payee;
    private  View view;
    private Transaction transaction;
    private Account activeAccount;

    public PaymentActivityPresenter(View view, List<Account>accounts){
        this.view=view;
        this.accounts=accounts;
        this.payee=new Debit();
        this.payer=new Debit();
        this.transaction=new Transaction();
    }

    public void updateActiveAccount(){
        for(int i=0;i<accounts.size();++i){
            Account account=accounts.get(i);

            if(Integer.parseInt(account.getAccountNumber())==(transaction.getPayerAccount())){
                this.activeAccount=accounts.get(i);
                view.updateAvailableBalance(this.activeAccount.getBalanceFormated());
                break;
            }
        }

    }

    public void updatePayerAccount(String payerAccountNumber){
            try{
                transaction.setPayerAccount(Integer.parseInt(payerAccountNumber));
            }
            catch (NumberFormatException err){
                view.setPayerAccountError("Invalid account");
                transaction.setPayerAccount(-1);
            }

    }

    public void updatePayeeAccount(String payeeAccountNumber){

        try{
            transaction.setPayeeAccount(Integer.parseInt(payeeAccountNumber));
        }
        catch (NumberFormatException error){
            view.setPayeeAccountError("Invalid account");
            transaction.setPayeeAccount(-1);
        }

    }

    public void updatePayerDescription(String description){
        payer.setDescription(description);
        payee.setDescription(description);
    }

    public void updateAmount(String amount){
        try{
            payer.setDebitAmount(-1*Double.parseDouble(amount));
            payee.setDebitAmount(Double.parseDouble(amount));
            if(Double.parseDouble(amount)>activeAccount.getBalance()){
                view.setAmountError("Exceeds available amount");
            }
        }
        catch (NumberFormatException err){
            payer.setDebitAmount(-1);
            view.setAmountError("Enter valid amount format i.e 10000 or 10000.50");
            System.err.println(err);
        }

        System.out.println(payee.getDebitAmount());
    }

    public boolean validatePayment(){
        boolean state=true;

        if(transaction.getPayerAccount()==-1){
            view.setPayerAccountError("Field cannot be empty");
            state=false;
        }

        if(transaction.getPayeeAccount()==-1){
            view.setPayeeAccountError("Field cannot be empty");
            state=false;
        }

        if(transaction.getPayeeAccount()!=-1 && transaction.getPayerAccount()==(transaction.getPayeeAccount())){
            view.setPayeeAccountError("Cannot pay same account");
            state=false;
        }

        if(payer.getDebitAmount()==-1 || payee.getDebitAmount()==-1 ){
            view.setAmountError("Field cannot be empty");
            state=false;
        }

        if(payee.getDebitAmount()==0 || payee.getDebitAmount()<20){
            view.setAmountError("Amount must be at least R20");
            state=false;
        }


        if(payee.getReference()==null || payee.getReference().equals("")){
            view.setPayeeReferenceError("Field cannot be empty");
            state=false;
        }

        if(payer.getReference()==null || payer.getReference().equals("")){
            view.setPayerReferenceError("Field cannot be empty");
            state=false;
        }



        if(payer.getDescription()==null || payer.getDescription().equals("")){
            view.setPayerDescriptionError("Field cannot be empty");
            state=false;
        }
        return state;
    }

    public  void updatePayerReference(String payReference){
        payer.setReference(payReference);
    }
    public void updatePayeeReference(String payeeReference){
        payee.setReference(payeeReference);
    }

    public void makePayment(){
        if(validatePayment()){
            view.showProgressBar();
            payer.setBalance(payer.getDebitAmount()+activeAccount.getBalance());
            transaction.setPayer(payer);
            transaction.setPayee(payee);

            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<Object>call=service.makePayment(transaction);
            call.enqueue(new Callback<Object>() {
                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    view.showErrorSnackBar("Payment unsuccessful");
                    view.hideProgressBar();

                }

                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    view.showSuccessSnackBar("Payment successful");
                    view.hideProgressBar();
                    view.exit();

                }
            });
        }
    }

    public interface  View {
        void setPayerAccountError(String error);
        void setPayeeAccountError(String error);
        void setPayerReferenceError(String error);
        void setPayeeReferenceError(String error);
        void setPayerDescriptionError(String error);
        void setAmountError(String error);
        void updateAvailableBalance(String balance);
        void showProgressBar();
        void hideProgressBar();
        void showSuccessSnackBar(String msg);
        void showErrorSnackBar(String err);
        void exit();
    }
}
