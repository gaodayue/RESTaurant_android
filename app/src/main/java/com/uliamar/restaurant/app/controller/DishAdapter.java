package com.uliamar.restaurant.app.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.Dishe;
import com.uliamar.restaurant.app.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yamazoa on 2014/5/17.
 */
public class DishAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    List<Dishe> mDishList;

    public void update(List<Dishe> disheList) {
        mDishList = disheList;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView dishName;
        public TextView dishPrice;
        public TextView dishNum;
        public Button addDishe;
        public Button delDishe;
    }

    public DishAdapter(Context context, LayoutInflater inflater)  {
        mContext = context;
        mInflater = inflater;
        mDishList = new ArrayList<Dishe>();
    }

    @Override
    public int getCount() {
        return mDishList.size();
    }

    @Override
    public Dishe getItem(int i) {
        return mDishList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getD_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        // check if the view already exists
        // if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.dishe_item_list, null);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            holder.dishName = (TextView) convertView.findViewById(R.id.DisheName);
            holder.dishPrice = (TextView) convertView.findViewById(R.id.DishePrice);
            holder.dishNum = (TextView) convertView.findViewById(R.id.textView2);
            holder.addDishe = (Button) convertView.findViewById(R.id.add);
            holder.delDishe = (Button) convertView.findViewById(R.id.delete);


            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }

        final Dishe dish = getItem(position);

        holder.dishName.setText(dish.getName());
        holder.dishPrice.setText(dish.getPrice()+"");
        holder.dishNum.setText(dish.getQuantity() + "");
        holder.addDishe.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                dish.addDish();
                holder.dishNum.setText(dish.getQuantity()+"");
            }
        });
        holder.delDishe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dish.delDish();
                holder.dishNum.setText(dish.getQuantity()+"");
            }
        });

        return convertView;
    }
}
