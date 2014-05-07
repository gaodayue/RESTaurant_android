package com.uliamar.restaurant.app.services;

import com.squareup.otto.Subscribe;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.GetLocalRestaurantEvent;
import com.uliamar.restaurant.app.Bus.GetOneRestaurantEvent;
import com.uliamar.restaurant.app.Bus.GetOrderDatas;
import com.uliamar.restaurant.app.Bus.LocalRestaurantReceivedEvent;
import com.uliamar.restaurant.app.Bus.OnRestaurantDatasReceived;
import com.uliamar.restaurant.app.Bus.OneRestaurantReceivedEvent;
import com.uliamar.restaurant.app.model.Dishe;
import com.uliamar.restaurant.app.model.Friend;
import com.uliamar.restaurant.app.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pol on 06/05/14.
 */
public class RESTClient {
    public RESTClient() {
        BusProvider.get().register(this);
    }

    @Subscribe
    public void onGetLocalRestaurantEvent(GetLocalRestaurantEvent e) {
        List<Restaurant> restaurantList = new ArrayList<Restaurant>();
        restaurantList.add(new Restaurant("Le petit Bouchon", "French food. I mean GOOD food.", "7.42 km away"));
        restaurantList.add(new Restaurant("Le petit Bouchon", "French food. I mean GOOD food.", "7.42 km away"));
        restaurantList.add(new Restaurant("Le petit Bouchon", "French food. I mean GOOD food.", "7.42 km away"));
        restaurantList.add(new Restaurant("Le petit Bouchon", "French food. I mean GOOD food.", "7.42 km away"));
        restaurantList.add(new Restaurant("Le petit Bouchon", "French food. I mean GOOD food.", "7.42 km away"));
        BusProvider.get().post(new LocalRestaurantReceivedEvent(restaurantList));
    }

    @Subscribe
    public void onGetOneRestaurant(GetOneRestaurantEvent e) {
        int restID = e.get();
        BusProvider.get().post(new OneRestaurantReceivedEvent(new Restaurant("MacDo", "French food. I mean GOOD food.", "7.42 km away")));
    }

    @Subscribe
    public void onGetOrderDatas(GetOrderDatas e) {
        int restID = e.get();
        Restaurant r = new Restaurant("MacDo", "French food. I mean GOOD food.", "7.42 km away");
        List<Dishe> dishes = null;
        List<Friend> friends = null;
        BusProvider.get().post(new OnRestaurantDatasReceived(r, dishes, friends));
    }
}
