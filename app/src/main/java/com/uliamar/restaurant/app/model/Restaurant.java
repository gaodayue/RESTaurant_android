package com.uliamar.restaurant.app.model;

import java.util.Collection;
import java.util.List;

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
    private List<Dishe> dishes;
    private float distance;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public class Coord {
        private Float longitude;
        private Float latitude;

        public Float getLongitude() {
            return longitude;
        }

        public void setLongitude(Float longitude) {
            this.longitude = longitude;
        }

        public Float getLatitude() {
            return latitude;
        }

        public void setLatitude(Float latitude) {
            this.latitude = latitude;
        }
    }

    public int getRest_id() {
        return rest_id;
    }

    public void setRest_id(int rest_id) {
        this.rest_id = rest_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Coord getGeo_location() {
        return geo_location;
    }

    public void setGeo_location(Coord geo_location) {
        this.geo_location = geo_location;
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

    public List<Dishe> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dishe> dishes) {
        this.dishes = dishes;
    }
}
