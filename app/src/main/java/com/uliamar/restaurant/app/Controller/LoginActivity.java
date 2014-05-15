package com.uliamar.restaurant.app.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.squareup.otto.Subscribe;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.LoginEvent;
import com.uliamar.restaurant.app.Bus.LoginSuccessEvent;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.LoginResult;
import com.uliamar.restaurant.app.services.DataService;

/**
 * A login screen that offers login via email/password.

 */
public class LoginActivity extends Activity {
    DataService dataService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         dataService = new DataService();

        setContentView(R.layout.activity_login);
        SharedPreferences preferences=getSharedPreferences("pushService",0);
        String userId=preferences.getString("user_id","no data");
        Toast.makeText(this,"user id is:"+userId,Toast.LENGTH_SHORT).show();
        Button loginButton=(Button)findViewById(R.id.email_sign_in_button);
        loginButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                String phoneno=((TextView)findViewById(R.id.email)).getText().toString();
                String password=((TextView)findViewById(R.id.password)).getText().toString();

                Toast.makeText(getBaseContext(),"login..."+phoneno+"..."+password,Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this,result.getCust_id()+result.getCust_name()+result.getCust_access_token(),Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,"Login Success",Toast.LENGTH_SHORT).show();
        SharedPreferences preferences=this.getSharedPreferences("accessToken", 0);
        preferences.edit().putString("accessToken",result.getCust_access_token()).commit();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}



