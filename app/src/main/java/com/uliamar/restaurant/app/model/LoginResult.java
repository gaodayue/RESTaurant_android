package com.uliamar.restaurant.app.model;

/**
 * Created by Bigred on 2014/5/15.
 */
public class LoginResult {
    private int cust_id;
    private String cust_name;
    private String cust_access_token;

    public void setCust_id(int cust_id){
        this.cust_id=cust_id;
    }
    public void setCust_name(String cust_name){
        this.cust_name=cust_name;
    }
    public void setCust_access_token(String cust_access_token){
        this.cust_access_token=cust_access_token;
    }
    public int getCust_id(){return cust_id;}
    public String getCust_name(){return cust_name;}
    public String getCust_access_token(){return cust_access_token;}
}
