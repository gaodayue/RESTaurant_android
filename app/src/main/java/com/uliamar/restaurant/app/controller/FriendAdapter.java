package com.uliamar.restaurant.app.controller;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.Dishe;
import com.uliamar.restaurant.app.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yamazoa on 2014/5/18.
 */
public class FriendAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    List<User> mFriendList;

    public void update(List<User> friendList) {
        mFriendList = friendList;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView friendName;
        public ImageView friendStatus;
    }

    public FriendAdapter(Context context, LayoutInflater inflater)  {
        mContext = context;
        mInflater = inflater;
        mFriendList = new ArrayList<User>();
    }

    @Override
    public int getCount() {
        return mFriendList.size();
    }

    @Override
    public User getItem(int i) {
        return mFriendList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getCust_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        // check if the view already exists
        // if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.infriends_item_list, null);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            holder.friendName = (TextView) convertView.findViewById(R.id.InFriends_name);
            holder.friendStatus = (ImageView) convertView.findViewById(R.id.InFriends_status);
            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }

        final User user = getItem(position);
        String name = user.getName();
        if (name.length() > 0) {
            name = String.valueOf(name.charAt(0)).toUpperCase() + name.subSequence(1, name.length());
        }
        holder.friendName.setText(name);
        if (user.isIs_host()){
            holder.friendStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.host96));
        }else {
            if (user.getInv_status().equals("accepted")) {
                holder.friendStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.check96));
            }
            else if(user.getInv_status().equals("denied")){
                holder.friendStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.uncheck96));
            }
        }

        return convertView;
    }

}
