package com.uliamar.restaurant.app.model;

import java.util.List;

/**
 * Created by Yamazoa on 2014/5/13.
 */
public class Invitation {
    private String iID;
    private Order order;
    private List<Dishe> dishes;
    private List<User> friends;

    public String getiID() {
        return iID;
    }

    public void setiID(String iID) {
        this.iID = iID;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public  void addDishe(Dishe d){

    }

    public List<Dishe> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dishe> dishes) {
        this.dishes = dishes;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void addFriend(User f) {

    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }


    public Invitation(){

    }
}
