package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pol on 06/05/14.
 */
public class LocalRestaurantReceivedEvent {
    private List<Restaurant> restaurantList;

    public LocalRestaurantReceivedEvent(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public List<Restaurant> get() {
        return restaurantList;
    }
}
