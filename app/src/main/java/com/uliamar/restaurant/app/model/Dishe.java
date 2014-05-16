package com.uliamar.restaurant.app.model;

/**
 * Created by Pol on 07/05/14.
 */
public class Dishe {

    public void setD_name(String d_name) {
        this.d_name = d_name;
        this.name = d_name;
    }

    public int getD_price() {
        return d_price;
    }

    public void setD_price(int d_price) {
        this.d_price = d_price;
        this.price = d_price;
    }

    private String d_name;
    private int d_price;


    public Dishe(){
        this.quantity = 0;
    }
    private int d_id;

    public String getName() {
        return name;
    }
    public String getD_name() {
        return d_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    private String name;
    private int price;
    private int quantity;

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addDish(){
        this.quantity++;
    }
    public void delDish(){
        this.quantity--;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
