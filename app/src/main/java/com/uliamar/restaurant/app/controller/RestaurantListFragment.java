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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RestaurantAdaptateur mAdapteur;

//    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static RestaurantListFragment newInstance(String param1, String param2) {
        RestaurantListFragment fragment = new RestaurantListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mAdapteur = new RestaurantAdaptateur(getActivity(), inflater);
        // TODO: Change Adapter to display your content
        setListAdapter(mAdapteur);
    }

//
//    List<Restaurant> restaurantList = new ArrayList<Restaurant>();
//    restaurantList.add(new Restaurant("Le petit Bouchon", "French food. I mean GOOD food.", "7.42 km away"));
//    mAdapteur.update(restaurantList);


    @Override
    public void onResume() {
        super.onResume();
        BusProvider.get().register(this);
        BusProvider.get().post(new GetLocalRestaurantEvent());

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
