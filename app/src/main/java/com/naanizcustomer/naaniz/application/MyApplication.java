package com.naanizcustomer.naaniz.application;

import android.app.Application;

import com.hypertrack.lib.HyperTrack;
import com.naanizcustomer.naaniz.R;

/**
 * Created by hemba on 6/17/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HyperTrack.initialize(this, getResources().getString(R.string.HyperTrackPublishableKey));

    }
}
