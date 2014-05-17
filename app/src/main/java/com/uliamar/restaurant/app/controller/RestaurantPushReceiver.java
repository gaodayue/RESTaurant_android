package com.uliamar.restaurant.app.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.baidu.android.pushservice.PushConstants;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bigred on 2014/5/9.
 */
public class RestaurantPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context,Intent intent){
        if(intent.getAction().equals(PushConstants.ACTION_RECEIVE)){
            final String method = intent
                    .getStringExtra(PushConstants.EXTRA_METHOD);
            int errorCode = intent
                    .getIntExtra(PushConstants.EXTRA_ERROR_CODE,
                            PushConstants.ERROR_SUCCESS);
            String content = "";
            if (intent.getByteArrayExtra(PushConstants.EXTRA_CONTENT) != null) {
                content = new String(
                        intent.getByteArrayExtra(PushConstants.EXTRA_CONTENT));
            }
            Log.i("bigred","method : " + method + "\n result: " + errorCode
                    + "\n content = " + content);
            if(errorCode==0){
                JSONObject contentJson;
                JSONObject paramsJson;
                try{
                    contentJson=new JSONObject(content);
                    String paramsString=contentJson.getString("response_params");
                    paramsJson=new JSONObject(paramsString);
                    String userId=paramsJson.getString("user_id");
                    Log.i("bigred","=============user id is:"+userId);
                    SharedPreferences preferences=context.getSharedPreferences("pushService",0);
                    preferences.edit().putString("user_id",userId).commit();

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
//            Toast.makeText(
//                    context,
//                    "method : " + method + "\n result: " + errorCode
//                            + "\n content = " + content, Toast.LENGTH_SHORT)
//                    .show();
        }else if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            String message = intent.getExtras().getString(
                    PushConstants.EXTRA_PUSH_MESSAGE_STRING);
            Toast.makeText(context, "receive message："+message, Toast.LENGTH_SHORT).show();
        }else if(intent.getAction().equals(PushConstants.ACTION_RECEIVER_NOTIFICATION_CLICK)){
            String myMsg=intent.getStringExtra(PushConstants.EXTRA_EXTRA);
            Log.i("bigred",myMsg);
            try{
                JSONObject myJsonObj=new JSONObject(myMsg);
                int invitation_id=myJsonObj.getInt("invitation_id");
                Intent myIntent=OrderReviewActivity.createIntent(context,invitation_id);
                context.startActivity(myIntent);
                Toast.makeText(context,myMsg,Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
