package com.naanizcustomer.naaniz.database;

import com.naanizcustomer.naaniz.interfaces.DbConstUtils;


/**
 * Created by hemba on 6/18/2017.
 */

public class DbUtils implements DbConstUtils{

    public static final String CREATE_ORDERS_TABLE=CREATE_TABLE+IF_NOT_EXISTS+ Schema.OrderSchema.ORDERS_TABLE_NAME+LEFT_BRACKET
            + Schema.OrderSchema._ID+INTEGER+PRIMARY_KEY+COMMA_SEP
            + Schema.OrderSchema.ORDER_NAME+TEXT+COMMA_SEP
            + Schema.OrderSchema.ORDER_CATEGORY+TEXT+COMMA_SEP
            + Schema.OrderSchema.ORDER_PRICE+TEXT+COMMA_SEP
            + Schema.OrderSchema.ORDER_QUANTITY+TEXT+COMMA_SEP
            + Schema.OrderSchema.ORDER_VENDOR_AADHAAR+TEXT+COMMA_SEP
            +RIGHT_BRACKET;
}