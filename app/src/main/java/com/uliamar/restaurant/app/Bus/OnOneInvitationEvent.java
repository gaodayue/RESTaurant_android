package com.uliamar.restaurant.app.Bus;

import com.uliamar.restaurant.app.model.Invitation;
import com.uliamar.restaurant.app.model.Restaurant;

/**
 * Created by Pol on 06/05/14.
 */
public class OnOneInvitationEvent {
    private Invitation invitation;

    public OnOneInvitationEvent(Invitation exInvitation) {
        this.invitation = exInvitation;
    }

    public Invitation get() {
        return invitation;
    }
}
