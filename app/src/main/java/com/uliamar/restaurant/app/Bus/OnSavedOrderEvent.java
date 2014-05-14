package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.Invitation;
import com.uliamar.restaurant.app.model.Order;

/**
 * Created by Pol on 07/05/14.
 */
public class OnSavedOrderEvent {
    Invitation mInvitation;

    public OnSavedOrderEvent(Invitation mInvitation) {
        mInvitation = mInvitation;
    }

    public Invitation get() {
        return mInvitation;
    }
}
