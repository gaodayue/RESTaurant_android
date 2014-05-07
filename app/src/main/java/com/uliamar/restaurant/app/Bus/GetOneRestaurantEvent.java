package com.uliamar.restaurant.app.Bus;

/**
 * Created by Pol on 06/05/14.
 */
public class GetOneRestaurantEvent {
    private static Integer mid;

    public  GetOneRestaurantEvent(Integer id) {
        mid = id;
    }

    public int get() {
        return mid;
    }
}
