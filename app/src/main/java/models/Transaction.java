package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transaction extends  Account implements Serializable {

    @SerializedName("payer_account")
    private int payerAccount=-1;
    @SerializedName("payee_account")
    private int payeeAccount=-1;

    @SerializedName("payer")
    private Debit payer;
    @SerializedName("payee")

    private  Debit payee;
    public int getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(int payeeAccount) {
        this.payeeAccount = payeeAccount;
    }


    public int getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(int payerAccount) {
        this.payerAccount = payerAccount;
    }


    public Debit getPayer() {
        return payer;
    }

    public void setPayer(Debit payer) {
        this.payer = payer;
    }

    public Debit getPayee() {
        return payee;
    }

    public void setPayee(Debit payee) {
        this.payee = payee;
    }

}
