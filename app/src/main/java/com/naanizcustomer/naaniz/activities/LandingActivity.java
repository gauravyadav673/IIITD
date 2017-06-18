package com.naanizcustomer.naaniz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.accountkit.AccountKit;
import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import com.hypertrack.lib.models.ErrorResponse;
import com.hypertrack.lib.models.SuccessResponse;
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.utils.SharedPrefUtil;
import com.naanizcustomer.naaniz.utils.Util;

import java.util.ArrayList;

public class LandingActivity extends AppCompatActivity {
    private SharedPrefUtil mSharedPrefUtil;
    private Button mTrackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   FacebookSdk.sdkInitialize(LandingActivity.this);
        AccountKit.initialize(LandingActivity.this);
        setContentView(R.layout.activity_landing);
        mTrackBtn = (Button) findViewById(R.id.track_btn);
        mSharedPrefUtil = new SharedPrefUtil(LandingActivity.this);
        mTrackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackAction();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_out_menu, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void trackAction() {
        // Call trackAction API method with action ID for tracking.
        // Start YourMapActivity containing HyperTrackMapFragment view with the
        // customization on succes response of trackAction method
        ArrayList<String> actions = new ArrayList<>();
        actions.add("#hemant123");

        HyperTrack.trackAction(actions, new HyperTrackCallback() {
            @Override
            public void onSuccess(@NonNull SuccessResponse response) {


                //Start Activity containing HyperTrackMapFragment
                Intent intent = new Intent(LandingActivity.this, TrackingActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(@NonNull ErrorResponse errorResponse) {

                Util.toastS(LandingActivity.this, "Error Occurred while trackActions: " +
                        errorResponse.getErrorMessage());

            }
        });

    }
}
