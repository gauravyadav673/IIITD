package com.naanizcustomer.naaniz.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.iid.FirebaseInstanceId;
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.app.Config;
import com.naanizcustomer.naaniz.models.Customer;
import com.naanizcustomer.naaniz.utils.SharedPrefUtil;
import com.naanizcustomer.naaniz.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private TextView mAddrTv;
    private Button mPlacePickerBtn;
    private Button mRegBtn;
    private EditText mNameInp;
    private boolean placeKnown = false;
    private Context mContext;
    private String mAddress;
    private LatLng mCustomerLatLong;
    private String mCustomerName;
    private int mCustomerNumber = 0;
    private SharedPrefUtil mSharedPrefUtil;
    private Button mPhoneButton;
    private TextView mPhoneTv;
    private RequestQueue mRequestQueue;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   FacebookSdk.sdkInitialize(RegisterActivity.this);
        AccountKit.initialize(RegisterActivity.this);
        setContentView(R.layout.activity_register);
        mContext = RegisterActivity.this;
        mAddrTv = (TextView) findViewById(R.id.address_tv);
        mPlacePickerBtn = (Button) findViewById(R.id.pick_place_btn);
        mRegBtn = (Button) findViewById(R.id.final_register_btn);
        mPhoneTv = (TextView) findViewById(R.id.number_tv);
        mNameInp = (EditText) findViewById(R.id.name_inp);
        mPhoneButton = (Button) findViewById(R.id.phone_btn);
        mSharedPrefUtil = new SharedPrefUtil(mContext);
        mRequestQueue = Volley.newRequestQueue(mContext);
        askPermissions();
        buildGoogleApiClient();
        getCustomerNumber();
        Log.d("Firebase Token", FirebaseInstanceId.getInstance().getToken());
        mPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccessToken accessToken = AccountKit.getCurrentAccessToken();
                if (accessToken != null) {
                    //Handle Returning User
                    phoneAlert();
                } else {
                    //Handle new or logged out user
                    phoneLogin();
                }
            }
        });
        mPlacePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGoogleApiClient != null) {
                    startPlacePicker();
                } else {
                    Util.toastS(mContext, "Client Not ready!");
                }
            }
        });
        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomerName = mNameInp.getText().toString();
                if ((mCustomerName.length() > 0) && (mCustomerNumber != 0) && placeKnown) {
                    mProgressDialog = Util.getProgDialog(mContext, "Registering", "Please Wait we are logging you in...", false);
                    regCustomerOnServer();
                } else {
                    Util.toastS(mContext, "Some fields Empty");
                }
            }
        });
    }

    private void startPlacePicker() {

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(RegisterActivity.this), Config.PLACE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(RegisterActivity.this, RegisterActivity.this)
                .build();

    }

    public void phoneLogin() {
        final Intent intent = new Intent(RegisterActivity.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, Config.PHONE_REQUEST_CODE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Error", connectionResult.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Config.PERMISSION_ALL:
                //perform handling if request granted or not

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Config.PLACE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Place place = PlacePicker.getPlace(data, this);
                    LatLng s = place.getLatLng();
                    mCustomerLatLong = s;
                    mAddress = place.getAddress().toString();
                    mAddrTv.setText(mAddress);
                    placeKnown = true;
                }else{
                    Util.toastS(mContext,"Error");
                }
                break;
            case Config.PHONE_REQUEST_CODE:
                AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
                if ((loginResult.getError() == null) && !(loginResult.wasCancelled()) && (loginResult.getAccessToken() != null)) {
                    getCustomerNumber();
                } else {

                }
        }
    }

    private void getCustomerNumber() {
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                String s = account.getPhoneNumber().toString().substring(3, 12);
                int contact = Integer.parseInt(s);
                mCustomerNumber = contact;
                mPhoneTv.setText("" + account.getPhoneNumber());
            }
            @Override
            public void onError(AccountKitError error) {
            }
        });

    }

    private void phoneAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this)
                .setTitle("Already Verified")
                .setMessage("Your Phone number is already verified, if you wish to change your number press Ok")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        phoneLogin();
                    }
                }).create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRequestQueue.cancelAll(Config.REQUEST_QUEUE_TAG);
    }

    private void regCustomerOnServer() {
        mProgressDialog.show();
        String mRegUrl=Config.API_URL+Config.CUSTOMERS_URL+Config.REGISTER_CUSTOMER_URL;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mRegUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("Registering user",response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    int a = jsonObject.getInt("failed");
                    int b = jsonObject.getInt("success");
                    if (a == 0 && b == 1) {
                        saveCustomerData();
                        mProgressDialog.cancel();
                        goToLandingActivity();
                    } else {
                        mProgressDialog.cancel();
                        Util.toastS(mContext, "Some Error Occured");
                    }

                } catch (JSONException e) {
                    mProgressDialog.cancel();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.cancel();
                Util.toastS(mContext, "Server Error!");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("name", mCustomerName);
                params.put("contact", "" + mCustomerNumber);
                params.put("lat", "" + mCustomerLatLong.latitude);
                params.put("long", "" + mCustomerLatLong.longitude);
                params.put("address", mAddress);
                params.put("firebasetoken", FirebaseInstanceId.getInstance().getToken().toString());

                return params;
            }
        };
        stringRequest.setTag(Config.REQUEST_QUEUE_TAG);
        mRequestQueue.add(stringRequest);
    }

    private void saveCustomerData() {
        Customer customer = new Customer(mCustomerName, mCustomerNumber, mCustomerLatLong, mAddress);
        mSharedPrefUtil.saveCustomerDetails(customer);
        mSharedPrefUtil.setRegistered(true);
        Util.toastS(mContext, "Registered");
    }

    private void goToLandingActivity() {
        Intent i = new Intent(RegisterActivity.this, LandingActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void askPermissions() {
        if (!Util.hasPermissions(RegisterActivity.this, Config.PERMISSIONS)) {
            ActivityCompat.requestPermissions(RegisterActivity.this, Config.PERMISSIONS, Config.PERMISSION_ALL);
        }
    }

}
