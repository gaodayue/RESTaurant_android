package com.uliamar.restaurant.app.Bus;

import android.widget.SearchView;

/**
 * Created by Pol on 06/05/14.
 */
public class SearchRestaurantEvent {
    private String mSearchString;

    public SearchRestaurantEvent(String s) {
        mSearchString = s;
    }

    public String get() {
        return mSearchString;
    }
}
