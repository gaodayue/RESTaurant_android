package com.uliamar.restaurant.app.Bus;

/**
 * Created by Pol on 06/05/14.
 */
public class ChangeInvitationStatus {
    public static enum Status {CANCEL, SEND, ACCEPT, DENY};
    Status invStatus;
    int mInvitationID;

    public ChangeInvitationStatus(int invitationID, Status s) {
        invStatus = s;
        mInvitationID = invitationID;
    }

    public Status getInvStatus() {
        return invStatus;
    }

    public int getInvitationID() {
        return mInvitationID;
    }
}
