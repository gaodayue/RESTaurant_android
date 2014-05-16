package com.uliamar.restaurant.app.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.GetOneInvitationEvent;
import com.uliamar.restaurant.app.Bus.OnOneInvitationEvent;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.Dishe;
import com.uliamar.restaurant.app.model.Invitation;
import com.uliamar.restaurant.app.model.User;

import java.util.List;

public class OrderReviewActivity extends Activity {
    public static final String ARG_INVITATION_ID = "ARG_INVITATION_ID";

    private Context mContext;
    private Invitation mInvitation;
    private int mInvitationID;

    private ProgressDialog progressDialog;
    private TextView mRestaurantName;
    private TextView mCountDownTextView;
    private TextView mDateTextView;
    private TextView mAddressTextView;
    private TextView mFriendListTextView;
    private TextView mDishesListTextView;
    private TextView mTotalPrice;
    private TextView mPricePerParticipant;
    private Button mCancelButton;
    private Button mSendButton;

    public static Intent createIntent(Context c, int invitationID) {
        Intent myIntent = new Intent(c, OrderReviewActivity.class);
        myIntent.putExtra(ARG_INVITATION_ID, invitationID);
        return myIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mInvitationID = getIntent().getIntExtra(ARG_INVITATION_ID, 0);

        setContentView(R.layout.activity_order_review);
        mRestaurantName = (TextView) findViewById(R.id.EventReview_RestaurantName);
        mCountDownTextView = (TextView) findViewById(R.id.EventReview_CountDown);
        Typeface fontRobo = Typeface.createFromAsset(this.getAssets(), "fonts/roboto_thin.ttf");
        mCountDownTextView.setTypeface(fontRobo);
        mDateTextView = (TextView) findViewById(R.id.EventReview_Date);
        mAddressTextView = (TextView) findViewById(R.id.EventReview_address);
        mAddressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + mInvitation.getOrder().getRestaurant().getAddress()));
                mContext.startActivity(i);
            }
        });
        mDishesListTextView = (TextView) findViewById(R.id.EventReview_DishesList);
        mFriendListTextView = (TextView) findViewById(R.id.EventReview_FriendList);
        mTotalPrice = (TextView) findViewById(R.id.EventReview_totalPrice);
        mPricePerParticipant = (TextView) findViewById(R.id.EventReview_pricePerParticipant);
        mCancelButton = (Button) findViewById(R.id.EventReview_CancelButton);
        mSendButton = (Button) findViewById(R.id.EventReview_SendButton);

    }




    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.get().register(this);

        if (mInvitation == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Wait while loading...");
            progressDialog.show();

            BusProvider.get().post(new GetOneInvitationEvent(mInvitationID));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);
    }

    @Subscribe
    public void onOnGetInvitationEvent(OnOneInvitationEvent e) {
        mInvitation = e.get();
        if (mInvitation != null) {
            progressDialog.dismiss();
            mRestaurantName.setText(mInvitation.getOrder().getRestaurant().getName());
            mDateTextView.setText(mInvitation.getOrder().getRequest_date());
            mAddressTextView.setText(mInvitation.getOrder().getRestaurant().getAddress());

            int nbParticipantComming = 0;
            String userListText = "";
            List<User> userList = mInvitation.getParticipants();
            for (int i = 0; i < userList.size(); ++i) {
                if (!userList.get(i).isIs_host()) {
                    userListText += userList.get(i).getName() + " " + userList.get(i).getInv_status() + "\n";
                    if (userList.get(i).getInv_status() == "accepted") {
                        nbParticipantComming += 1;
                    }
                }

            }
            mFriendListTextView.setText(userListText);

            String dishListText = "";
            List<Dishe> disheList = mInvitation.getOrder().getDishes();
            for (int i = 0; i < disheList.size(); ++i) {
                dishListText += disheList.get(i).getName() + " " + disheList.get(i).getQuantity() + " x " + disheList.get(i).getPrice() + "RMB\n";
            }
            mDishesListTextView.setText(dishListText);
            mTotalPrice.setText(mInvitation.getOrder().getTotal_price() + "");
            mPricePerParticipant.setText(( mInvitation.getOrder().getTotal_price() / (nbParticipantComming + 1)) + " per participant") ;

        } else {
            Toast.makeText(this, "Unable to get this Invitation", Toast.LENGTH_SHORT).show();
        }
    }


}
