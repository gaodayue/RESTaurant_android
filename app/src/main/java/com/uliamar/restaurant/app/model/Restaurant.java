package com.uliamar.restaurant.app.model;

import java.util.Collection;

/**
 * Created by Pol on 05/05/14.
 */
public class Restaurant {

    private int rest_id;
    private String name;
    private String address;
    private Coord geo_location;
    private String pic;
    private String pic_thumb;
    private String mgr_id;
    private String mgr_name;

private class Coord {
    private String longitude;
    private String latitude;
}
    private int mID;
    private String mName;
    private String mDescritpion;
    private String mDistance;

    public Restaurant(String name, String description, String distance) {
        mID = 42;
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

    public long getID() {
        return mID;
    }

    public int getRest_id() {
        return rest_id;
    }

    public void setRest_id(int rest_id) {
        this.rest_id = rest_id;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPic_thumb() {
        return pic_thumb;
    }

    public void setPic_thumb(String pic_thumb) {
        this.pic_thumb = pic_thumb;
    }

    public String getMgr_id() {
        return mgr_id;
    }

    public void setMgr_id(String mgr_id) {
        this.mgr_id = mgr_id;
    }

    public String getMgr_name() {
        return mgr_name;
    }

    public void setMgr_name(String mgr_name) {
        this.mgr_name = mgr_name;
    }
}
