package com.uliamar.restaurant.app.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.squareup.otto.Subscribe;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.GetLocalRestaurantEvent;
import com.uliamar.restaurant.app.Bus.LocalRestaurantReceivedEvent;


/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class RestaurantListFragment extends ListFragment {


    public static String ARG_USER_ID = "arg_userID";
    private RestaurantAdaptateur mAdapteur;
    private int mUserID;
    private Boolean requestPending =false;

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

        if (getArguments() != null) {
            mUserID = getArguments().getInt(ARG_USER_ID);
        }

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAdapteur = new RestaurantAdaptateur(getActivity(), inflater);
        setListAdapter(mAdapteur);
    }


    @Override
    public void onResume() {
        super.onResume();
        BusProvider.get().register(this);
        if (!requestPending) {
            BusProvider.get().post(new GetLocalRestaurantEvent());
            requestPending = true;
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
        mAdapteur.update(event.get());

    }
}
