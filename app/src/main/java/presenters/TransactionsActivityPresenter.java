package presenters;

import android.text.format.DateFormat;
import android.view.View;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import models.Account;
import models.Debit;

public class TransactionsActivityPresenter {

    private View view;
    private Account account;
    private Date startDate;
    private Date endDate;

    public TransactionsActivityPresenter(View view,Account account){
        this.view=view;
        this.account=account;
    }

    public void getTransactions(){
        List<Debit>temp=account.getDebits();

        Collections.sort(temp);
        List<Debit>subItems=new LinkedList<>();

        if(startDate!=null && endDate!=null) {
            long lowerBound = this.startDate.getTime()-86400000;
            long upperBound = this.endDate.getTime()+86400000;
            if (upperBound >= lowerBound) {
                for (int i = 0; i < temp.size(); ++i) {
                    Debit debit = temp.get(i);
                    if (lowerBound <= debit.getDate() && debit.getDate() <= upperBound) {
                        subItems.add(debit);
                    }
                }

                view.displayTransactions(subItems);
            }

            else {
                view.displayTransactions(subItems);
                view.showErrorSnackBar("Invalid date ranges");
            }

        }

        else{
            view.displayTransactions(temp.subList(0,temp.size()));
        }

    }

    public void  updateStartDate(int year,int month,int day){
        this.startDate=new Date(year,month,day);
        view.setStartDate((DateFormat.format("dd/MM/yyyy",this.startDate)).toString());
    }

    public void updateEndDate(int year,int month,int day){
        this.endDate=new Date(year,month,day);
        view.setEndDate((DateFormat.format("dd/MM/yyyy",this.endDate)).toString());

    }

    public interface View{

        void setStartDate(String date);
        void setEndDate(String date);
        void displayTransactions(List<Debit>transactions);
        void showErrorSnackBar(String error);
    }
}
