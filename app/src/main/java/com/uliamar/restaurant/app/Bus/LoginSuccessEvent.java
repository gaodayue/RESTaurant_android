package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.LoginResult;

/**
 * Created by Bigred on 2014/5/14.
 */
public class LoginSuccessEvent {
    private LoginResult result;

    public LoginSuccessEvent(LoginResult result){
        this.result=result;
    }

    public LoginResult getResult(){return result;}
}
