package com.naanizcustomer.naaniz.app;


import android.Manifest;

/**
 * Created by hemba on 6/14/2017.
 */

public class Config {
    public static final int PHONE_REQUEST_CODE=0;
    public static final int PLACE_REQUEST_CODE=1;
    public static final String API_URL="https://naanibackend.herokuapp.com/api/v1";
    public static final String CUSTOMERS_URL="/customers";
    public static final String ALL_CUSTOMERS_URL="/allcustomers";
    public static final String REGISTER_CUSTOMER_URL="/registercustomer";
    public static final String ITEMS_URL="/items";
    public static final String GET_ALL_ITEMS_URL="/getallitems";
    public static final String VENDORS_URL="/vendors";
    public static final String GET_ALL_VENDORS_URL="/allvendors";
    public static final String REQUEST_QUEUE_TAG="register";
    public static final String GET_ITEMS_QUEUE_TAG="getitems";
    public static final int PERMISSION_ALL = 1;
    public static final String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.RECEIVE_SMS,Manifest.permission.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE};

    public static final String DISTANCE_URL="https://maps.googleapis.com/maps/api/distancematrix/json?origins=vikas+nagar+sonepat+Haryana+india&destinations=ymca+faridabad+haryana+india&mode=driving&language=en-EN";

}
