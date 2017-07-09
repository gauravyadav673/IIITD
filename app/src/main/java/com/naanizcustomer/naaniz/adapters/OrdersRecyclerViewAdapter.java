package com.naanizcustomer.naaniz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import com.hypertrack.lib.models.ErrorResponse;
import com.hypertrack.lib.models.SuccessResponse;
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.activities.OrderTrackingActivity;
import com.naanizcustomer.naaniz.app.Config;
import com.naanizcustomer.naaniz.database.DbHelper;
import com.naanizcustomer.naaniz.models.Order;
import com.naanizcustomer.naaniz.utils.SharedPrefUtil;
import com.naanizcustomer.naaniz.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hemba on 7/7/2017.
 */

public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<OrdersRecyclerViewAdapter.OrderViewHolder> {
    private Context mContext;
    private ArrayList<Order> mOrders;
    private RequestQueue requestQueue;
    private SharedPrefUtil sharedPrefUtil;
    private DbHelper dbHelper;

    public OrdersRecyclerViewAdapter(Context context, ArrayList<Order> orders) {
        mContext = context;
        mOrders = orders;
        requestQueue = Volley.newRequestQueue(context);
        sharedPrefUtil = new SharedPrefUtil(context);
        dbHelper = new DbHelper(context);
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_row_layout, parent,false );
        OrderViewHolder viewHolder = new OrderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, final int position) {
        holder.mOrderNoTV.setText("" + (position + 1));
        holder.mOrderNameTV.setText(mOrders.get(position).getItemName());
        holder.mOrderCatTV.setText(mOrders.get(position).getItemCategory());
        boolean bAccepted = mOrders.get(position).isAccepted();
        boolean bConfirmed = mOrders.get(position).isConfirmed();
        if (bAccepted && bConfirmed) {
            holder.mOrderTrackButton.setEnabled(true);
            holder.mOrderTrackButton.setText("Track Order");
            holder.mOrderTrackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String actionID = mOrders.get(position).getActionID();
                    trackAction(actionID);
                }
            });
        } else if(bAccepted){
            holder.mOrderTrackButton.setText("Confirm");
            holder.mOrderTrackButton.setEnabled(true);
            holder.mOrderTrackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmOrder(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mOrders.size();
    }

    protected class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView mOrderNoTV, mOrderNameTV, mOrderCatTV;
        Button mOrderTrackButton;

        public OrderViewHolder(View itemView) {
            super(itemView);
            mOrderNameTV = (TextView) itemView.findViewById(R.id.order_row_item_name);
            mOrderNoTV = (TextView) itemView.findViewById(R.id.order_row_order_number);
            mOrderCatTV = (TextView) itemView.findViewById(R.id.order_row_item_category);
            mOrderTrackButton = (Button) itemView.findViewById(R.id.order_row_track_btn);
        }
    }

    private void trackAction(final String action_id) {
        // Call trackAction API method with action ID for tracking.
        // Start YourMapActivity containing HyperTrackMapFragment view with the
        // customization on succes response of trackAction method
        ArrayList<String> actions = new ArrayList<>();
        actions.add(action_id);
        HyperTrack.trackAction(actions, new HyperTrackCallback() {
            @Override
            public void onSuccess(@NonNull SuccessResponse response) {
                //Start Activity containing HyperTrackMapFragment
                Intent intent = new Intent(mContext, OrderTrackingActivity.class);
                intent.putExtra(Config.INTENT_KEY_ACTION_ID,action_id);
                mContext.startActivity(intent);
            }

            @Override
            public void onError(@NonNull ErrorResponse errorResponse) {

                Util.toastS(mContext, "Error Occurred while tracking action: " +
                        errorResponse.getErrorMessage());

            }
        });
    }

    private void confirmOrder(final int Position){
        String url = Config.API_URL + Config.ORDER + Config.CONFIRM_ORDER;
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Confirm Response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            String success = jsonArray.getJSONObject(0).getString("success");
                            if(success.equals("1")){
                                dbHelper.setConfirmed(mOrders.get(Position).getActionID());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Confirm Error", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("actionid", mOrders.get(Position).getActionID());
                params.put("customername", sharedPrefUtil.getCustomerDetails().getName());
                params.put("lookupid", mOrders.get(Position).getVendorLookupID());
                params.put("customer", String.valueOf(sharedPrefUtil.getCustomerDetails().getContact()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

