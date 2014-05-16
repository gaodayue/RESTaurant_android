package com.uliamar.restaurant.app.Bus;

/**
 * Created by Bigred on 2014/5/16.
 */
public class PushRegisterEvent {
    private int customer_id;
    private String access_token;
    private String push_id;

    public PushRegisterEvent(int customer_id,String access_token,String push_id){
        this.customer_id=customer_id;
        this.access_token=access_token;
        this.push_id=push_id;
    }

    public int getCustomer_id(){return customer_id;}
    public String getAccess_token(){return access_token;}
    public String getPush_id(){return push_id;}
}
