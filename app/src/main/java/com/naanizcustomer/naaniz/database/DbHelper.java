package com.naanizcustomer.naaniz.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.naanizcustomer.naaniz.models.Customer;
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
        values.put(Schema.OrderSchema.ORDER_NAME, order.getOrderName());
        values.put(Schema.OrderSchema.ORDER_CATEGORY, order.getOrderCategory());
        values.put(Schema.OrderSchema.ORDER_PRICE, order.getOrderPrice());
        values.put(Schema.OrderSchema.ORDER_QUANTITY, order.getOrderQuantity());
        values.put(Schema.OrderSchema.ORDER_VENDOR_AADHAAR, order.getVendorAadhaar());
        db.insert(Schema.OrderSchema.ORDERS_TABLE_NAME, null, values);

        db.close();
    }

    public ArrayList<Order> retrieveOrders() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Order> mOrders=new ArrayList<>();
        String projection[] = {Schema.OrderSchema.ORDER_NAME,
                Schema.OrderSchema.ORDER_CATEGORY,
                Schema.OrderSchema.ORDER_PRICE,
                Schema.OrderSchema.ORDER_QUANTITY,
                Schema.OrderSchema.ORDER_VENDOR_AADHAAR,};
        Cursor cursor = db.query(Schema.OrderSchema.ORDERS_TABLE_NAME, projection, null, null, null, null, null);
        int l = cursor.getCount();
        cursor.moveToFirst();
        while(l>0)
        {
            l--;
            String name = cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_NAME));
            String category=cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_CATEGORY));
            String price=cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_CATEGORY));
            String quantity=cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_CATEGORY));
            String vendor_aadhaar=cursor.getString(cursor.getColumnIndexOrThrow(Schema.OrderSchema.ORDER_CATEGORY));
            Double mPrice=Double.parseDouble(price);
            Double mQuantity=Double.parseDouble(quantity);
            Integer mAadhaar=Integer.parseInt(vendor_aadhaar);
            mOrders.add(new Order(name,category,mPrice,mQuantity,mAadhaar));
            cursor.moveToNext();
        }
        db.close();

        return  mOrders;
    }





}
