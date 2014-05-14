package com.uliamar.restaurant.app.model;

import java.util.List;

/**
 * Created by Yamazoa on 2014/5/13.
 */
public class Invitation {
    private int inv_id;
    private String created_time;
    private List<User>  participants;
    private String pic = "";

    private Order order;


    public int getiID() {
        return inv_id;
    }

}
