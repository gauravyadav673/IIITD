package com.naanizcustomer.naaniz.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.naanizcustomer.naaniz.models.Order;

import java.util.ArrayList;

/**
 * Created by hemba on 6/18/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NaanizCustomer.db";
    private Context mContext;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbUtils.CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveOrder(Order order) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.clear();
        values.put(Schema.OrderSchema.ORDER_ACTION_ID, order.getActionID());
        values.put(Schema.OrderSchema.ORDER_ITEM_NAME, order.getItemName());
        values.put(Schema.OrderSchema.ORDER_ITEM_CATEGORY, order.getItemCategory());
        values.put(Schema.OrderSchema.ORDER_SCHEDULED_AT, order.getScheduledAt());
        values.put(Schema.OrderSchema.ORDER_VENDOR_NAME, order.getVendorName());
        values.put(Schema.OrderSchema.ORDER_VENDOR_LOOKUPID, order.getVendorLookupID());
        values.put(Schema.OrderSchema.ORDER_DISPATCHED, String.valueOf(order.isDispatched()));
        values.put(Schema.OrderSchema.ORDER_COMPLETED, String.valueOf(order.isCompleted()));
        values.put(Schema.OrderSchema.ORDER_CONFIRMED, String.valueOf(order.isConfirmed()));
        values.put(Schema.OrderSchema.ORDER_ACCEPTED, String.valueOf(order.isAccepted()));
        db.insert(Schema.OrderSchema.ORDERS_TABLE_NAME, null, values);
        Log.d("inserted", " ");
        db.close();
    }

    public ArrayList<Order> retrieveOrders() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Order> mOrders=new ArrayList<>();
        String projection[] = {Schema.OrderSchema.ORDER_ACTION_ID,
                Schema.OrderSchema.ORDER_ITEM_NAME,
                Schema.OrderSchema.ORDER_ITEM_CATEGORY,
                Schema.OrderSchema.ORDER_SCHEDULED_AT,
                Schema.OrderSchema.ORDER_VENDOR_NAME,
                Schema.OrderSchema.ORDER_VENDOR_LOOKUPID,
                Schema.OrderSchema.ORDER_DISPATCHED,
                Schema.OrderSchema.ORDER_COMPLETED,
                Schema.OrderSchema.ORDER_CONFIRMED,
                Schema.OrderSchema.ORDER_ACCEPTED
        };
        Cursor cursor = db.query(Schema.OrderSchema.ORDERS_TABLE_NAME, projection, null, null, null, null, null);
        int l = cursor.getCount();
        cursor.moveToFirst();
        while(l>0)
        {
            l--;
            String actionID = cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_ACTION_ID));
            String itemName = cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_ITEM_NAME));
            String itemCategory = cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_ITEM_CATEGORY));
            String scheduledAt = cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_SCHEDULED_AT));
            String vendorName = cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_VENDOR_NAME));
            String vendorLookup = cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_VENDOR_LOOKUPID));
            String dispatched = cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_DISPATCHED));
            String delivered  =cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_COMPLETED));
            String confirmed = cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_CONFIRMED));
            String accepted = cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_ACCEPTED));
            Boolean isDispatched = Boolean.valueOf(dispatched);
            Boolean isDelivered = Boolean.valueOf(delivered);
            Boolean isConfirmed = Boolean.valueOf(confirmed);
            Boolean isAccepted = Boolean.valueOf(accepted);
            mOrders.add(new Order(itemName, itemCategory, actionID, scheduledAt, vendorName, vendorLookup, isDispatched, isDelivered, isAccepted, isConfirmed));
            cursor.moveToNext();
        }
        db.close();
        return  mOrders;
    }

    public void setAcceptedAndVendorDetails(String actionID, String VendorName, String VendorLookupID){
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.clear();
            try{
                contentValues.put(Schema.OrderSchema.ORDER_VENDOR_NAME, VendorName);
                contentValues.put(Schema.OrderSchema.ORDER_VENDOR_LOOKUPID, VendorLookupID);
                contentValues.put(Schema.OrderSchema.ORDER_ACCEPTED, String.valueOf(true));
                db.update(Schema.OrderSchema.ORDERS_TABLE_NAME, contentValues, Schema.OrderSchema.ORDER_ACTION_ID+"=\""+actionID+"\"", null);
            }catch (Exception e){
                Log.d("CurrentError", e.toString());
        }
    }

    public void setConfirmed(String actionID){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.clear();
        try{
            contentValues.put(Schema.OrderSchema.ORDER_CONFIRMED, String.valueOf(true));
            db.update(Schema.OrderSchema.ORDERS_TABLE_NAME, contentValues, Schema.OrderSchema.ORDER_ACTION_ID+"=\""+actionID+"\"", null);
        }catch (Exception e){
            Log.d("CurrentError", e.toString());
        }
    }

    public void clearOrdersTable(){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(DbUtils.DROP_ORDERS_TABLE);
        database.execSQL(DbUtils.CREATE_ORDERS_TABLE);
    }
}
