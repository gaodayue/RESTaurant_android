package com.uliamar.restaurant.app.model;

/**
 * Created by Pol on 07/05/14.
 */
public class Dishe {

    private int d_id;

    private String d_name;
    private int d_price;
    private int quantity;


    public Dishe(){
        this.quantity = 0;
    }

    public String getD_name() {
        return d_name;
    }

    public void setD_name(String d_name) {
        this.d_name = d_name;
    }

    public int getD_price() {
        return d_price;
    }

    public void setD_price(int d_price) {
        this.d_price = d_price;
    }


    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
