package com.uliamar.restaurant.app.services;

import android.util.Log;

import com.uliamar.restaurant.app.model.Invitation;
import com.uliamar.restaurant.app.model.LoginResult;
import com.uliamar.restaurant.app.model.Order;
import com.uliamar.restaurant.app.model.OrderSaved;
import com.uliamar.restaurant.app.model.Restaurant;
import com.uliamar.restaurant.app.model.User;

import java.util.List;

import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Field;
import com.google.gson.Gson;


/**
 * Created by Pol on 08/05/14.
 */
public  class RESTrepository {

    public static final String ARG_ACCESS_TOKEN = "access_token";
    public static final String ARG_CUSTOMER_ID = "customer_id";
    public static final String ARG_CONTENT = "content";
    private static String mToken = "";
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
        List<User> listUsers();

        @GET("/restaurants/nearby")
        List<Restaurant> listRestaurants(@Query("latitude") String latitude, @Query("longitude") String longitude);

        @GET("/restaurants/show/{id}")
        Restaurant GetRestaurant(@Path("id") int id);

        @GET("/invitations/{id}")
        Invitation getInvitation(@Path("id") int id);

        @POST("/invitations/create")
        Invitation sendOrder(@Body Order order);


        @GET("/invitations/")
        List<Invitation> getInvitations();


        @FormUrlEncoded
        @POST("/customer/accounts/signin")
        LoginResult login(@Field("phoneno") String phoneno, @Field("password") String password);

        @FormUrlEncoded
        @POST("/push/register")
        String pushRegister(@Field("push_id") String push_id);

        @POST("/invitations/book/{id}")
        Invitation bookInvitation(@Path("id") int id);

        @POST("/invitations/cancel/{id}")
        Invitation cancelInvitation(@Path("id") int id);

        @POST("/invitations/accept/{id}")
        Invitation acceptInvitation(@Path("id") int id);

        @POST("/invitations/deny/{id}")
        Invitation denyInvitation(@Path("id") int id);

        @GET("/restaurants/search")
        List<Restaurant> searchRestaurant(@Query("keyword") String search);

    }

    private static ErrorHandler errorHandler = new MyErrorHandler();

    public static class TokenSetterInterceptor implements RequestInterceptor {
        private String token = "";

        public void setToken(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        @Override
        public void intercept(RequestFacade request) {
            if (token.length() != 0) {
                request.addHeader("Authorization", "Bearer " + token);
            }
        }
    }



    private static TokenSetterInterceptor requestInterceptor = new TokenSetterInterceptor();



    private static  RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("http://118.193.54.222/api")
            .setErrorHandler(errorHandler)
            .setRequestInterceptor(requestInterceptor)
            .setLogLevel(RestAdapter.LogLevel.FULL)  // Do this for development too.
            .build();

    private static RESTaurantService restService = restAdapter.create(RESTaurantService.class);

    public static List<Restaurant>listRestaurants(String lat, String lon) {return restService.listRestaurants(lat, lon);}
    public static Restaurant getRestaurant(int id) {return restService.GetRestaurant(id);}
    public static List<User> listUsers() { return restService.listUsers();}
    public static Invitation sendOrder(Order order){ return restService.sendOrder(order);}
    public static LoginResult login(String phoneno, String password){ return restService.login(phoneno, password);}
    public static String pushRegister(int customer_id,String access_token,String push_id){
        return restService.pushRegister(push_id);
    }
    public static Invitation getInvitation(int id) {return restService.getInvitation(id);}

    public static void setToken(String token) {
        requestInterceptor.setToken(token);
        mToken = token;
    }

    public static void setUser_id(int user_id) {
        RESTrepository.user_id = user_id;
    }
    public static List<Invitation>getInvitations() {return restService.getInvitations();}
    public static Invitation bookInvitation(int id) {return restService.bookInvitation(id);}
    public static Invitation cancelInvitation(int id) {return restService.cancelInvitation(id);}
    public static Invitation acceptInvitation(int id) {return restService.acceptInvitation(id);}
    public static Invitation denyInvitation(int id) {return restService.denyInvitation(id);}
    public static List<Restaurant> searchRestaurant(String search) {return restService.searchRestaurant(search);}

}
