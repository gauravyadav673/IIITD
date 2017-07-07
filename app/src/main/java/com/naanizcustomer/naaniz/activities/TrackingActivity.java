package com.naanizcustomer.naaniz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.HyperTrackMapFragment;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import com.hypertrack.lib.models.Action;
import com.hypertrack.lib.models.ErrorResponse;
import com.hypertrack.lib.models.SuccessResponse;
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.adapters.TrackingMapAdapter;
import com.naanizcustomer.naaniz.app.Config;
import com.naanizcustomer.naaniz.callbacks.MyMapFragmentCallback;
import com.naanizcustomer.naaniz.database.DbHelper;
import com.naanizcustomer.naaniz.models.Order;
import com.naanizcustomer.naaniz.utils.SharedPrefUtil;
import com.naanizcustomer.naaniz.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TrackingActivity extends AppCompatActivity  {
    private Button mOrderBtn;
    private HyperTrackMapFragment hyperTrackMapFragment;
    private String s,itemName, category, actionID, schedule;
    private Intent i;
    SharedPrefUtil sharedPrefUtil;
    DbHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        mOrderBtn = (Button) findViewById(R.id.order_btn);
        sharedPrefUtil = new SharedPrefUtil(TrackingActivity.this);
        DB = new DbHelper(TrackingActivity.this);
        i=getIntent();
        s=i.getStringExtra("vendors");
        itemName = i.getStringExtra("itemName");
        category = i.getStringExtra("category");
        actionID = i.getStringExtra("actionID");
        schedule = i.getStringExtra("scheduledat");
        Log.d("vendors", s);
        initUI();

        mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

/*        mCompleteActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Complete action using actionId for the order tracking action that you created
                HyperTrack.completeAction("#hemant123");
            }
        });
       // getActionDetails();*/

    }

    private void initUI() {
        /** Initialize Map Fragment added in Activity Layout to getMapAsync
         *  Once map is created onMapReady callback will be fire with GoogleMap object
         */
        hyperTrackMapFragment = (HyperTrackMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.htMapfragment);
        hyperTrackMapFragment.setHTMapAdapter(new TrackingMapAdapter(TrackingActivity.this));
        hyperTrackMapFragment.setMapFragmentCallback(new MyMapFragmentCallback(TrackingActivity.this,s));

    }

    @Override
    protected void onStop() {
        HyperTrack.removeActions(null);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        HyperTrack.stopTracking();

        super.onDestroy();
    }

/*    private void getActionDetails() {

        HyperTrack.getAction("8b9114f6-2c95-4014-a230-f755ebe1b7fa", new HyperTrackCallback() {
            @Override
            public void onSuccess(@NonNull SuccessResponse successResponse) {
                Action action = (Action) successResponse.getResponseObject();
                //Log.d("distance: ", action.getDistanceInKMS().toString());
                Log.d("status: ", action.getStatus());
                Log.d("Expected: ", action.getExpectedAT().toString());
                Log.d("ExpectedPlace: ", action.getExpectedPlace().toString());
            }

            @Override
            public void onError(@NonNull ErrorResponse errorResponse) {
                Log.d("Error",errorResponse.toString());

            }
        });
    }*/

/*    private void notfyVendors(){
        final String actionID = i.getStringExtra("actionID");
        final String ItemName = i.getStringExtra("itemName");
        final String users = parseTokens();
        final String url = Config.API_URL + Config.ORDER + Config.NOTIFY_VENDORS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("noti", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("noti", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("actionid", actionID);
                params.put("itemname", ItemName);
                params.put("users", users);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TrackingActivity.this);
        requestQueue.add(stringRequest);

    }

    public String parseTokens(){
        try {
            JSONArray data = new JSONArray(s);
            int Count = data.length();
            String firstToken = data.getJSONObject(0).getString("token");
            String users = "[{\"Count\":"+ Count +"},[{\"token\":\"" + firstToken + "\"}";
            Log.d("users", users);

            for(int i = 1 ; i < Count ; i++){
                JSONObject object = data.getJSONObject(i);
                users = users + ",{\"token\":\"" + object.getString("token") + "\"}";

            }
            users = users + "]]";
            Log.d("users1", users);
            return users;
        } catch (JSONException e) {
            e.printStackTrace();
            return "error";
        }
    }*/

    private void placeOrder(){
        String url = Config.API_URL + Config.ORDER + Config.PLACE_ORDER;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //DB.saveOrder(new Order(itemName, category, actionID, schedule, false, false));
                        Util.toastS(TrackingActivity.this, "Order Placed");
                        Log.d("response", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
                Util.toastS(TrackingActivity.this, "Error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", sharedPrefUtil.getCustomerDetails().getName());
                params.put("itemname", itemName);
                params.put("buyer", String.valueOf(sharedPrefUtil.getCustomerDetails().getContact()));
                params.put("category", category);
                params.put("lat", String.valueOf(sharedPrefUtil.getCustomerDetails().getLatLng().latitude));
                params.put("long", String.valueOf(sharedPrefUtil.getCustomerDetails().getLatLng().longitude));
                params.put("address", sharedPrefUtil.getCustomerDetails().getAddress());
                params.put("actionid", actionID);
                params.put("scheduledat", schedule);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(TrackingActivity.this);
        requestQueue.add(stringRequest);
    }

}
