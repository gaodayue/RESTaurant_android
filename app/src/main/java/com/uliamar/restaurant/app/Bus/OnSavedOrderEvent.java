package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.Order;

/**
 * Created by Pol on 07/05/14.
 */
public class OnSavedOrderEvent {
    Order mOrder;

    public OnSavedOrderEvent(Order order) {
        mOrder = order;
    }

    public Order get() {
        return mOrder;
    }
}
