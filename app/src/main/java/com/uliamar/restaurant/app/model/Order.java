package com.uliamar.restaurant.app.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Pol on 07/05/14.
 */
public class Order {
    private int o_id;
    private Restaurant restaurant;
    private User customer;
    private List<Dishe> dishes;

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getO_id() {
        return o_id;
    }

    public void setO_id(int o_id) {
        this.o_id = o_id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public List<Dishe> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dishe> dishes) {
        this.dishes = dishes;
    }

    public String getSchedule_info() {
        return schedule_info;
    }

    public void setSchedule_info(String schedule_info) {
        this.schedule_info = schedule_info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int[] getCustomer_ids() {
        return customer_ids;
    }

    public void setCustomer_ids(int[] customer_ids) {
        this.customer_ids = customer_ids;
    }

    private int total_price;

    public String getRequest_date() {
        return request_date;
    }

    public void setRequest_date(String request_date) {
        this.request_date = request_date;
    }

    public int getRequest_period() {
        return request_period;
    }

    public void setRequest_period(int request_period) {
        this.request_period = request_period;
    }

    private String request_date;
    //noon|evening|midnight
    private int request_period;
    //No.3 table, 8pm to 10pm, 2014-05-05
    private String schedule_info;
    // planing|booking|booking_succeed|booking_failed|canceled|revoked|consumed
    private String status;
    private String created_time;
    private String updated_time;

    /**
     * Data to feed before saving;
     */

    private int customer_id;
    private int restaurant_id;
    //request date
    //request_period
    private int[] customer_ids;
    //dishes


    public int getID() {
        return o_id;
    }

    public void setID(int ID) {
        this.o_id = ID;
    }

}