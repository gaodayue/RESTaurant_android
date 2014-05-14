package com.uliamar.restaurant.app.services;

import com.uliamar.restaurant.app.model.Invitation;
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

    private static  RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("http://118.193.54.222")
            .build();
    private static RESTaurantService restService = restAdapter.create(RESTaurantService.class);


    public List<User>listUsers() {
        return restService.listUsers();
    }

    public static List<Restaurant>listRestaurant() {
        return restService.listRestaurants();
    }
}
