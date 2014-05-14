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
    private int total_price;
    private String request_date;
    //noon|evening|midnight
    private String request_period;
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