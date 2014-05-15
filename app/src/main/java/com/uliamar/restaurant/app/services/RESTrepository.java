package com.uliamar.restaurant.app.services;

import android.util.Log;

import com.uliamar.restaurant.app.model.Invitation;
import com.uliamar.restaurant.app.model.Order;
import com.uliamar.restaurant.app.model.Restaurant;
import com.uliamar.restaurant.app.model.User;

import java.lang.reflect.Type;
import java.util.List;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Pol on 08/05/14.
 */
public  class RESTrepository {

    private static class MyErrorHandler implements ErrorHandler {
        @Override public Throwable handleError(RetrofitError cause) {
            Response r = cause.getResponse();
            Log.e("Retrofit Error", r.getStatus() + " " + cause.getBodyAs(String.class));
            return cause;
        }
    }


    public interface RESTaurantService {
        @GET("/customer/accounts")
        List<User> listUsers();

        @GET("/restaurants/nearby")
        List<Restaurant> listRestaurants(@Query("longitude") String longitude, @Query("latitude") String latitude);

        @GET("/restaurants/show/{id}")
        Restaurant GetRestaurant(@Path("id") int id);

        @GET("/invitations/{id}")
        Invitation GetInvitation(@Path("id") int id);

        @POST("/invitations/create")
        Invitation sendOrder(@Body Order order);

    }

    private static ErrorHandler errorHandler = new MyErrorHandler();

    private static  RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("http://118.193.54.222/api")
            .setErrorHandler(errorHandler)
            .setLogLevel(RestAdapter.LogLevel.FULL)  // Do this for development too.
            .build();

    private static RESTaurantService restService = restAdapter.create(RESTaurantService.class);

    public static List<User> listUsers() {
        return restService.listUsers();
    }
    public static List<Restaurant>listRestaurants(String lon, String lat) {return restService.listRestaurants(lon, lat);}
    public static Restaurant getRestaurant(int id) {return restService.GetRestaurant(id);}
    public static List<User> listUser() { return restService.listUsers();}
    public static Invitation sendOrder(Order order){ return restService.sendOrder(order);}
}
