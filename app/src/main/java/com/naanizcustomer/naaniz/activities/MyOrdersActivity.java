package com.naanizcustomer.naaniz.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.adapters.OrdersRecyclerViewAdapter;
import com.naanizcustomer.naaniz.app.Config;
import com.naanizcustomer.naaniz.database.DbHelper;
import com.naanizcustomer.naaniz.models.Order;
import com.naanizcustomer.naaniz.utils.SharedPrefUtil;
import com.naanizcustomer.naaniz.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {
    private  DbHelper mDbHelper;
    private RecyclerView mOrderRv;
    private SharedPrefUtil sharedPrefUtil;
    ArrayList<Order> orders;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        mOrderRv=(RecyclerView)findViewById(R.id.my_orders_rv);
        mDbHelper=new DbHelper(MyOrdersActivity.this);
        sharedPrefUtil = new SharedPrefUtil(getApplicationContext());
        pd = Util.getProgDialog(MyOrdersActivity.this, "Wait", "loading...", false);
        pd.show();
        getAllOrders();
        ArrayList<Order> orders=mDbHelper.retrieveOrders();
        initRv(orders);
    }
    private void initRv(ArrayList<Order> orders) {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MyOrdersActivity.this,LinearLayoutManager.VERTICAL,false);
        mOrderRv.setLayoutManager(linearLayoutManager);
        mOrderRv.setAdapter(new OrdersRecyclerViewAdapter(MyOrdersActivity.this,orders));
    }

    private void getAllOrders(){
        SharedPrefUtil sharedPrefUtil=new SharedPrefUtil(MyOrdersActivity.this);
        String number=sharedPrefUtil.getCustomerDetails().getContact();
        String url= Config.API_URL+Config.ORDER+ Config.GET_ORDERS+"/"+number;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("orders of buyer :",response);
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    JSONObject check=jsonArray.getJSONObject(0);
                    if(check.getInt("success")==1){
                        mDbHelper.clearOrdersTable();
                        JSONObject data=jsonArray.getJSONObject(1);
                        JSONArray allOrders=data.getJSONArray("data");
                        int n=allOrders.length();
                        orders=new ArrayList<>();
                        for(int i=0;i<n;i++){
                            JSONObject orderN=allOrders.getJSONObject(i);
                            String actionId=orderN.getString("ActionID");
                            String scheduledAt=orderN.getString("ScheduledAt");
/*
                            String assigned=orderN.getString("Assigned");
*/
                            String status=orderN.getString("Status");
                            String buyer=orderN.getString("Buyer");
                            String category=orderN.getString("Category");
                            String itemName=orderN.getString("ItemName");
/*                            String latLoc=orderN.getString("Lat");
                            String longLoc=orderN.getString("Long");
                            String address=orderN.getString("Address");*/
                            String vendorName = orderN.getJSONObject("Vendor").getString("Name");
                            String vendorLookupID=orderN.getJSONObject("Vendor").getString("LookupId");
                            boolean isDispatched=false, isCompleted=false, isAccepted=false, isConfirmed=false;
                            if(status.equals(Config.mOrderStatusDispatched)){
                                isAccepted=true;
                                isConfirmed=true;
                                isDispatched=true;
                            }else if(status.equals(Config.mOrderStatusCompleted)){
                                isCompleted=true;
                                isAccepted=true;
                                isConfirmed=true;
                                isDispatched=true;
                            }else if(status.equals(Config.mOrderStatusAccepted)){
                                isAccepted=true;
                            }else if(status.equals(Config.mOrderStatusConfirmed)){
                                isConfirmed=true;
                                isAccepted=true;
                            }
                            Order order=new Order(itemName, category, actionId, scheduledAt, vendorName, vendorLookupID, isDispatched, isCompleted, isAccepted, isConfirmed);
                            mDbHelper.saveOrder(order);
                            orders.add(order);

                        }
                        Util.toastS(MyOrdersActivity.this, "Orders updated");
                        pd.dismiss();
                        initRv(orders);
                    }else{
                        Util.toastS(MyOrdersActivity.this,"Error getting orders");
                        pd.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pd.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("orders of buyer error:",error.toString());

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(MyOrdersActivity.this);
        requestQueue.add(stringRequest);

    }

}
