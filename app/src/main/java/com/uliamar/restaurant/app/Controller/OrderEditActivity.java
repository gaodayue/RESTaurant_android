package com.uliamar.restaurant.app.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.GetOrderDatasEvent;
import com.uliamar.restaurant.app.Bus.OnRestaurantDatasReceivedEvent;
import com.uliamar.restaurant.app.Bus.OnSavedOrderEvent;
import com.uliamar.restaurant.app.Bus.SaveOrderEvent;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.Order;
import com.uliamar.restaurant.app.model.Restaurant;

public class OrderEditActivity extends ActionBarActivity {
    Button mSendInvitationButton;
    OrderEditActivity ref;
    ProgressDialog progressDialog;
    Restaurant restaurant;
    private int mRestaurantID;

    public static final String ARG_RESTAURANT_ID = "ARG_RESTAURANT_ID";


    public static Intent createIntent(Context c, int restaurantID) {
        Intent myIntent = new Intent(c, OrderEditActivity.class);
        myIntent.putExtra(ARG_RESTAURANT_ID, restaurantID);
        return myIntent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_edit);
        ref = this;
        mRestaurantID = getIntent().getIntExtra(ARG_RESTAURANT_ID, 0);

        mSendInvitationButton = (Button) findViewById(R.id.SendActivityButton);
        mSendInvitationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * @To-DO: retrieve value of the UI and fill the order object;
                 */
                Order order = new Order();
                progressDialog = new ProgressDialog(ref);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Wait while loading...");
                progressDialog.show();
                BusProvider.get().post(new SaveOrderEvent(order));

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.get().register(this);

        if (restaurant == null) {
            progressDialog = new ProgressDialog(ref);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Wait while loading...");
            progressDialog.show();

            BusProvider.get().post(new GetOrderDatasEvent(mRestaurantID));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);

    }

    @Subscribe
    public void OnRestaurantDatasReceived(OnRestaurantDatasReceivedEvent e) {
        progressDialog.dismiss();
        Toast.makeText(this, "Datas received", Toast.LENGTH_SHORT).show();
    }

    @Subscribe
    public void OnSavedOrderEvent(OnSavedOrderEvent e) {
        progressDialog.dismiss();
        Intent i = OrderReviewActivity.createIntent(this, e.get().getID());
        startActivity(i);
        finish();
    }

}
