package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Debit implements Serializable {
    
    @SerializedName("date")
    private  long date;

    @SerializedName("debit_amount")
    private double debitAmount;

    @SerializedName("balance")
    private double balance;

    @SerializedName("description")
    private String description;

    public long getDate() {
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

}
