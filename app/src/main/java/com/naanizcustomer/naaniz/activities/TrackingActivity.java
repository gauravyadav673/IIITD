package com.naanizcustomer.naaniz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import com.naanizcustomer.naaniz.callbacks.MyMapFragmentCallback;

public class TrackingActivity extends AppCompatActivity  {
    private Button mCompleteActionBtn;
    private HyperTrackMapFragment hyperTrackMapFragment;
    private String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        mCompleteActionBtn = (Button) findViewById(R.id.complete_action_btn);
        Intent i=getIntent();
        s=i.getStringExtra("vendors");
        initUI();

        mCompleteActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Complete action using actionId for the order tracking action that you created
                HyperTrack.completeAction("#hemant123");
            }
        });
        getActionDetails();

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

    private void getActionDetails() {

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
    }

}
