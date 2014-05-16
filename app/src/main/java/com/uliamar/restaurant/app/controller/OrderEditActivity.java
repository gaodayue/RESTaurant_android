package com.uliamar.restaurant.app.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaRouter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    Spinner mPeriod;
    DatePicker mDate;
    ListView mDishes;
    TextView friendList;
    private int mRestaurantID;
    private List<String> list = new ArrayList<String>();
    private List<Dishe> dishes = new ArrayList<Dishe>();
    private List<User> friends = new ArrayList<User>();
    private List<User> inFriends = new ArrayList<User>();
    private ArrayAdapter<String> adapter;
    private int cust_id;
    private Order order;

    public static final String ARG_RESTAURANT_ID = "ARG_RESTAURANT_ID";


    public static Intent createIntent(Context c, int restaurantID) {
        Intent myIntent = new Intent(c, OrderEditActivity.class);
        myIntent.putExtra(ARG_RESTAURANT_ID, restaurantID);
        return myIntent;
    }

    private void init(){
        order = new Order();
        list.add("noon");
        list.add("evening");
        list.add("midnight");
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SharedPreferences preferences=this.getSharedPreferences(SHARED_PREF_DB_NAME, MODE_PRIVATE);
        cust_id = preferences.getInt(PREF_ACCOUNT_ID, 1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_order_edit);
        ref = this;
        mRestaurantID = getIntent().getIntExtra(ARG_RESTAURANT_ID, 0);

        mPeriod = (Spinner) findViewById(R.id.spinner);
        mPeriod.setAdapter(adapter);
        /**
         *      Caused by: java.lang.RuntimeException: setOnItemClickListener cannot be used with a spinner.
         */
        mPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //order.setRequest_date("noon");
                order.setRequest_period(list.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        mDate = (DatePicker) findViewById(R.id.datePicker);
        mDate.init(2014,5,14,new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                order.setRequest_date(i+"-"+i2+"-"+i3);
                //order.setDate(date);
            }
        });

        mDishes = (ListView) findViewById(R.id.DishList);

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
                builder.setTitle("FriendLIST").setIcon(android.R.drawable.ic_lock_lock).setItems(itemStrings, new DialogInterface.OnClickListener() {
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
                final String[] itemStrings = new String[inFriends.size()];
                for(int i=0;i<inFriends.size();i++){
                    itemStrings[i] = inFriends.get(i).getName();
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(OrderEditActivity.this);
                builder.setTitle("inFriendLIST").setIcon(android.R.drawable.ic_lock_lock).setItems(itemStrings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(getApplicationContext(), "你点击的是" + itemStrings[which], Toast.LENGTH_LONG).show();
                        //String tmp = friendList.getText().toString();
                        //tmp += itemStrings[which] + ",";
                        //friendList.setText(tmp);
                        //inFriends.add(friends.get(which));
                        //friends.remove(which);
                        friends.add(inFriends.get(which));
                        inFriends.remove(which);
                        String tmp = "";
                        for (int i=0;i<inFriends.size();i++){
                            tmp += inFriends.get(i).getName() + ",";
                        }
                        friendList.setText(tmp);
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
                order.setRequest_date("2014-05-05");
                order.setRequest_period("1");
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
                order.setDishes(dl);

                progressDialog = new ProgressDialog(ref);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Wait while loading...");
                progressDialog.show();
                BusProvider.get().post(new SaveOrderEvent(order));

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
        progressDialog.dismiss();
        /**
         * @To-do: feed the view with data
         */
        Toast.makeText(this, "Datas received", Toast.LENGTH_SHORT).show();
        restaurant = e.getRestaurant();
        order.setRestaurant(restaurant);
        friends = e.getFriends();
        dishes = restaurant.getDishes();

        for (int i=0;i<friends.size();i++){
            User t = friends.get(i);
            if (t.getCust_id() == cust_id){
                //t.setIs_host(true);
                inFriends.add(t);
                friends.remove(i);
                friendList.setText(t.getName()+",");
                break;
            }
        }

        //生成动态数组，加入数据
        ArrayList< HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for(int i=0;i<dishes.size();i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("DishName", dishes.get(i).getD_name());
            map.put("DishPrice", dishes.get(i).getD_price());
            map.put("DishNum",dishes.get(i).getQuantity());
            listItem.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源
                R.layout.dishe_item_list,//ListItem的XML实现
                //动态数组与ImageItem对应的子项
                new String[] {"DishName", "DishNum","DishPrice"},
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] {R.id.DisheName,R.id.textView2,R.id.DishePrice}
        );

        //添加并且显示
        mDishes.setAdapter(listItemAdapter);
    }

    @Subscribe
    public void OnSavedOrderEvent(OnSavedOrderEvent e) {
        progressDialog.dismiss();
        Intent i = OrderReviewActivity.createIntent(this, e.get().getiID());
        startActivity(i);
        finish();
    }

}
