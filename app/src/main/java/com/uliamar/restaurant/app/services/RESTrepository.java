package com.uliamar.restaurant.app.services;

import android.util.Log;

import com.uliamar.restaurant.app.model.Invitation;
import com.uliamar.restaurant.app.model.LoginResult;
import com.uliamar.restaurant.app.model.Order;
import com.uliamar.restaurant.app.model.Restaurant;
import com.uliamar.restaurant.app.model.User;

import java.util.List;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Field;

/**
 * Created by Pol on 08/05/14.
 */
public  class RESTrepository {

    public static final String ARG_ACCESS_TOKEN = "access_token";
    public static final String ARG_CUSTOMER_ID = "customer_id";
    private static String token = "";
    private static int user_id;

    private static class MyErrorHandler implements ErrorHandler {
        @Override public Throwable handleError(RetrofitError cause) {
            Response r = cause.getResponse();
            Log.e("Retrofit Error", r.getStatus() + " " + cause.getBodyAs(String.class));
            return cause;
        }
    }


    public interface RESTaurantService {
        @GET("/customer/accounts")
        List<User> listUsers(@Query(ARG_CUSTOMER_ID) int customer_id,
                              @Query(ARG_ACCESS_TOKEN) String accesstoken);

        @GET("/restaurants/nearby")
        List<Restaurant> listRestaurants(@Query("longitude") String longitude,
                                         @Query("latitude") String latitude,
                                         @Query(ARG_CUSTOMER_ID) int customer_id,
                                         @Query(ARG_ACCESS_TOKEN) String accesstoken);

        @GET("/restaurants/show/{id}")
        Restaurant GetRestaurant(@Path("id") int id,
                                 @Query(ARG_CUSTOMER_ID) int customer_id,
                                 @Query(ARG_ACCESS_TOKEN) String accesstoken);

        @GET("/invitations/{id}")
        Invitation GetInvitation(@Path("id") int id,
                                 @Query(ARG_CUSTOMER_ID) int customer_id,
                                 @Query(ARG_ACCESS_TOKEN) String accesstoken);

        @POST("/invitations/create")
        Invitation sendOrder(@Body Order order,
                             @Query(ARG_CUSTOMER_ID) int customer_id,
                             @Query(ARG_ACCESS_TOKEN) String accesstoken);

        @FormUrlEncoded
        @POST("/customer/accounts/signin")
        LoginResult login(@Field("phoneno") String phoneno, @Field("password") String password);

        @FormUrlEncoded
        @POST("/push/register")
        String pushRegister(@Field("customer_id") int customer_id, @Field("access_token") String access_token,
                            @Field("push_id") String push_id);

    }

    private static ErrorHandler errorHandler = new MyErrorHandler();

    private static  RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("http://118.193.54.222/api")
            .setErrorHandler(errorHandler)
            .setLogLevel(RestAdapter.LogLevel.FULL)  // Do this for development too.
            .build();

    private static RESTaurantService restService = restAdapter.create(RESTaurantService.class);

    public static List<User> listUsers() {
        return restService.listUsers(user_id, token);
    }
    public static List<Restaurant>listRestaurants(String lon, String lat) {return restService.listRestaurants(lon, lat, user_id, token);}
    public static Restaurant getRestaurant(int id) {return restService.GetRestaurant(id, user_id, token);}
    public static List<User> listUser() { return restService.listUsers(user_id, token);}
    public static Invitation sendOrder(Order order){ return restService.sendOrder(order, user_id, token);}
    public static LoginResult login(String phoneno, String password){
        return restService.login(phoneno, password);
    }
    public static String pushRegister(int customer_id,String access_token,String push_id){
        return restService.pushRegister(customer_id, access_token, push_id);
    }

    public static void setToken(String token) {
        RESTrepository.token = token;
    }

    public static void setUser_id(int user_id) {
        RESTrepository.user_id = user_id;
    }
}
