package com.naanizcustomer.naaniz.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.adapters.OrdersRecyclerViewAdapter;
import com.naanizcustomer.naaniz.database.DbHelper;
import com.naanizcustomer.naaniz.models.Order;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {
    private  DbHelper mDbHelper;
    private RecyclerView mOrderRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        mOrderRv=(RecyclerView)findViewById(R.id.my_orders_rv);
        mDbHelper=new DbHelper(MyOrdersActivity.this);
        ArrayList<Order> orders=mDbHelper.retrieveOrders();
        initRv(orders);
    }
    private void initRv(ArrayList<Order> orders) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MyOrdersActivity.this,LinearLayoutManager.VERTICAL,false);
        mOrderRv.setLayoutManager(linearLayoutManager);
        mOrderRv.setAdapter(new OrdersRecyclerViewAdapter(MyOrdersActivity.this,orders));
    }

}
