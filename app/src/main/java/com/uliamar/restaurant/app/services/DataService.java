package com.uliamar.restaurant.app.services;

import com.squareup.otto.Subscribe;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.GetLocalRestaurantEvent;
import com.uliamar.restaurant.app.Bus.GetOneRestaurantEvent;
import com.uliamar.restaurant.app.Bus.GetOrderDatasEvent;
import com.uliamar.restaurant.app.Bus.LocalRestaurantReceivedEvent;
import com.uliamar.restaurant.app.Bus.OnOneRestaurantReceivedEvent;
import com.uliamar.restaurant.app.Bus.OnRestaurantDatasReceivedEvent;
import com.uliamar.restaurant.app.Bus.OnSavedOrderEvent;
import com.uliamar.restaurant.app.Bus.SaveOrderEvent;
import com.uliamar.restaurant.app.model.Dishe;
import com.uliamar.restaurant.app.model.User;
import com.uliamar.restaurant.app.model.Order;
import com.uliamar.restaurant.app.model.Restaurant;

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

//        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        List<Restaurant> restaurantList = null; //RESTrepository.listRestaurant();

//        restaurantList.add(new Restaurant("Le petit Bouchon", "French food. I mean GOOD food.", "7.42 km away"));
//        restaurantList.add(new Restaurant("Le petit Bouchon", "French food. I mean GOOD food.", "7.42 km away"));
//        restaurantList.add(new Restaurant("Le petit Bouchon", "French food. I mean GOOD food.", "7.42 km away"));
//        restaurantList.add(new Restaurant("Le petit Bouchon", "French food. I mean GOOD food.", "7.42 km away"));
//        restaurantList.add(new Restaurant("Le petit Bouchon", "French food. I mean GOOD food.", "7.42 km away"));

        BusProvider.get().post(new LocalRestaurantReceivedEvent(restaurantList));
    }

    @Subscribe
    public void onGetOneRestaurant(GetOneRestaurantEvent e) {
        int restID = e.get();
        BusProvider.get().post(new OnOneRestaurantReceivedEvent(new Restaurant("MacDo", "French food. I mean GOOD food.", "7.42 km away")));
    }

    @Subscribe
    public void onGetOrderDatas(GetOrderDatasEvent e) {
        int restID = e.get();
        Restaurant r = new Restaurant("MacDo", "French food. I mean GOOD food.", "7.42 km away");
        List<Dishe> dishes = null;
        List<User> friends = null;
        BusProvider.get().post(new OnRestaurantDatasReceivedEvent(r, dishes, friends));
    }

    @Subscribe
    public void onSaveOrder(SaveOrderEvent e) {
        Order order = e.get();
        /**
         * @to-do save the order on server and retrive the ID
         */
        order.setID(42);
        BusProvider.get().post(new OnSavedOrderEvent(order));
    }


}
