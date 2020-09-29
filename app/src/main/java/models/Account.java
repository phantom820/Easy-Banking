package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {

    @SerializedName("account_number")
    private String accountNumber;

    @SerializedName("type")
    private  String type;

    @SerializedName("balance")
    private  double balance;

    @SerializedName("active")
    private boolean active;

    @SerializedName("identity_number")
    private  String identityNumber;

    @SerializedName("debits")
    private ArrayList<Debit>debits;


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public String getBalanceFormated(){

        String balanceString=balance+"";
        String data[]=balanceString.split("\\.");
        String line=data[0];
        String decimal=data[1];
        StringBuilder balance=new StringBuilder("R ");

        if(line.length()<=3){
            balance.append(line);
        }

        else{
            if(line.length()%3==0){
                balance.append(line.substring(0,3));
                for(int i=0;i<line.length();i=i+3){
                    balance.append(" "+line.substring(i,i+3));
                }
            }

            else{
                int r=line.length()%3;
                balance.append(line.substring(0,r));
                for(int i=r;i<line.length();i=i+3){
                    balance.append(" "+line.substring(i,i+3));
                }
            }
        }

        if(decimal.length()<2){
            decimal=decimal+"0";
        }

        balance.append("."+decimal);

        return  balance.toString();

    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public ArrayList<Debit> getDebits() {
        return debits;
    }

    public void setDebits(ArrayList<Debit> debits) {
        this.debits = debits;
    }


}
