package com.uliamar.restaurant.app.services;

import com.uliamar.restaurant.app.model.Restaurant;
import com.uliamar.restaurant.app.model.User;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Pol on 08/05/14.
 */
public class RESTrepository {
    private static  RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("http://118.193.54.222:8888")
            .build();
    private static RESTaurantService restService = restAdapter.create(RESTaurantService.class);


    public List<User>listUsers() {
        return restService.listUsers();
    }

    public List<Restaurant>listRestaurant() {
        return restService.listRestaurants();
    }
}
