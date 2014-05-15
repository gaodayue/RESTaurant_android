package com.uliamar.restaurant.app.controller;


import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.model.Restaurant;
import com.uliamar.restaurant.app.model.User;
import com.uliamar.restaurant.app.services.DataService;
import com.uliamar.restaurant.app.services.RESTrepository;

import java.net.SocketTimeoutException;
import java.util.List;

public class MainActivity extends FragmentActivity  implements NFCFragment.OnFragmentInteractionListener{
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    DataService dataService;
    NfcAdapter nfcAdapter;
    private static final String TAG = "Main activity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
        //        "hwfeocSIPlgKTasIuARPREnS");

        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        dataService = new DataService();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.get().register(this);
        nfcAdapter=NfcAdapter.getDefaultAdapter(this);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())){
            mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager());
            mViewPager = (ViewPager) findViewById(R.id.pager);
            mViewPager.setAdapter(mDemoCollectionPagerAdapter);
            mViewPager.setCurrentItem(2);
            Parcelable[] rawMsgs = getIntent().getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg;
            if(rawMsgs!=null){
                msg=(NdefMessage)rawMsgs[0];
                String tagMsg=new String(msg.getRecords()[0].getPayload());
                int restId=Integer.parseInt(tagMsg.substring(3));
                Intent myIntent=RestaurantActivity.createIntent(this,restId);
                startActivity(myIntent);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE,Menu.FIRST+1,0,"login");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case Menu.FIRST+1:
            {
                Intent myIntent=new Intent(this,LoginActivity.class);
                startActivity(myIntent);
                break;
            }
        }
        return false;
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


