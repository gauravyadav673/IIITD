package com.naanizcustomer.naaniz.adapters;

import android.content.Context;

import com.hypertrack.lib.HyperTrackMapAdapter;

/**
 * Created by hemba on 6/17/2017.
 */

public class TrackingMapAdapter extends HyperTrackMapAdapter {

    private Context mContext;

    public TrackingMapAdapter(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    /*
    * Show the trailing polyline of driver
    * */
    @Override
    public boolean showTrailingPolyline() {
        return true;
    }

}
