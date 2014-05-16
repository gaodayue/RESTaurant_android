package com.uliamar.restaurant.app.model;

/**
 * Created by Pol on 07/05/14.
 */
public class User {
    private int cust_id;
    private String name;
    private String phoneno;
    private boolean is_host;
    private String inv_status;

    public String getInv_status() {
        return inv_status;
    }

    public void setInv_status(String inv_status) {
        this.inv_status = inv_status;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public boolean isIs_host() {
        return is_host;
    }

    public void setIs_host(boolean is_host) {
        this.is_host = is_host;
    }


}
