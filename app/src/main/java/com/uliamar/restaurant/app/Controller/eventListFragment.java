package com.uliamar.restaurant.app.controller;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.GetInvitationList;
import com.uliamar.restaurant.app.Bus.GetLocalRestaurantEvent;
import com.uliamar.restaurant.app.Bus.InvitationListReceivedEvent;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.Invitation;

import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the
 * interface.
 */
public class EventListFragment extends ListFragment {

    private boolean requestPending = false;
    private MySimpleArrayAdapter adapter;
    public final String TAG = "EventListFragment";

//    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static EventListFragment newInstance() {
        EventListFragment fragment = new EventListFragment();

        return fragment;
    }




    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Invitation[] invitations = new Invitation[0];
        adapter = new MySimpleArrayAdapter(getActivity(), invitations);
        setListAdapter(adapter);
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
//        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.get().register(this);
        onAskRefresh();
    }


    private void onAskRefresh() {
        if (!requestPending) {
            BusProvider.get().post(new GetInvitationList());
            Log.d(TAG, "Send GetInvitationList");
            requestPending = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);
    }

    @Subscribe
    public void OnInvitationListReceivedEvent(InvitationListReceivedEvent e) {
        List<Invitation> l = e.get();
        adapter.update(l.toArray(new Invitation[l.size()]));

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(getActivity(), OrderReviewActivity.class);
        startActivity(i);
//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//        }
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }


    public class MySimpleArrayAdapter extends ArrayAdapter<Invitation> {
        private final Context context;
        private  Invitation[] values;

        public MySimpleArrayAdapter(Context context, Invitation[] values) {
            super(context, R.layout.invitation_item_list, values);
            this.context = context;
            this.values = values;
        }

        public void update(Invitation[] values) {
            this.values = values;
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.invitation_item_list, parent, false);

            TextView nameTextView = (TextView) rowView.findViewById(R.id.EventName);
            TextView text1TextView = (TextView) rowView.findViewById(R.id.EventText1);
            TextView text2TextView = (TextView) rowView.findViewById(R.id.EventText2);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            text1TextView.setText(values[position].getOrder().getRestaurant().getName());
            return rowView;
        }
    }

}
