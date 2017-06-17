package com.naanizcustomer.naaniz.application;

import android.app.Application;

import com.hypertrack.lib.HyperTrack;

/**
 * Created by hemba on 6/17/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize HyperTrack SDK with your Publishable Key here
        // Refer to documentation at
        // https://docs.hypertrack.com/gettingstarted/authentication.html
        // @NOTE: Add **"pk_test_f61b043fd5989cd99ad90f0b28e30929c828b701"** here for SDK to be
        // authenticated with HyperTrack Server
        HyperTrack.initialize(this, "pk_test_f61b043fd5989cd99ad90f0b28e30929c828b701");

    }
}
