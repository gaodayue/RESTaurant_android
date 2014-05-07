package com.uliamar.restaurant.app.Bus;

/**
 * Created by Pol on 07/05/14.
 */
public class GetOrderDatasEvent {
    int mID;
    public GetOrderDatasEvent(int i) {
        mID = i;
    }

    public int get() {
        return mID;
    }
}
