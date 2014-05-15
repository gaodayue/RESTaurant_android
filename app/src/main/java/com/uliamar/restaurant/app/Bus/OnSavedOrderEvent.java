package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.Invitation;
import com.uliamar.restaurant.app.model.Order;

/**
 * Created by Pol on 07/05/14.
 */
public class OnSavedOrderEvent {
    Invitation mInvitation;

    public OnSavedOrderEvent(Invitation invitation) {
        mInvitation = invitation;
    }

    public Invitation get() {
        return mInvitation;
    }
}
