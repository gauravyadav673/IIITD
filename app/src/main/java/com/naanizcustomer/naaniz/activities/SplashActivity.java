package com.naanizcustomer.naaniz.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import com.hypertrack.lib.models.ErrorResponse;
import com.hypertrack.lib.models.SuccessResponse;
import com.hypertrack.lib.models.User;
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.utils.SharedPrefUtil;
import com.naanizcustomer.naaniz.utils.Util;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {
    private SharedPrefUtil mSharedPrefUtil;
    private Intent i;
    RequestQueue mRequestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSharedPrefUtil=new SharedPrefUtil(SplashActivity.this);
        mRequestQueue= Volley.newRequestQueue(SplashActivity.this);
        if (mSharedPrefUtil.isCustomerRegistered()){
            i=new Intent(SplashActivity.this,LandingActivity.class);
        }else{
            i=new Intent(SplashActivity.this,RegisterActivity.class);
        }

        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        Handler handler=new Handler();
        Runnable r=new Runnable() {
            @Override
            public void run() {
            startActivity(i);
            }
        };
        handler.postDelayed(r,1000);

    }}
