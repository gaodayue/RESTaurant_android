package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.Invitation;
import com.uliamar.restaurant.app.model.Restaurant;

import java.util.List;

/**
 * Created by Pol on 06/05/14.
 */
public class InvitationListReceivedEvent {
    private List<Invitation> restaurantList;

    public InvitationListReceivedEvent(List<Invitation> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public List<Invitation> get() {
        return restaurantList;
    }
}
