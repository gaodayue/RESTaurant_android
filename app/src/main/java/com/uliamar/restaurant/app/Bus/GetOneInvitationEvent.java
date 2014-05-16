package com.uliamar.restaurant.app.Bus;

/**
 * Created by Pol on 06/05/14.
 */
public class GetOneInvitationEvent {
    private static Integer mid;

    public GetOneInvitationEvent(Integer id) {
        mid = id;
    }

    public int get() {
        return mid;
    }
}
