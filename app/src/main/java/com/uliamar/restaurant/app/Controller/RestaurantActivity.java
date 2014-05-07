package com.uliamar.restaurant.app.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.GetOneRestaurantEvent;
import com.uliamar.restaurant.app.Bus.OneRestaurantReceivedEvent;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.Restaurant;
import com.uliamar.restaurant.app.services.RESTClient;

public class RestaurantActivity extends Activity {
    private int mID;

    private Button mOrderButton;
    private TextView mRestaurantNameTextView;
    public static final String ARG_RESTAURANT_ID = "ARG_RESTAURANT_ID";

    RestaurantActivity ref;
    RESTClient restClient;

    private Restaurant restaurant = null;
    ProgressDialog progressDialog = null;


    public static Intent createIntent(Context c, int restaurantID) {
        Intent myIntent = new Intent(c, RestaurantActivity.class);
        myIntent.putExtra(ARG_RESTAURANT_ID, restaurantID);
        return myIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mID = getIntent().getIntExtra(ARG_RESTAURANT_ID, 0);
        setContentView(R.layout.activity_restaurant);
        ref = this;
        mOrderButton = (Button) findViewById(R.id.OrderButton);
        mOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ref, OrderEditActivity.class);
                startActivity(i);
            }
        });
        mRestaurantNameTextView = (TextView) findViewById(R.id.restaurant_name);
        restClient = new RESTClient();

    }


    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.get().register(this);

        if (restaurant == null) {
            BusProvider.get().post(new GetOneRestaurantEvent(mID));
            progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);
    }

    @Subscribe
    public void OnOneRestaurantReceivedEvent(OneRestaurantReceivedEvent e) {
        if (progressDialog != null){
            progressDialog.hide();
        }
        restaurant = e.get();
        mRestaurantNameTextView.setText(restaurant.getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.restaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
