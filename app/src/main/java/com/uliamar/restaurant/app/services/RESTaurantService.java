package com.uliamar.restaurant.app.services;

import com.uliamar.restaurant.app.model.Invitation;
import com.uliamar.restaurant.app.model.Restaurant;
import com.uliamar.restaurant.app.model.User;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Pol on 08/05/14.
 */
public interface RESTaurantService {
        @GET("/customer/accounts")
        List<User> listUsers();

        @GET("/restaurants/nearby")
        List<Restaurant> listRestaurants();


        @GET("/restaurants/show/{id}")
        List<Restaurant> GetRestaurants(@Path("id") int id);

        @GET("invitations/{id}")
        Invitation GetInvitation(@Path("id") int id);

}
