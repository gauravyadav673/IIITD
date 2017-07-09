package com.naanizcustomer.naaniz.database;

import com.naanizcustomer.naaniz.interfaces.DbConstUtils;


/**
 * Created by hemba on 6/18/2017.
 */

public class DbUtils implements DbConstUtils{

    public static final String CREATE_ORDERS_TABLE=CREATE_TABLE+IF_NOT_EXISTS+ Schema.OrderSchema.ORDERS_TABLE_NAME+LEFT_BRACKET
            + Schema.OrderSchema.ORDER_ACTION_ID+TEXT+PRIMARY_KEY+COMMA_SEP
            + Schema.OrderSchema.ORDER_ITEM_NAME+TEXT+COMMA_SEP
            + Schema.OrderSchema.ORDER_ITEM_CATEGORY+TEXT+COMMA_SEP
            + Schema.OrderSchema.ORDER_SCHEDULED_AT+TEXT+COMMA_SEP
            + Schema.OrderSchema.ORDER_VENDOR_NAME+TEXT+COMMA_SEP
            + Schema.OrderSchema.ORDER_VENDOR_LOOKUPID+TEXT+COMMA_SEP
            + Schema.OrderSchema.ORDER_DISPATCHED+TEXT+COMMA_SEP
            +Schema.OrderSchema.ORDER_CONFIRMED+TEXT+COMMA_SEP
            +Schema.OrderSchema.ORDER_ACCEPTED+TEXT+COMMA_SEP
            + Schema.OrderSchema.ORDER_COMPLETED+TEXT
            +RIGHT_BRACKET;

    public static final String DROP_ORDERS_TABLE = DROP_TABLE + Schema.OrderSchema.ORDERS_TABLE_NAME + "'";
}