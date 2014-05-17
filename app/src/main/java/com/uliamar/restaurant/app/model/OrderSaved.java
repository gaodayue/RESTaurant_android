package com.uliamar.restaurant.app.model;

import java.util.List;

/**
 * Created by Yamazoa on 2014/5/17.
 */
public class OrderSaved {
    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public int[] getCustomer_ids() {
        return customer_ids;
    }

    public void setCustomer_ids(int[] customer_ids) {
        this.customer_ids = customer_ids;
    }

    public List<Dishe> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dishe> dishes) {
        this.dishes = dishes;
    }

    private int restaurant_id;
    private String request_date;
    private int start_time;
    private int end_time;
    private int[] customer_ids;
    private List<Dishe> dishes;
}
