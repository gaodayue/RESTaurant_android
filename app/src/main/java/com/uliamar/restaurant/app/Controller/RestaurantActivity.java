package com.uliamar.restaurant.app.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.GetOneRestaurantEvent;
import com.uliamar.restaurant.app.Bus.OnOneRestaurantReceivedEvent;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.Restaurant;

public class RestaurantActivity extends Activity {
    public static final String ARG_RESTAURANT_ID = "ARG_RESTAURANT_ID";
    private int mID;
    private RestaurantActivity ref;
    private Restaurant restaurant = null;

    private Button mOrderButton;
    private TextView mRestaurantNameTextView;
    private ProgressDialog progressDialog = null;
private ImageView mRestaurantCoverImageView;

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
                Intent i = OrderEditActivity.createIntent(ref, mID);
                startActivity(i);
            }
        });
        mRestaurantNameTextView = (TextView) findViewById(R.id.restaurant_name);
        mRestaurantCoverImageView = (ImageView) findViewById(R.id.EventReview_Cover);
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

            BusProvider.get().post(new GetOneRestaurantEvent(mID));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);
    }

    @Subscribe
    public void OnOneRestaurantReceivedEvent(OnOneRestaurantReceivedEvent e) {
        if (progressDialog != null){
            progressDialog.hide();
        }
        restaurant = e.get();
        if (restaurant == null) {
            Toast.makeText(this, "Unable to retrieve this restaurant", Toast.LENGTH_SHORT).show();
        } else {
            mRestaurantNameTextView.setText(restaurant.getName());
            if (restaurant.getPic() != null && !restaurant.getPic().isEmpty()) {
                Picasso.with(this).load("http://118.193.54.222" + restaurant.getPic()).placeholder(R.drawable.resto_big).into(mRestaurantCoverImageView);
            }
        }
    }

}
