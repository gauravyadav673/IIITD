package com.naanizcustomer.naaniz.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;
import com.naanizcustomer.naaniz.interfaces.SharedPrefConstantUtils;
import com.naanizcustomer.naaniz.models.Customer;

/**
 * Created by hemba on 6/14/2017.
 */

public class SharedPrefUtil implements SharedPrefConstantUtils {
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    public SharedPrefUtil(Context mContext){
        mSharedPreferences=mContext.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();
    }
    public void saveCustomerDetails(Customer mCustomer){
        mEditor.putBoolean(CUSTOMER_SAVED,true);
        mEditor.putString(CUSTOMER_NAME,mCustomer.getName());
        mEditor.putInt(CUSTOMER_CONTACT,mCustomer.getContact());
        mEditor.putString(CUSTOMER_LAT,""+mCustomer.getLatLng().latitude);
        mEditor.putString(CUSTOMER_LONG,""+mCustomer.getLatLng().longitude);
        mEditor.putString(CUSTOMER_ADDRESS,mCustomer.getAddress());
        mEditor.apply();
    }
    public void setRegistered(boolean registered){
        mEditor.putBoolean(CUSTOMER_REGISTERED,registered);
        mEditor.apply();
    }
    public boolean isCustomerRegistered(){
        boolean a=mSharedPreferences.getBoolean(CUSTOMER_REGISTERED,false);
        return a;
    }

    public Customer getCustomerDetails(){
        String name=mSharedPreferences.getString(CUSTOMER_NAME,null);
        int contact=mSharedPreferences.getInt(CUSTOMER_CONTACT,0);
        Double mLat=Double.parseDouble(mSharedPreferences.getString(CUSTOMER_LAT,null));
        Double mLong=Double.parseDouble(mSharedPreferences.getString(CUSTOMER_LAT,null));
        String addr=mSharedPreferences.getString(CUSTOMER_ADDRESS,null);
        Customer customer=new Customer(name,contact,new LatLng(mLat,mLong),addr);
        return customer;
    }
    public boolean isCustomerSaved(){
        boolean b=mSharedPreferences.getBoolean(CUSTOMER_SAVED,false);
        return b;
    }

    public void deleteCustomerDetails(){
        mEditor.clear().apply();
    }
    public void saveToken(String token){
        mEditor.putString(FIREBASE_TOKEN,token);
        mEditor.apply();
    }
    public String getToken(){
        return mSharedPreferences.getString(FIREBASE_TOKEN,null);
    }
}
