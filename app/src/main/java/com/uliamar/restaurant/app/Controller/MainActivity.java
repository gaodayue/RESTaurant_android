package com.uliamar.restaurant.app.controller;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.services.RESTClient;

public class MainActivity extends FragmentActivity  implements NFCFragment.OnFragmentInteractionListener{
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    RESTClient restClient;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        restClient = new RESTClient();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.get().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);
    }



    public class DemoCollectionPagerAdapter extends FragmentPagerAdapter {
        private static final String TAG = "DemoCollectionPagerAdapter";

        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment;
            switch (i) {
                case 0:
                    fragment = new RestaurantListFragment();
                    break;
                case 1:
                    fragment = new EventListFragment();
                    break;
                case 2:
                    fragment = new NFCFragment();
                    break;
                default:
                    Log.e(TAG, "Ask for an inexisting tab");
                    fragment = null;
            }

            // Bundle args = new Bundle();
            // Our object is just an integer :-P
            // args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
            // fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Around you";
                case 1:
                    return "Previous event";
                case 2:
                    return "NFCFragment";
                default:
                    return "What ? An other tab ?!";
            }
        }
    }

}


