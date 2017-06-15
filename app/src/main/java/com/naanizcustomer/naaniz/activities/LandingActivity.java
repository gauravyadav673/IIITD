package com.naanizcustomer.naaniz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookSdk;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.app.Config;
import com.naanizcustomer.naaniz.utils.SharedPrefUtil;
import com.naanizcustomer.naaniz.utils.Util;

public class LandingActivity extends AppCompatActivity {
private SharedPrefUtil mSharedPrefUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(LandingActivity.this);
        AccountKit.initialize(LandingActivity.this);
        setContentView(R.layout.activity_main);
        mSharedPrefUtil=new SharedPrefUtil(LandingActivity.this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_out_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out_option:
                AccountKit.logOut();
                mSharedPrefUtil.deleteCustomerDetails();
                mSharedPrefUtil.setRegistered(false);
                Intent i=new Intent(LandingActivity.this,RegisterActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);

        }
        return super.onOptionsItemSelected(item);
    }
}
