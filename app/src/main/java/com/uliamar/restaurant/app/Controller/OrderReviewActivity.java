package com.uliamar.restaurant.app.controller;

import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.uliamar.restaurant.app.R;

public class OrderReviewActivity extends ActionBarActivity {
    TextView mCountDownTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_review);
        mCountDownTextView = (TextView) findViewById(R.id.CountDown);
        Typeface fontRobo = Typeface.createFromAsset(this.getAssets(), "fonts/roboto_thin.ttf");
        mCountDownTextView.setTypeface(fontRobo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order2_review, menu);
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
