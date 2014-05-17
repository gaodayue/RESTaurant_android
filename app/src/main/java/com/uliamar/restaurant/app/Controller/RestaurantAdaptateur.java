package com.uliamar.restaurant.app.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pol on 05/05/14.
 */


public class RestaurantAdaptateur extends BaseAdapter {
private String TAG = "RestaurantAdaptateur";
    Context mContext;
    LayoutInflater mInflater;
    List<Restaurant> mRestaurantList;

    public void update(List<Restaurant> restaurantList) {
        mRestaurantList = restaurantList;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public ImageView imageView;
        public TextView restaurantName;
        public TextView restaurantDescritpion;
        public TextView restaurantDistance;
    }

    public RestaurantAdaptateur(Context context, LayoutInflater inflater)  {
        mContext = context;
        mInflater = inflater;
        mRestaurantList = new ArrayList<Restaurant>();
    }

    @Override
    public int getCount() {
        return mRestaurantList.size();
    }

    @Override
    public Restaurant getItem(int i) {
        return mRestaurantList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getRest_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // check if the view already exists
        // if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.restaurantlist_row, null);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.EventReview_Cover);
            holder.restaurantName = (TextView) convertView.findViewById(R.id.restaurant_name);
            holder.restaurantDescritpion = (TextView) convertView.findViewById(R.id.restaurant_desc);
            holder.restaurantDistance = (TextView) convertView.findViewById(R.id.restaurant_distance);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        Restaurant restaurant = getItem(position);
        if (restaurant.getPic_thumb() != null) {
            Picasso.with(mContext).load("http://118.193.54.222" + restaurant.getPic_thumb()).placeholder(R.drawable.resto_small).into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.resto_small);
        }

        holder.restaurantName.setText(restaurant.getName());

        holder.restaurantDescritpion.setText(restaurant.getCategory());
        Float distance = restaurant.getDistance();
        String distanceString;
        if (distance < 1) {
            distance = distance * 1000;
            distanceString = distance.intValue() + "m away";
        } else {
            distanceString = distance.intValue() + "km away";
        }
        holder.restaurantDistance.setText(distanceString);

        return convertView;
    }
}