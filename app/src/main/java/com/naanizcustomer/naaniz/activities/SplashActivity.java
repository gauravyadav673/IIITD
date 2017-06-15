package com.naanizcustomer.naaniz.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.utils.SharedPrefUtil;

public class SplashActivity extends AppCompatActivity {
    private SharedPrefUtil mSharedPrefUtil;
    private Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mSharedPrefUtil=new SharedPrefUtil(SplashActivity.this);
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

    }
}
