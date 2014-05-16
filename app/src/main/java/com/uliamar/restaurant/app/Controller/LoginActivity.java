package com.uliamar.restaurant.app.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.LoginEvent;
import com.uliamar.restaurant.app.Bus.LoginSuccessEvent;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.LoginResult;
import com.uliamar.restaurant.app.services.DataService;
import com.uliamar.restaurant.app.services.RESTrepository;

/**
 * A login screen that offers login via email/password.

 */
public class LoginActivity extends Activity {
    DataService dataService;
    public static final String PREF_ACCOUNT_ID = "cust_id";
    public static final String PREF_TOKEN = "accessToken";
    private String SHARED_PREF_DB_NAME = "loginResult";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataService = new DataService();

        /**
         * Check either we are already logged in
         */
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_DB_NAME, 0);
        if (sharedPreferences.getString(PREF_TOKEN, "").length() != 0) {
            RESTrepository.setToken(sharedPreferences.getString(PREF_TOKEN, ""));
            RESTrepository.setUser_id(sharedPreferences.getInt(PREF_ACCOUNT_ID, 0));
            goToMainActivity();
        }

        setContentView(R.layout.activity_login);
        SharedPreferences preferences=getSharedPreferences("pushService", MODE_PRIVATE);
        String userId=preferences.getString("user_id","no data");
        //Toast.makeText(this,"user id is:"+userId,Toast.LENGTH_SHORT).show();
        Button loginButton=(Button)findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                String phoneno=((TextView)findViewById(R.id.email)).getText().toString();
                String password=((TextView)findViewById(R.id.password)).getText().toString();

          //      Toast.makeText(getBaseContext(),"login..."+phoneno+"..."+password,Toast.LENGTH_SHORT).show();
                BusProvider.get().post(new LoginEvent(phoneno,password));
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        BusProvider.get().register(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        BusProvider.get().unregister(this);
    }

    @Subscribe
    public void onLoginSuccessEvent(LoginSuccessEvent loginSuccessEvent){
        Log.i("bigred","comes here");
        LoginResult result=loginSuccessEvent.getResult();
      //  Toast.makeText(this,result.getCust_id()+result.getCust_name()+result.getCust_access_token(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
        SharedPreferences preferences=this.getSharedPreferences(SHARED_PREF_DB_NAME, MODE_PRIVATE);
        preferences.edit().putString(PREF_TOKEN,result.getCust_access_token()).commit();
        preferences.edit().putInt(PREF_ACCOUNT_ID,result.getCust_id()).commit();
        goToMainActivity();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}



