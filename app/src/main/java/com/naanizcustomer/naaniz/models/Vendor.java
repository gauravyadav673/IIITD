package com.naanizcustomer.naaniz.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by hemba on 6/20/2017.
 */

public class Vendor {
    private String mAadharNumber,mContact,mVendorName,Address;
    private LatLng mLatLng;
    private boolean mSabziwala,mFruitWala;

    public Vendor(String aadharNumber, String contact, String vendorName, String address, LatLng latLng, boolean sabziwala, boolean fruitWala) {
        mAadharNumber = aadharNumber;
        mContact = contact;
        mVendorName = vendorName;
        Address = address;
        mLatLng = latLng;
        mSabziwala = sabziwala;
        mFruitWala = fruitWala;
    }

    public String getAadharNumber() {
        return mAadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        mAadharNumber = aadharNumber;
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    public String getVendorName() {
        return mVendorName;
    }

    public void setVendorName(String vendorName) {
        mVendorName = vendorName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        mLatLng = latLng;
    }

    public boolean isSabziwala() {
        return mSabziwala;
    }

    public void setSabziwala(boolean sabziwala) {
        mSabziwala = sabziwala;
    }

    public boolean isFruitWala() {
        return mFruitWala;
    }

    public void setFruitWala(boolean fruitWala) {
        mFruitWala = fruitWala;
    }
}
