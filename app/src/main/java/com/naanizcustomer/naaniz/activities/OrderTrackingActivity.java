package com.naanizcustomer.naaniz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.HyperTrackMapFragment;
import com.hypertrack.lib.models.Action;
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.adapters.TrackingMapAdapter;
import com.naanizcustomer.naaniz.app.Config;
import com.naanizcustomer.naaniz.callbacks.OrderTrackingMapFragmentCallback;

import java.util.ArrayList;
import java.util.List;

public class OrderTrackingActivity extends AppCompatActivity {
    String trackedAction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracking);
        Intent i=getIntent();
        trackedAction=i.getStringExtra(Config.INTENT_KEY_ACTION_ID);
        initUI();
    }
    private void initUI(){

        /** Initialize Map Fragment added in Activity Layout to getMapAsync
         *  Once map is created onMapReady callback will be fire with GoogleMap object
         */
        HyperTrackMapFragment hyperTrackMapFragment = (HyperTrackMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.htMapfragment_order_tracking_activity);

        /**
         * Call the method below to enable UI customizations for Live Tracking View,
         * an instance of HyperTrackMapAdapter needs to be set as depicted below
         */
        hyperTrackMapFragment.setHTMapAdapter(new TrackingMapAdapter(OrderTrackingActivity.this));

         /*
         * Call the method below to register for any callbacks/updates on Live Tracking View/Map
         */
        hyperTrackMapFragment.setMapFragmentCallback(new OrderTrackingMapFragmentCallback());

    }

    @Override
    protected void onStop() {
        super.onStop();
        ArrayList<String> actions=new ArrayList<>();
        actions.add(trackedAction);
        HyperTrack.removeActions(actions);
    }
}
