    package com.uliamar.restaurant.app.controller;

    import android.app.ProgressDialog;
    import android.content.Context;
    import android.content.Intent;
    import android.support.v7.app.ActionBarActivity;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.Button;
    import android.widget.Toast;

    import com.squareup.otto.Subscribe;
    import com.uliamar.restaurant.app.Bus.BusProvider;
    import com.uliamar.restaurant.app.Bus.GetOneRestaurantEvent;
    import com.uliamar.restaurant.app.Bus.GetOrderDatas;
    import com.uliamar.restaurant.app.Bus.OnRestaurantDatasReceived;
    import com.uliamar.restaurant.app.R;
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
                    Intent i = new Intent(ref, OrderReviewActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }


        @Override
        protected void onPostResume() {
            super.onPostResume();
            BusProvider.get().register(this);

            if (restaurant == null) {
                progressDialog = new ProgressDialog(ref);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Wait while loading...");
                progressDialog.show();

                BusProvider.get().post(new GetOrderDatas(mRestaurantID));
            }
        }

        @Override
        protected void onPause() {
            super.onPause();
            BusProvider.get().unregister(this);

        }

        @Subscribe
        public void OnRestaurantDatasReceived(OnRestaurantDatasReceived e) {
            progressDialog.dismiss();
            Toast.makeText(this, "Datas received", Toast.LENGTH_SHORT).show();
        }
    }
