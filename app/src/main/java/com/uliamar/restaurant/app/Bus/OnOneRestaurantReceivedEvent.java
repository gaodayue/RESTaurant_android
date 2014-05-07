package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.Restaurant;

import java.util.List;

/**
 * Created by Pol on 06/05/14.
 */
public class OnOneRestaurantReceivedEvent {
    private Restaurant restaurant;

    public OnOneRestaurantReceivedEvent(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant get() {
        return restaurant;
    }
}
