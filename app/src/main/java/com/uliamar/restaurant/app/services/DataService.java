package com.uliamar.restaurant.app.services;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.otto.Subscribe;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.GetLocalRestaurantEvent;
import com.uliamar.restaurant.app.Bus.GetOneRestaurantEvent;
import com.uliamar.restaurant.app.Bus.GetOrderDatasEvent;
import com.uliamar.restaurant.app.Bus.LocalRestaurantReceivedEvent;
import com.uliamar.restaurant.app.Bus.LoginEvent;
import com.uliamar.restaurant.app.Bus.LoginSuccessEvent;
import com.uliamar.restaurant.app.Bus.OnOneRestaurantReceivedEvent;
import com.uliamar.restaurant.app.Bus.OnRestaurantDatasReceivedEvent;
import com.uliamar.restaurant.app.Bus.OnSavedOrderEvent;
import com.uliamar.restaurant.app.Bus.SaveOrderEvent;
import com.uliamar.restaurant.app.model.Dishe;
import com.uliamar.restaurant.app.model.Invitation;
import com.uliamar.restaurant.app.model.LoginResult;
import com.uliamar.restaurant.app.model.User;
import com.uliamar.restaurant.app.model.Order;
import com.uliamar.restaurant.app.model.Restaurant;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pol on 06/05/14.
 */
public class DataService {

    public DataService() {
        BusProvider.get().register(this);
    }

    @Subscribe
    public void onGetLocalRestaurantEvent(GetLocalRestaurantEvent e) {
        new  AsyncTask<Void, Void,  List<Restaurant>>() {
            private String TAG = "listRestaurant asyncTask";

            protected List<Restaurant> doInBackground(Void... voids) {
                try {
                    List<Restaurant> rest = RESTrepository.listRestaurants("55", "66");

                    Log.i("asd", rest.size() + "");
                    return rest;
                } catch (Exception e) {
                    if (e instanceof SocketTimeoutException) {
                        Log.e(TAG, "Timeout");
                    } else {
                        Log.e(TAG, "Unable to retrive restaurants " +  e.getClass().getName() + " cause " + e.getMessage() );


                    }
                    e.getStackTrace();
                }

                return new ArrayList<Restaurant>();
            }

            protected void onProgressUpdate() {

            }

            protected void onPostExecute(List<Restaurant> rest) {
                BusProvider.get().post(new LocalRestaurantReceivedEvent(rest));

            }
        }.execute();
    }

    @Subscribe
    public void onGetOneRestaurant(GetOneRestaurantEvent e) {
        final int restID = e.get();

        new  AsyncTask<Void, Void,  Restaurant>() {
            private String TAG = "listRestaurant asyncTask";

            protected Restaurant doInBackground(Void... voids) {
                try {
                    Restaurant rest = RESTrepository.getRestaurant(restID);
                    return rest;
                } catch (Exception e) {
                    if (e instanceof SocketTimeoutException) {
                        Log.e(TAG, "Timeout");
                    } else {
                        Log.e(TAG, "Unable to retrive restaurant " +  e.getCause());

                    }
                    e.getStackTrace();
                    Log.v(TAG, e.getMessage());
                }

                return null;
            }

            protected void onProgressUpdate() {

            }

            protected void onPostExecute(Restaurant rest) {
                BusProvider.get().post(new OnOneRestaurantReceivedEvent(rest));

            }
        }.execute();
    }

    @Subscribe
    public void onGetOrderDatas(GetOrderDatasEvent e) {
        final int restID = e.get();

        new  AsyncTask<Void, Void,  OnRestaurantDatasReceivedEvent>() {
            private String TAG = "listRestaurant asyncTask";

            protected OnRestaurantDatasReceivedEvent doInBackground(Void... voids) {
                try {
                    Restaurant rest = RESTrepository.getRestaurant(restID);
                    List<User> friends = RESTrepository.listUser();
                    return  new OnRestaurantDatasReceivedEvent(rest, friends);
                } catch (Exception e) {
                    if (e instanceof SocketTimeoutException) {
                        Log.e(TAG, "Timeout");
                    } else {
                        Log.e(TAG, "Unable to retrive restaurant " +  e.getCause());

                    }
                    e.getStackTrace();
                    Log.v(TAG, e.getMessage());
                }

                return null;
            }

            protected void onProgressUpdate() {

            }

            protected void onPostExecute(OnRestaurantDatasReceivedEvent e) {
                BusProvider.get().post(e);
            }
        }.execute();
    }

    @Subscribe
    public void onSaveOrder(SaveOrderEvent e) {
        final Order order = e.get();
        new  AsyncTask<Void, Void,  OnSavedOrderEvent>() {
            private String TAG = "listRestaurant asyncTask";

            protected OnSavedOrderEvent doInBackground(Void... voids) {
                try {
                    Invitation invitation = RESTrepository.sendOrder(order);
                    return new OnSavedOrderEvent(invitation);
                } catch (Exception e) {
                    if (e instanceof SocketTimeoutException) {
                        Log.e(TAG, "Timeout");
                    } else {
                        Log.e(TAG, "Unable to retrive restaurant " +  e.getCause());

                    }
                    e.getStackTrace();
                    Log.v(TAG, e.getMessage());
                }

                return null;
            }

            protected void onProgressUpdate() {

            }

            protected void onPostExecute(OnSavedOrderEvent e) {
                BusProvider.get().post(e);
            }
        }.execute();

    }

    @Subscribe
    public void onLoginEvent(LoginEvent loginEvent){
        final String phoneno=loginEvent.getPhoneno();
        final String password=loginEvent.getPassWord();
        new AsyncTask<Void,Void,LoginResult>(){
            protected LoginResult doInBackground(Void...voids){
                try{
                    LoginResult result=RESTrepository.login(phoneno,password);
                    return result;
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
            protected void onProgressUpdate(){}

            protected void onPostExecute(LoginResult result){
                Log.i("bigred","login success");
                LoginSuccessEvent loginSuccessEvent=new LoginSuccessEvent(result);
                BusProvider.get().post(loginSuccessEvent);
            }
        }.execute();


    }

}
