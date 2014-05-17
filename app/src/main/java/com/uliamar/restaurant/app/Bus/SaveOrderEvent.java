package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.Order;
import com.uliamar.restaurant.app.model.OrderSaved;

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

    public OrderSaved getSaved(){
        OrderSaved t = new OrderSaved();
        t.setRestaurant_id(mOrder.getRestaurant_id());
        t.setRequest_date(mOrder.getRequest_date());
        t.setStart_time(mOrder.getStart_time());
        t.setEnd_time(mOrder.getEnd_time());
        t.setCustomer_ids(mOrder.getCustomer_ids());
        t.setDishes(mOrder.getDishes());
        return t;
    }
}
