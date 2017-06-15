package com.naanizcustomer.naaniz.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by hemba on 6/14/2017.
 */

public class Customer  {
    private String mName;
    private int mContact;
    private LatLng mLatLng;
    private String mAddress;

    public Customer(String name, int contact, LatLng latLng, String address) {
        mName = name;
        mContact = contact;
        mLatLng = latLng;
        mAddress = address;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getContact() {
        return mContact;
    }

    public void setContact(int contact) {
        mContact = contact;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}
