package com.uliamar.restaurant.app.Bus;

/**
 * Created by Bigred on 2014/5/14.
 */
public class LoginSuccessEvent {
    String result;

    public LoginSuccessEvent(String result){
        this.result=result;
    }

    public String getResult(){return result;}
}
