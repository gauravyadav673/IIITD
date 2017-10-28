package com.naanizcustomer.naaniz.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.activities.TrackingActivity;
import com.naanizcustomer.naaniz.app.Config;
import com.naanizcustomer.naaniz.models.Items;
import com.naanizcustomer.naaniz.utils.SharedPrefUtil;
import com.naanizcustomer.naaniz.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.hypertrack.lib.internal.consumer.ConsumerClient.TAG;

/**
 * Created by hemba on 6/19/2017.
 */

public class ItemsRecycyclerViewAdapter extends RecyclerView.Adapter<ItemsRecycyclerViewAdapter.ItemViewHolder> {
    private ArrayList<Items> mItemses;
    private Context mContext;
    private ProgressDialog mOrderProgress;
    private RequestQueue mRequestQueue;
    private SharedPrefUtil mSharedPrefUtil;
    private String scheduledAt;
    ProgressDialog pd;

    public ItemsRecycyclerViewAdapter(ArrayList<Items> itemses, Context context) {
        mItemses = itemses;
        mContext = context;
        mOrderProgress=new ProgressDialog(context);
        mOrderProgress.setMessage("We are processing your order please wait!");
        mOrderProgress.setCancelable(false);
        mRequestQueue= Volley.newRequestQueue(context);
        mSharedPrefUtil = new SharedPrefUtil(context);
        pd = Util.getProgDialog(context, "Wait", "loading...", false);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_row_layout,parent,false);
        Log.d("address", mSharedPrefUtil.getCustomerDetails().getAddress());

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        holder.mItemNameTV.setText(mItemses.get(position).getItemName());
        holder.mPriceTV.setText("Rs. "+mItemses.get(position).getPrice()+"/"+mItemses.get(position).getPriceType());
        holder.mCategoryTV.setText(mItemses.get(position).getCategory());
        holder.mOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isNetConnected(mContext)){
                    pd.show();
                    getVendors("67efa3ce-2d46-4ddd-9306-3c7644055e82",position);
                    //Util.toastS(mContext,"Placing Order");
                }else{
                    Util.toastS(mContext,"Connect to internet");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemses.size();
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView mItemNameTV,mPriceTV,mCategoryTV;
        Button mOrderBtn;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemNameTV=(TextView)itemView.findViewById(R.id.item_row_name);
            mPriceTV=(TextView)itemView.findViewById(R.id.item_row_price);
            mCategoryTV=(TextView)itemView.findViewById(R.id.item_row_category);
            mOrderBtn=(Button)itemView.findViewById(R.id.item_row_order_btn);
        }
    }

/*    private void createAction(final int position){
        String url = Config.API_URL + Config.ACTIONS_URL +Config.CREATE_ACTIONS_URL;
        Log.d("App", url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("respons", response);
                        try {
                            JSONArray result = new JSONArray(response);
                            if(result.getJSONObject(0).getString("success").equals("1")){
                                String actionId = result.getJSONObject(1).getString("action_id");
                                getVendors(actionId, position);
                                pd.dismiss();

                            }else{
                                Util.toastS(mContext,"Error ordering!");
                                pd.dismiss();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Util.toastS(mContext,"Server Error");
                            pd.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.toastS(mContext, "Error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                scheduledAt = Util.getScheduleDate();
                Map<String, String> params = new HashMap<>();
                params.put("scheduledat", scheduledAt);
                params.put("lat", String.valueOf(mSharedPrefUtil.getCustomerDetails().getLatLng().latitude));
                params.put("long", String.valueOf(mSharedPrefUtil.getCustomerDetails().getLatLng().longitude));
                params.put("address", mSharedPrefUtil.getCustomerDetails().getAddress());
                Log.d("App", mSharedPrefUtil.getCustomerDetails().getAddress());
                return params;
            }
        };
        mRequestQueue.add(stringRequest);
    }
*/
/*    private void notifyVendors(final String actionID, final int position){
        String url = Config.API_URL + Config.ORDER + Config.NOTIFY_VENDORS;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        Util.toastS(mContext, "Ordered");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.toastS(mContext, "Error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("actionid", actionID);
                params.put("itemname", mItemses.get(position).getItemName());
                return params;
            }
        };
        mRequestQueue.add(stringRequest);
    }*/

        private void getVendors(final String actionID, final int position){
            String url=Config.API_URL+Config.VENDORS_URL+Config.NEARBY_VENDORS;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("resp", response);
                            parseVendorResponse(response, actionID, position);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.toastS(mContext, "fuck");
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("actionid", "67efa3ce-2d46-4ddd-9306-3c7644055e82");
                    params.put("itemname", mItemses.get(position).getItemName());
                    return params;
                }
            };
            mRequestQueue.add(stringRequest);
        }

        private void parseVendorResponse(String result, String actionID, int posi){
            try {
                JSONArray response = new JSONArray(result);
                Log.d("App", response.toString());
                if(response.getJSONObject(0).getString("success").equals("1")){
                    JSONArray data = response.getJSONArray(2);
                    String VedorData = data.toString();
                    Log.d("response1", VedorData);
                    Intent intent = new Intent(mContext, TrackingActivity.class);
                    intent.putExtra("vendors", VedorData);
                    intent.putExtra("actionID", actionID);
                    intent.putExtra("itemName", mItemses.get(posi).getItemName());
                    intent.putExtra("category", mItemses.get(posi).getCategory());
                    intent.putExtra("scheduledat", scheduledAt);
                    intent.putExtra(Config.INTENT_STATUS,Config.INTENT_STATUS_ORDERING);
                    mContext.startActivity(intent);
                }else{
                    Util.toastS(mContext, "Server error");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

}
