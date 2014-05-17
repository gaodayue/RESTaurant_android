package com.uliamar.restaurant.app.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.GetOrderDatasEvent;
import com.uliamar.restaurant.app.Bus.OnRestaurantDatasReceivedEvent;
import com.uliamar.restaurant.app.Bus.OnSavedOrderEvent;
import com.uliamar.restaurant.app.Bus.SaveOrderEvent;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.Dishe;
import com.uliamar.restaurant.app.model.Order;
import com.uliamar.restaurant.app.model.Restaurant;
import com.uliamar.restaurant.app.model.User;
import com.uliamar.restaurant.app.services.DataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderEditActivity extends ActionBarActivity {

    DataService dataService;
    public static final String PREF_ACCOUNT_ID = "cust_id";
    private String SHARED_PREF_DB_NAME = "loginResult";

    Button mSendInvitationButton;
    Button mAddFriend;
    Button mDelFriend;
    Button deleteDish;
    OrderEditActivity ref;
    ProgressDialog progressDialog;
    Restaurant restaurant;
    Spinner start;
    Spinner end;
    DatePicker mDate;
    ListView mDishes;
    DishAdapter mDishAdapter;
    TextView friendList;
    private int mRestaurantID;
    private List<String> list = new ArrayList<String>();
    private List<String> list2 = new ArrayList<String>();
    private List<Dishe> dishes = new ArrayList<Dishe>();
    private List<User> friends = new ArrayList<User>();
    private List<User> inFriends = new ArrayList<User>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter2;
    private int cust_id;
    private Order order;
    private ImageView mCoverImageView;
    private TextView mRestaurantName;

    public static final String ARG_RESTAURANT_ID = "ARG_RESTAURANT_ID";


    public static Intent createIntent(Context c, int restaurantID) {
        Intent myIntent = new Intent(c, OrderEditActivity.class);
        myIntent.putExtra(ARG_RESTAURANT_ID, restaurantID);
        return myIntent;
    }

    private void init(){
        order = new Order();
        for (int i=11;i<24;i++){
            list.add(i+"");
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (int i=1;i<4;i++){
            list2.add(i+"");
        }
        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SharedPreferences preferences=this.getSharedPreferences(SHARED_PREF_DB_NAME, MODE_PRIVATE);
        cust_id = preferences.getInt(PREF_ACCOUNT_ID, 1);
        order.setRequest_date("2014-05-18");
        order.setStart_time(11);
        order.setEnd_time(13);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_order_edit);
        ref = this;
        mRestaurantID = getIntent().getIntExtra(ARG_RESTAURANT_ID, 0);

        start = (Spinner) findViewById(R.id.spinner);
        end = (Spinner) findViewById(R.id.spinner2);
        mRestaurantName = (TextView) findViewById(R.id.EventEdit_RestaurantName);
        mCoverImageView = (ImageView) findViewById(R.id.EventEdit_Cover);

        start.setAdapter(adapter);
        end.setAdapter(adapter2);
        /**
         *      Caused by: java.lang.RuntimeException: setOnItemClickListener cannot be used with a spinner.
         */
        start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //order.setRequest_date("noon");
                //order.setStart_time(position+11);
                if ((position+11+end.getSelectedItemPosition()+1) <=24 ){
                    order.setStart_time(position+11);
                    order.setEnd_time(end.getSelectedItemPosition()+1);
                }else {
                    Toast.makeText(getApplicationContext(), "You need to choose right time!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        end.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //order.setRequest_date("noon");
                if (order.getStart_time()+position+1 <= 24) {
                    order.setEnd_time(order.getStart_time() + position + 1);
                }else {
                    Toast.makeText(getApplicationContext(), "You need to choose right time!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        mDate = (DatePicker) findViewById(R.id.datePicker);
        mDate.init(2014,4,18,new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                order.setRequest_date(i + "-" + i2 + "-" + i3);
                //order.setDate(date);
                System.out.println("Date: " +i+"-"+(i2+1)+"-"+i3);
            }
        });

        mDishes = (ListView) findViewById(R.id.DishList);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDishAdapter = new DishAdapter(this, inflater);
        mDishes.setAdapter(mDishAdapter);
        /*
        mDishes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                TextView tmp = (TextView)arg1.findViewById(R.id.textView2);
                int i = Integer.parseInt(tmp.getText().toString());
                i++;
                tmp.setText(i+"");
                dishes.get(arg2).addDish();
            }
        });
        */
        deleteDish = (Button) mDishes.findViewById(R.id.delete);

        friendList = (TextView) findViewById(R.id.friendList);
        mAddFriend = (Button) findViewById(R.id.addFriend);
        mAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final String[] itemStrings={"AA","BB","CC","DD"};
                final String[] itemStrings = new String[friends.size()];
                for(int i=0;i<friends.size();i++){
                    itemStrings[i] = friends.get(i).getName();
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(OrderEditActivity.this);
                builder.setTitle("FriendLIST").setItems(itemStrings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(getApplicationContext(), "你点击的是" + itemStrings[which], Toast.LENGTH_LONG).show();
                        String tmp = friendList.getText().toString();
                        tmp += itemStrings[which] + ",";
                        friendList.setText(tmp);
                        User t = friends.get(which);
                        inFriends.add(t);
                        friends.remove(which);
                    }
                }).create().show();

            }
        });

        mDelFriend = (Button) findViewById(R.id.delFriend);
        mDelFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final String[] itemStrings={"AA","BB","CC","DD"};
                final String[] itemStrings2 = new String[inFriends.size()];
                //System.out.println("in delfriends: " + inFriends.size());
                for(int i=0;i<inFriends.size();i++){
                    itemStrings2[i] = inFriends.get(i).getName();
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(OrderEditActivity.this);
                builder.setTitle("inFriendLIST").setItems(itemStrings2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (which == 0)
                            Toast.makeText(getApplicationContext(), "Don't delete yourself!", Toast.LENGTH_LONG).show();
                        //String tmp = friendList.getText().toString();
                        //tmp += itemStrings[which] + ",";
                        //friendList.setText(tmp);
                        //inFriends.add(friends.get(which));
                        //friends.remove(which);
                        else {
                            friends.add(inFriends.get(which));
                            inFriends.remove(which);
                            String tmp = "";
                            for (int i = 0; i < inFriends.size(); i++) {
                                tmp += inFriends.get(i).getName() + ",";
                            }
                            friendList.setText(tmp);
                        }
                    }
                }).create().show();

            }
        });

        mSendInvitationButton = (Button) findViewById(R.id.SendActivityButton);
        mSendInvitationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * @To-DO: retrieve value of the UI and fill the order object;
                 */
                //Order order = new Order();
                //mDate.

                /**
                 {
                 ""customer_id"": 1,
                 ""restaurant_id"": 00001,
                 ""request_date"": ""2014-05-05"",
                 ""request_period"": ""noon|evening|midnight"",
                 ""customer_ids"": [1,2], // include host
                 ""dishes"": [
                 {
                 ""d_id"": 101,
                 ""name"": ""Big Mac"",
                 ""price"": ""18"",
                 ""quantity"": 3
                 },
                 // other ordered dish object
                 ]
                 }"
                 */

                order.setCustomer_id(cust_id);
                order.setRestaurant_id(mRestaurantID);
                //order.setRequest_date("2014-05-05");
                //order.setRequest_period("1");
                int[] l = new int[inFriends.size()];
                for (int i=0;i<inFriends.size();i++){
                    l[i] = inFriends.get(i).getCust_id();
                }
                order.setCustomer_ids(l);
                List<Dishe> dl = new ArrayList<Dishe>();
                for (int i=0;i<dishes.size();i++){
                    if (dishes.get(i).getQuantity() > 0){
                        dl.add(dishes.get(i));
                    }
                }
                if (dl.size() == 0){
                    new AlertDialog.Builder(OrderEditActivity.this).setMessage("A meal without dishes is like santa claus without his hod and his BAALLS").setPositiveButton("OK",null).show();

                    return;
                }
                order.setDishes(dl);

                progressDialog = new ProgressDialog(ref);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Wait while loading...");
                progressDialog.show();
                BusProvider.get().post(new SaveOrderEvent(order));
                System.out.println(new Gson().toJson(order));

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.get().register(this);

        if (restaurant == null) {
            progressDialog = new ProgressDialog(ref);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Wait while loading...");
            progressDialog.show();

            BusProvider.get().post(new GetOrderDatasEvent(mRestaurantID));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);
    }

    @Subscribe
    public void OnRestaurantDatasReceived(OnRestaurantDatasReceivedEvent e) {
        progressDialog.hide();

        restaurant = e.getRestaurant();
        friends = e.getFriends();
        dishes = restaurant.getDishes();

        if (restaurant != null && friends != null && dishes != null) {
            if (restaurant.getPic() != null && !restaurant.getPic().isEmpty()) {
                Picasso.with(this).load("http://118.193.54.222" + restaurant.getPic()).placeholder(R.drawable.resto_big).into(mCoverImageView);
            }
            mRestaurantName.setText(restaurant.getName());

            if (inFriends.size() == 0) {
                for (int i = 0; i < friends.size(); i++) {
                    User t = friends.get(i);
                    if (t.getCust_id() == cust_id) {
                        //t.setIs_host(true);
                        inFriends.add(t);
                        friends.remove(i);
                        friendList.setText(t.getName() + ",");
                        //System.out.println("inFriends size: " + inFriends.size() + inFriends.get(0).getName());
                        break;
                    }
                }
            }
        }
        mDishAdapter.update(dishes);
        setListViewHeightBasedOnChildren(mDishes);
    }

    @Subscribe
    public void OnSavedOrderEvent(OnSavedOrderEvent e) {
        progressDialog.hide();
        if (e.get() != null) {
            Intent i = OrderReviewActivity.createIntent(this, e.get().getiID());
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, "Unable to send this invitation", Toast.LENGTH_SHORT).show();
        }
    }

    public int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        //获取listview的适配器
        ListAdapter listAdapter = listView.getAdapter();
        //item的高度
        int itemHeight = 46;

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            totalHeight += Dp2Px(getApplicationContext(),itemHeight)+listView.getDividerHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;

        listView.setLayoutParams(params);
    }

}
