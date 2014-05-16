package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.Order;

/**
 * Created by Pol on 07/05/14.
 */
public class SaveOrderEvent {
    Order mOrder;

    public SaveOrderEvent(Order order) {
        mOrder = order;
    }

    public Order get() {
        return mOrder;
    }
}
