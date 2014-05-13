package com.uliamar.restaurant.app.controller;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.model.Restaurant;
import com.uliamar.restaurant.app.model.User;
import com.uliamar.restaurant.app.services.DataService;
import com.uliamar.restaurant.app.services.RESTrepository;

import java.util.List;

public class MainActivity extends FragmentActivity  implements NFCFragment.OnFragmentInteractionListener{
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    DataService dataService;
    private static final String TAG = "Main activity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
                "hwfeocSIPlgKTasIuARPREnS");

        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        dataService = new DataService();

        new DownloadFilesTask().execute();

    }


    private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... voids) {
            try {
                List<Restaurant> rest = RESTrepository.listRestaurant();
                Log.i("asd", rest.size() + "");

            } catch (Exception e) {
                e.getStackTrace();
                Log.v(TAG, e.getMessage());
            }

            return null;
        }

        protected void onProgressUpdate() {
        }

        protected void onPostExecute() {
        }
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


