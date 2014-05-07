package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.Dishe;
import com.uliamar.restaurant.app.model.Friend;
import com.uliamar.restaurant.app.model.Restaurant;

import java.util.List;

/**
 * Created by Pol on 07/05/14.
 */
public class OnRestaurantDatasReceived {
    Restaurant mR;
    List<Dishe> mDishes;
    List<Friend> mFriends;

    public OnRestaurantDatasReceived(Restaurant r, List<Dishe> dishes, List<Friend> friends) {
        mR = r;
        mDishes = dishes;
        mFriends = friends;
    }

    public Restaurant getRestaurant() {
        return mR;
    }


    public List<Dishe> getDishes() {
        return mDishes;
    }


    public List<Friend> getFriends() {
        return mFriends;
    }

}

