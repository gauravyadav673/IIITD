package com.naanizcustomer.naaniz.activities;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.accountkit.AccountKit;
import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import com.hypertrack.lib.models.ErrorResponse;
import com.hypertrack.lib.models.SuccessResponse;
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.adapters.ItemsRecycyclerViewAdapter;
import com.naanizcustomer.naaniz.app.Config;
import com.naanizcustomer.naaniz.database.DbHelper;
import com.naanizcustomer.naaniz.models.Items;
import com.naanizcustomer.naaniz.utils.SharedPrefUtil;
import com.naanizcustomer.naaniz.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static android.R.id.list;
import static android.R.id.selectAll;

public class LandingActivity extends AppCompatActivity {
    private SharedPrefUtil mSharedPrefUtil;
   private Button ordersBtn;
    private RequestQueue mRequestQueue;
    private final String TAG="LANDING";
    private RecyclerView mItemRV;
    private String mAllVendors;
    private boolean mGetVendors=false;
    ArrayList<Items> itemses=new ArrayList<>();
    ProgressDialog pd;
    DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountKit.initialize(LandingActivity.this);
        setContentView(R.layout.activity_landing);
       // mTrackBtn = (Button) findViewById(R.id.track_btn);
        mSharedPrefUtil = new SharedPrefUtil(LandingActivity.this);
        dbHelper = new DbHelper(LandingActivity.this);
        ordersBtn = (Button) findViewById(R.id.myOrders_btn);
        mRequestQueue= Volley.newRequestQueue(LandingActivity.this);
        ordersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, MyOrdersActivity.class);
                startActivity(intent);
            }
        });
        initItemsRecyclerView();
        if(Util.isNetConnected(LandingActivity.this)){
        getAllItems();
        }else {
            Util.toastS(LandingActivity.this, "No Internet");
        }
        Util.getScheduleDate();
/*        getVendors();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_layout, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Items> sortedList = new ArrayList<>();
                Log.d("Search:",newText);
                for(int i = 0 ; i < itemses.size() ; i++){
                    if(itemses.get(i).getItemName().toLowerCase().contains(newText.toLowerCase())){
                        sortedList.add(itemses.get(i));
                    }
                }

                setAdapterToRV(sortedList);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out_option:
                AccountKit.logOut();
                mSharedPrefUtil.deleteCustomerDetails();
                mSharedPrefUtil.setRegistered(false);
                Intent i = new Intent(LandingActivity.this, RegisterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;

            case R.id.clear_data_option:
                dbHelper.clearOrdersTable();
                Util.toastS(LandingActivity.this, "Data Cleared");
                break;
        }
        return super.onOptionsItemSelected(item);
    }



/*    private void trackAction() {
        // Call trackAction API method with action ID for tracking.
        // Start YourMapAc  tivity containing HyperTrackMapFragment view with the
        // customization on succes response of trackAction method
        ArrayList<String> actions = new ArrayList<>();
        actions.add("#hemant123");
        HyperTrack.trackAction(actions, new HyperTrackCallback() {
            @Override
            public void onSuccess(@NonNull SuccessResponse response) {
                //Start Activity containing HyperTrackMapFragment
                Intent intent = new Intent(LandingActivity.this, TrackingActivity.class);
                intent.putExtra("vendors",mAllVendors);
                startActivity(intent);
            }

            @Override
            public void onError(@NonNull ErrorResponse errorResponse) {

                Util.toastS(LandingActivity.this, "Error Occurred while trackActions: " +
                        errorResponse.getErrorMessage());

            }
        });

    }*/
    //get all the items sold by vendors
    private void getAllItems(){
        pd = Util.getProgDialog(LandingActivity.this, "Wait", "loading...", false);
        pd.show();
        String url= Config.API_URL+Config.ITEMS_URL+Config.GET_ALL_ITEMS_URL;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    Log.d(TAG,"array"+jsonArray.toString());
                    JSONObject jsonObject=jsonArray.getJSONObject(0);

                    int success=jsonObject.getInt("success");
                    if (success==1){
                        Log.d(TAG,"success");
                        JSONObject jsonObject1=jsonArray.getJSONObject(1);
                        JSONArray mAllItems=jsonObject1.getJSONArray("data");
                        for (int i=0;i<mAllItems.length();i++){
                            JSONObject item=mAllItems.getJSONObject(i);
                            Log.d(TAG,""+i+item.toString());
                            String price=item.getJSONObject("Details").getString("Price");
                            String priceType=item.getJSONObject("Details").getString("PriceType");
                            String categ=item.getString("Category");
                            String name=item.getString("ItemName");
                            String soldBy=item.getString("SoldBy");
                            Double pr=Double.parseDouble(price);
                            itemses.add(new Items(categ,name,priceType,soldBy,pr));
                        }
                        setAdapterToRV(itemses);
                        pd.dismiss();
                    }else{
                        Log.d(TAG,"failure");
                        pd.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.toString());
                pd.dismiss();
            }
        });
        stringRequest.setTag(Config.GET_ITEMS_QUEUE_TAG);
        mRequestQueue.add(stringRequest);
    }
    private void initItemsRecyclerView(){
        mItemRV=(RecyclerView)findViewById(R.id.items_recycler_view);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(LandingActivity.this,LinearLayoutManager.VERTICAL,false);
        mItemRV.setLayoutManager(linearLayoutManager);
    }
    private void setAdapterToRV(ArrayList<Items> itemses){
        mItemRV.setAdapter(new ItemsRecycyclerViewAdapter(itemses,LandingActivity.this));
    }
/*    private void getVendors(){
        String url=Config.API_URL+Config.VENDORS_URL+Config.GET_ALL_VENDORS_URL;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    Log.d(TAG,"array"+jsonArray.toString());
                    JSONObject jsonObject=jsonArray.getJSONObject(0);

                    int success=jsonObject.getInt("success");
                    if (success==1){
                        Log.d(TAG,"success");
                        JSONObject jsonObject1=jsonArray.getJSONObject(1);
                        JSONArray mAllItems=jsonObject1.getJSONArray("data");
                        mAllVendors=mAllItems.toString();
                        mGetVendors=true;
                    }else{
                        Log.d(TAG,"failure");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.toString());
            }
        });
        stringRequest.setTag(Config.GET_ITEMS_QUEUE_TAG);
        mRequestQueue.add(stringRequest);

    }*/
}
