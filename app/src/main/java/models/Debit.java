package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Debit implements Serializable,Comparable<Debit> {


    @SerializedName("date")
    private  double date;

    @SerializedName("debit_amount")
    private double debitAmount=-1;

    @SerializedName("balance")
    private double balance;

    @SerializedName("reference")
    private String reference;

    @SerializedName("description")
    private String description;

    public double getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public String getAmountFormated(){

        String balanceString=Math.abs(debitAmount)+"";
        String data[]=balanceString.split("\\.");
        String line=data[0];
        String decimal=data[1];
        StringBuilder balance=(debitAmount>0?new StringBuilder("R "):new StringBuilder("R - "));

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




    @Override
    public int compareTo(Debit o) {

        if(this.date==o.date){
            return 0;
        }

        else if(this.date>o.date){
            return -1;
        }
        return 1;
    }
}
