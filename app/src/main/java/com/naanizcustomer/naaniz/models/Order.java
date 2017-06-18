package com.naanizcustomer.naaniz.models;

/**
 * Created by hemba on 6/18/2017.
 */

public class Order {
    private String mOrderName,mOrderCategory;
    private Double mOrderPrice,mOrderQuantity;
    private Integer mVendorAadhaar;

    public Order(String orderName, String orderCategory, Double orderPrice, Double orderQuantity, Integer vendorAadhaar) {
        mOrderName = orderName;
        mOrderCategory = orderCategory;
        mOrderPrice = orderPrice;
        mOrderQuantity = orderQuantity;
        mVendorAadhaar = vendorAadhaar;
    }

    public String getOrderName() {
        return mOrderName;
    }

    public void setOrderName(String orderName) {
        mOrderName = orderName;
    }

    public String getOrderCategory() {
        return mOrderCategory;
    }

    public void setOrderCategory(String orderCategory) {
        mOrderCategory = orderCategory;
    }

    public Double getOrderPrice() {
        return mOrderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        mOrderPrice = orderPrice;
    }

    public Double getOrderQuantity() {
        return mOrderQuantity;
    }

    public void setOrderQuantity(Double orderQuantity) {
        mOrderQuantity = orderQuantity;
    }

    public Integer getVendorAadhaar() {
        return mVendorAadhaar;
    }

    public void setVendorAadhaar(Integer vendorAadhaar) {
        mVendorAadhaar = vendorAadhaar;
    }
}
