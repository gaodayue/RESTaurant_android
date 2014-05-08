package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.Dishe;
import com.uliamar.restaurant.app.model.User;
import com.uliamar.restaurant.app.model.Restaurant;

import java.util.List;

/**
 * Created by Pol on 07/05/14.
 */
public class OnRestaurantDatasReceivedEvent {
    Restaurant mR;
    List<Dishe> mDishes;
    List<User> mUsers;

    public OnRestaurantDatasReceivedEvent(Restaurant r, List<Dishe> dishes, List<User> friends) {
        mR = r;
        mDishes = dishes;
        mUsers = friends;
    }

    public Restaurant getRestaurant() {
        return mR;
    }


    public List<Dishe> getDishes() {
        return mDishes;
    }


    public List<User> getFriends() {
        return mUsers;
    }

}

