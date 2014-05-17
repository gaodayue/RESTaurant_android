package com.uliamar.restaurant.app.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.GetLocalRestaurantEvent;
import com.uliamar.restaurant.app.Bus.LocalRestaurantReceivedEvent;
import com.uliamar.restaurant.app.Bus.SearchRestaurantEvent;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.Restaurant;

import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class RestaurantListFragment extends ListFragment {

    private String TAG = "ResturautlistFragemnt";
    public static String ARG_USER_ID = "arg_userID";
    private RestaurantAdaptateur mAdapteur;
    private int mUserID;
    private Boolean requestPending = false;
    private List<Restaurant> restaurants;
    private ProgressDialog progressDialog;
    private Boolean searchPEnding = false;

    private SearchView searchView;
//    private OnFragmentInteractionListener mListener;

    public static RestaurantListFragment newInstance(int userID) {
        RestaurantListFragment fragment = new RestaurantListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RestaurantListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mUserID = getArguments().getInt(ARG_USER_ID);
        }
        progressDialog = new ProgressDialog(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAdapteur = new RestaurantAdaptateur(getActivity(), inflater);
        setListAdapter(mAdapteur);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.restaurant_list_fragment, null);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        if (listView == null) {
            Log.d(TAG, "listView Null");
        }
        searchView = new SearchView(getActivity());
        //  searchView.setLayoutParams(new SearchView.LayoutParams(SearchView.LayoutParams.MATCH_PARENT, SearchView.LayoutParams.WRAP_CONTENT));
//        searchView = (SearchView) view.findViewById(R.id.restaurantList_searchview);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.v(TAG, "We submitted" + s);
                progressDialog.setMessage("Searching...");
                progressDialog.show();
                BusProvider.get().post(new SearchRestaurantEvent(s));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.v(TAG, "onQueryTextChange" + s);
                if (s.equals("")) {
                    searchPEnding = false;
                    BusProvider.get().post(new GetLocalRestaurantEvent());
                } else {
                    searchPEnding = true;
                }
                return true;
            }
        });
        listView.addHeaderView(searchView);
        return view;
    }

    private void onAskRefresh() {
        if (!requestPending) {
            BusProvider.get().post(new GetLocalRestaurantEvent());
            requestPending = true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.get().register(this);
        if (!searchPEnding) {
            onAskRefresh();
        }
    }



    @Override
    public void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //  mListener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent myIntent = RestaurantActivity.createIntent(getActivity(), (int) id);
        startActivity(myIntent);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(String id);
//    }

    @Subscribe
    public void  onLocalRestaurantReceived(LocalRestaurantReceivedEvent event) {
        restaurants = event.get();
        requestPending = false;
        progressDialog.hide();
        if (restaurants == null) {
            Toast.makeText(getActivity(), "Unable to retrieve the nearby restaurants", Toast.LENGTH_SHORT).show();
        } else {
            mAdapteur.update(restaurants);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.restaurant_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(TAG, "Got a click on topBarItem");
        switch (item.getItemId()) {
            case R.id.refresh_restaurant_list_actionbar_item: {
                onAskRefresh();
                return true;
            }
            case R.id.map_restaurant_list_actionbar_item : {
                if (restaurants == null) {
                    Toast.makeText(getActivity(), "No restaurant to display", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = MapActivity.createIntent(getActivity(), restaurants);
                    startActivity(i);
                    return true;
                }
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
