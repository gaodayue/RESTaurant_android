package com.uliamar.restaurant.app.model;

import java.util.Date;

/**
 * Created by Pol on 07/05/14.
 */
public class Order {
    private int mID;
    private Date date;
    private String period;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }


    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        this.mID = ID;
    }
}
