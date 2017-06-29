package com.naanizcustomer.naaniz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hypertrack.lib.HyperTrack;
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.models.Items;

import java.util.ArrayList;

/**
 * Created by hemba on 6/19/2017.
 */

public class ItemsRecycyclerViewAdapter extends RecyclerView.Adapter<ItemsRecycyclerViewAdapter.ItemViewHolder> {
    private ArrayList<Items> mItemses;
    private Context mContext;

    public ItemsRecycyclerViewAdapter(ArrayList<Items> itemses, Context context) {
        mItemses = itemses;
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_row_layout,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.mItemNameTV.setText(mItemses.get(position).getItemName());
        holder.mPriceTV.setText("Rs. "+mItemses.get(position).getPrice()+"/"+mItemses.get(position).getPriceType());
        holder.mCategoryTV.setText(mItemses.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return mItemses.size();
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView mItemNameTV,mPriceTV,mCategoryTV;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemNameTV=(TextView)itemView.findViewById(R.id.item_row_name);
            mPriceTV=(TextView)itemView.findViewById(R.id.item_row_price);
            mCategoryTV=(TextView)itemView.findViewById(R.id.item_row_category);
        }
    }
}
