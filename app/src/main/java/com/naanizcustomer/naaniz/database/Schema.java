package com.naanizcustomer.naaniz.database;

import android.provider.BaseColumns;

/**
 * Created by hemba on 6/18/2017.
 */

public class Schema {
    public Schema() {
    }

    public static class OrderSchema implements BaseColumns {
        public static final String ORDERS_TABLE_NAME = "Orders";
        public static final String ORDER_ITEM_NAME = "Item_Name";
        public static final String ORDER_ITEM_CATEGORY = "Item_Category";
        public static final String ORDER_ACTION_ID = "ActionID";
        public static final String ORDER_SCHEDULED_AT = "ScheduledAt";
        public static final String ORDER_VENDOR_NAME = "Vendor_Name";
        public static final String ORDER_VENDOR_LOOKUPID = "Vendor_LookupID";
        public static final String ORDER_DISPATCHED = "IsDispatched";
        public static final String ORDER_COMPLETED = "IsCompleted";

    }

}
