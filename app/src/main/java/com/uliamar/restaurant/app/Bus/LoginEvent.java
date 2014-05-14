package com.uliamar.restaurant.app.Bus;

/**
 * Created by Bigred on 2014/5/14.
 */
public class LoginEvent {
    String phoneno;
    String passWord;

    public LoginEvent(String phoneno,String passWord){
        this.phoneno=phoneno;
        this.passWord=passWord;
    }
    public String getPhoneno(){return phoneno;}
    public String getPassWord(){return passWord;}
}
