package com.uliamar.restaurant.app.model;

/**
 * Created by Pol on 05/05/14.
 */
public class Restaurant {
    private String mName;
    private String mDescritpion;
    private String mDistance;

    public Restaurant(String name, String description, String distance) {
        mName = name;
        mDescritpion = description;
        mDistance = distance;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistance(String mDistance) {
        this.mDistance = mDistance;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescritpion() {
        return mDescritpion;
    }

    public void setDescritpion(String mDescritpion) {
        this.mDescritpion = mDescritpion;
    }
}
