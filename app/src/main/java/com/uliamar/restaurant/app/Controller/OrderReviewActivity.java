package com.uliamar.restaurant.app.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.uliamar.restaurant.app.Bus.BusProvider;
import com.uliamar.restaurant.app.Bus.ChangeInvitationStatus;
import com.uliamar.restaurant.app.Bus.GetOneInvitationEvent;
import com.uliamar.restaurant.app.Bus.OnOneInvitationEvent;
import com.uliamar.restaurant.app.R;
import com.uliamar.restaurant.app.model.Dishe;
import com.uliamar.restaurant.app.model.Invitation;
import com.uliamar.restaurant.app.model.Restaurant;
import com.uliamar.restaurant.app.model.User;
import com.uliamar.restaurant.app.services.DataService;
import com.uliamar.restaurant.app.services.RESTrepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderReviewActivity extends Activity {
    public static final String ARG_INVITATION_ID = "ARG_INVITATION_ID";
    private String TAG = "OrderReviewActivity";
    private CountDown time;

    private Context mContext;
    private Invitation mInvitation;
    private int mInvitationID;
    private int mUserID;
    private User mThisUser;
    FriendAdapter mFriendAdapter;

    private ProgressDialog progressDialog;
    private ImageView mRestaurantCoverImageView;
    private TextView mRestaurantName;
    private TextView mCountDownTextView;
    private TextView mDateTextView;
    private TextView mAddressTextView;
    //private TextView mFriendListTextView;
    //private TextView mDishesListTextView;
    private TextView mTotalPrice;
    private TextView mPricePerParticipant;
    private ListView mInFriendsListView;
    private ListView mDishesListView;
    private Button mCancelButton;
    private Button mSendButton;
    private Button mStatusButton; // a button, just for display purpose, no click listeners expected
    private LinearLayout mHostControls;
    private LinearLayout mParticipantControls;
    private Button mAcceptButton;
    private Button mDeniedButton;

    public static Intent createIntent(Context c, int invitationID) {
        Intent myIntent = new Intent(c, OrderReviewActivity.class);
        myIntent.putExtra(ARG_INVITATION_ID, invitationID);
        return myIntent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mInvitationID = getIntent().getIntExtra(ARG_INVITATION_ID, 0);
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.SHARED_PREF_DB_NAME, 0);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        mUserID = sharedPreferences.getInt(LoginActivity.PREF_ACCOUNT_ID, 0);
        DataService.init();

        setContentView(R.layout.activity_order_review);
        mRestaurantName = (TextView) findViewById(R.id.EventReview_RestaurantName);
        mCountDownTextView = (TextView) findViewById(R.id.EventReview_CountDown);
        Typeface fontRobo = Typeface.createFromAsset(this.getAssets(), "fonts/roboto_thin.ttf");
        mCountDownTextView.setTypeface(fontRobo);
        mDateTextView = (TextView) findViewById(R.id.EventReview_Date);
        mAddressTextView = (TextView) findViewById(R.id.EventReview_address);
        mAddressTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + mInvitation.getOrder().getRestaurant().getAddress()));
                mContext.startActivity(i);
            }
        });
        //mDishesListTextView = (TextView) findViewById(R.id.EventReview_DishesList);
        //mFriendListTextView = (TextView) findViewById(R.id.EventReview_FriendList);
        mInFriendsListView = (ListView) findViewById(R.id.inFriendsList);
        mDishesListView = (ListView) findViewById(R.id.EventReview_DishesList2);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFriendAdapter = new FriendAdapter(this, inflater);
        mInFriendsListView.setAdapter(mFriendAdapter);


        mTotalPrice = (TextView) findViewById(R.id.EventReview_totalPrice);
        mPricePerParticipant = (TextView) findViewById(R.id.EventReview_pricePerParticipant);
        mHostControls = (LinearLayout) findViewById(R.id.EventReview_HostControls);
        mCancelButton = (Button) findViewById(R.id.EventReview_CancelButton);
        mSendButton = (Button) findViewById(R.id.EventReview_SendButton);
        mStatusButton = (Button) findViewById(R.id.EventReview_EventStatus);
        mParticipantControls = (LinearLayout) findViewById(R.id.EventReview_ParticipantControls);
        mAcceptButton = (Button) findViewById(R.id.EventReview_AcceptButton);
        mDeniedButton = (Button) findViewById(R.id.EventReview_deniedButton);
        mRestaurantCoverImageView = (ImageView) findViewById(R.id.EventReview_Cover);
        mSendButton.setOnClickListener(new InvitationClickListener());
        mCancelButton.setOnClickListener(new InvitationClickListener());
        mDeniedButton.setOnClickListener(new InvitationClickListener());
        mAcceptButton.setOnClickListener(new InvitationClickListener());
    }




    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.get().register(this);

        if (mInvitation == null) {
            progressDialog.show();

            BusProvider.get().post(new GetOneInvitationEvent(mInvitationID));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.get().unregister(this);
    }

    @Subscribe
    public void onOnGetInvitationEvent(OnOneInvitationEvent e) {
        mInvitation = e.get();
        if (mInvitation != null) {

            //Reset items visibility
            mHostControls.setVisibility(LinearLayout.GONE);
            mParticipantControls.setVisibility(LinearLayout.GONE);
            mStatusButton.setVisibility(Button.GONE);
            mAcceptButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            mDeniedButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            for ( int i = 0; i < mHostControls.getChildCount();  i++ ){
                View view = mHostControls.getChildAt(i);
                view.setVisibility(View.GONE); // Or whatever you want to do with the view.
            }
            for ( int i = 0; i < mParticipantControls.getChildCount();  i++ ){
                View view = mParticipantControls.getChildAt(i);
                view.setVisibility(View.GONE); // Or whatever you want to do with the view.
            }


            progressDialog.hide();
            Restaurant restaurant = mInvitation.getOrder().getRestaurant();
            if (restaurant.getPic() != null && !restaurant.getPic().isEmpty()) {
                Picasso.with(this).load("http://118.193.54.222" + restaurant.getPic()).placeholder(R.drawable.resto_big).into(mRestaurantCoverImageView);
            }
            mRestaurantName.setText(mInvitation.getOrder().getRestaurant().getName());
            String date = mInvitation.getOrder().getRequest_date().substring(0, 10);
            date += " " + mInvitation.getOrder().getStart_time() + ":00-" + mInvitation.getOrder().getEnd_time()+":00";
            mDateTextView.setText(date);
            Calendar now = Calendar.getInstance();
            Date orderNow = null;
            long l = now.getTimeInMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                orderNow = sdf.parse(mInvitation.getOrder().getFormatDate());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            long l2 = orderNow.getTime();
            int hour = now.get(Calendar.HOUR_OF_DAY);
//            //System.out.println("day hour is:"+hour);
//            int minute = now.get(Calendar.MINUTE);
//            if (minute == 0) {
//                hour = mInvitation.getOrder().getStart_time() - hour;
//                //minute = 60 - minute;
//            }else {
//                hour = mInvitation.getOrder().getStart_time() - hour -1;
//                minute = 60 - minute;
//            }
//            long mills = hour*60*60*1000 + minute*60*1000;
            if (l < l2) {
                time = new CountDown(l2 - l, 1000);
                time.start();
            }
            else{
                mCountDownTextView.setText("Bye!");
            }

            mAddressTextView.setText(mInvitation.getOrder().getRestaurant().getAddress());

            int nbParticipantComming = 0;
            //String userListText = "";
            List<User> userList = mInvitation.getParticipants();
            for (int i = 0; i < userList.size(); ++i) {
                if (userList.get(i).getCust_id() == mUserID) {
                    mThisUser = userList.get(i);
                }
                //userListText += userList.get(i).getName() + " " + userList.get(i).getInv_status() + "\n";
                if (userList.get(i).getInv_status().equals("accepted")) {
                    nbParticipantComming += 1;
                }
            }
            //mFriendListTextView.setText(userListText);
            mFriendAdapter.update(userList);
            setListViewHeightBasedOnChildren(mInFriendsListView);


            List<Map<String,Object>> listT = new ArrayList<Map<String, Object>>();
            Map<String,Object> map;
            //String dishListText = "";
            List<Dishe> disheList = mInvitation.getOrder().getDishes();
            for (int i = 0; i < disheList.size(); ++i) {
                //dishListText += disheList.get(i).getName() + " " + disheList.get(i).getQuantity() + " x " + disheList.get(i).getPrice() + "RMB\n";
                map = new HashMap<String, Object>();

                String disheName = disheList.get(i).getName();
                if (disheName.length() > 0) {
                    disheName = String.valueOf(disheName.charAt(0)).toUpperCase() + disheName.subSequence(1, disheName.length());
                }

                map.put("name", disheName);
                map.put("price", + disheList.get(i).getPrice() + " ¥");
                map.put("num",disheList.get(i).getQuantity() + " x ");
                listT.add(map);
            }
            //mDishesListTextView.setText(dishListText);
            SimpleAdapter dishAdapterTmp = new SimpleAdapter(this,listT,R.layout.dishe_item_list2,new String[]{"name","price","num"},new int[]{R.id.DisheName2,R.id.DishePrice2,R.id.DisheNum2});
            mDishesListView.setAdapter(dishAdapterTmp);
            setListViewHeightBasedOnChildren(mDishesListView);

            mTotalPrice.setText(mInvitation.getOrder().getTotal_price() + "");
            mPricePerParticipant.setText(( mInvitation.getOrder().getTotal_price() / (nbParticipantComming)) + "") ;

            /**
             1: planning
             2: booking
             3: booking succeed
             4: booking failed
             5: canceled
             6: revoked
             7: consumed"
             */
            switch(mInvitation.getOrder().getStatus()) {
                case 1 : {
                    /**
                     * if host: display controls, put click listener which send some event
                     * if participant: display controls which also send events;
                     */
                    if (mThisUser.isIs_host()) {
                        mHostControls.setVisibility(View.VISIBLE);
                        for ( int i = 0; i < mHostControls.getChildCount();  i++ ){
                            mHostControls.getChildAt(i).setVisibility(View.VISIBLE);
                        }
                    } else {
                        mParticipantControls.setVisibility(View.VISIBLE);
                        for ( int i = 0; i < mParticipantControls.getChildCount();  i++ ){
                            mParticipantControls.getChildAt(i).setVisibility(View.VISIBLE);
                        }
                        Log.v(TAG, mThisUser.getInv_status() + "" );
                        //Hide the unecessary button
                        if (mThisUser.getInv_status().equals("accepted")) {
                            mAcceptButton.setVisibility(View.GONE);
                        } else if (mThisUser.getInv_status().equals("denied")) {
                            mDeniedButton.setVisibility(View.GONE);
                        }
                    }
                    break;
                }
                case 2 : {
                    mStatusButton.setText("Waiting for restaurant validation");
                    mStatusButton.setVisibility(View.VISIBLE);
                    break;
                }
                case 3 : {
                    mStatusButton.setText("Restaurant comfirmed !");
                    mStatusButton.setBackgroundResource(R.color.green);
                    mStatusButton.setVisibility(View.VISIBLE);
                    break;
                }
                case 4 : {
                    mStatusButton.setText("Restaurant Canceled :-(");
                    mStatusButton.setBackgroundResource(R.color.red);
                    mStatusButton.setVisibility(View.VISIBLE);
                    break;
                }
                case 5 : {
                    mStatusButton.setText("This Invitation have been cancelled");
                    mStatusButton.setBackgroundResource(R.color.red);
                    mStatusButton.setVisibility(View.VISIBLE);
                    break;
                }
                case 7 : {
                    mStatusButton.setText("This event is passed");
                    mStatusButton.setVisibility(View.VISIBLE);
                    break;
                }
            }

        } else {
            Toast.makeText(this, "Unable to get this Invitation", Toast.LENGTH_SHORT).show();
        }
    }

    class InvitationClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            progressDialog.show();

            switch (view.getId()) {
                case R.id.EventReview_CancelButton : {
                    BusProvider.get().post(new ChangeInvitationStatus(mInvitationID, ChangeInvitationStatus.Status.CANCEL));
                    break;
                }
                case R.id.EventReview_SendButton : {
                    BusProvider.get().post(new ChangeInvitationStatus(mInvitationID, ChangeInvitationStatus.Status.SEND));
                    break;
                }
                case R.id.EventReview_deniedButton : {
                    BusProvider.get().post(new ChangeInvitationStatus(mInvitationID, ChangeInvitationStatus.Status.DENY));
                    break;
                }
                case R.id.EventReview_AcceptButton : {
                    BusProvider.get().post(new ChangeInvitationStatus(mInvitationID, ChangeInvitationStatus.Status.ACCEPT));
                    break;
                }
            }
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


    class CountDown extends CountDownTimer {

        public CountDown(long l,long interval){
            super(l, interval);
        }

        @Override
        public void onTick(long l) {
            int sum = Integer.parseInt(l/1000+"");
            int hour = sum/3600;
            int day = hour/24;
            hour = hour%24;
            sum = sum%3600;
            int minute = sum/60;
            sum = sum%60;
            int second = sum;
            if (day == 0) {
                mCountDownTextView.setText(hour + ":" + minute + ":" + second);
            }else {
                mCountDownTextView.setText(day + "days left.");
            }
        }

        @Override
        public void onFinish() {

        }

    }

}
