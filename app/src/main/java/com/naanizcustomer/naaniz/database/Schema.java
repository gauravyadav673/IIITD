package com.naanizcustomer.naaniz.database;

import android.provider.BaseColumns;

/**
 * Created by hemba on 6/18/2017.
 */

public class Schema {
    public Schema() {
    }

    public static class OrderSchema implements BaseColumns {
        public static final String ORDERS_TABLE_NAME = "Customers";
        public static final String ORDER_NAME = "Customer_Name";
        public static final String ORDER_VENDOR_AADHAAR = "Customer_Contact";
        public static final String ORDER_CATEGORY = "Customer_Lat";
        public static final String ORDER_PRICE = "Customer_Long";
        public static final String ORDER_QUANTITY = "Customer_Address";
        public static final String ORDER_DATE = "orderDate";
    }

}
