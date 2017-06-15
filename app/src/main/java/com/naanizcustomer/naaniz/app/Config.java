package com.naanizcustomer.naaniz.app;


import android.Manifest;

/**
 * Created by hemba on 6/14/2017.
 */

public class Config {
    public static final int PHONE_REQUEST_CODE=0;
    public static final int PLACE_REQUEST_CODE=1;
    public static final String REGISTER_CUSTOMER_URL="https://naanibackend.herokuapp.com/api/v1/customers/registercustomer";
    public static final String ALL_CUSTOMERS_URL="https://naanibackend.herokuapp.com/api/v1/customers/allcustomers";

    public static final int PERMISSION_ALL = 1;
    public static final String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECEIVE_SMS,Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE};
}
