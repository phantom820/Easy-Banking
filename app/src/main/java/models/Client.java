package models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Client implements Serializable {

    @SerializedName("identity_number")
    private String identityNumber;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private  String name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("number")
    private String number;
    @SerializedName("password")
    private String password;
    @SerializedName("type")
    private String type;

    public  Client(){}

    public Client(String email,String password){
        this.email=email;
        this.password=password;
    }

    public Map<String,String> toMap(){
        Map<String,String>userMap=new HashMap<String, String>();
        userMap.put("email",email);
        userMap.put("password",password);
        return  userMap;
    }

    public String getIdentityNumber(){
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber){
        this.identityNumber=identityNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType(){
        return  type;
    }

    public void setAccountType(String type){
        this.type=type;
    }

    @Override
    public String toString() {
        return "Email : " + email + "\nFullName : " + name;
    }

}
