package com.naanizcustomer.naaniz.callbacks;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hypertrack.lib.HyperTrackMapFragment;
import com.hypertrack.lib.MapFragmentCallback;
import com.hypertrack.lib.models.Action;
import com.naanizcustomer.naaniz.utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hemba on 6/17/2017.
 */
public class MyMapFragmentCallback extends MapFragmentCallback {
    private Context mContext;
    private String mVendors;

    public MyMapFragmentCallback(Context context, String vendors) {
        mContext = context;
        mVendors = vendors;
    }

    @Override
    public void onMapReadyCallback(HyperTrackMapFragment hyperTrackMapFragment, GoogleMap map) {
        //Write your code here
        super.onMapReadyCallback(hyperTrackMapFragment, map);

        try {
            JSONArray data = new JSONArray(mVendors);
            for (int i = 0 ; i < data.length() ; i++){
                JSONObject oneVendor = data.getJSONObject(i);
                LatLng ltlng = new LatLng(oneVendor.getDouble("LastLocationLong"), oneVendor.getDouble("LastLocationLat"));
                String vendorName = "Vendor";
                Log.d("latlang", ltlng.toString());
                hyperTrackMapFragment.addCustomMarker(new MarkerOptions().position(ltlng)
                        .title(vendorName).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*        try {
            JSONArray jsonArray=new JSONArray(mVendors);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                JSONArray jsonArray1=jsonObject.getJSONArray("Location");
                JSONObject jsonObject1=jsonArray1.getJSONObject(0);
                String lat=jsonObject1.getString("lat");
                String lng=jsonObject1.getString("long");
                Double lt=Double.parseDouble(lat);
                Double lg=Double.parseDouble(lng);
                LatLng latLng=new LatLng(lt,lg);
                String mVendorName=jsonObject.getString("VendorName");
                hyperTrackMapFragment.addCustomMarker(new MarkerOptions().position(latLng)
                        .title(mVendorName).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*/



    }

    @Override
    public void onCustomMarkerClicked(HyperTrackMapFragment hyperTrackMapFragment, Marker customMarker) {
        customMarker.showInfoWindow();
        Util.toastS(mContext,"Marker");

        super.onCustomMarkerClicked(hyperTrackMapFragment, customMarker);

    }

    /**
     * Called when status of any action changes during live order tracking.
     * @param changedStatusActionIds The List of ActionIDs for which the status changed.
     * @param changedStatusActions The List of Actions for which the status changed.
     */
    @Override
    public void onActionStatusChanged(List<String> changedStatusActionIds,
                                      List<Action> changedStatusActions) {
        //Write your code here
        super.onActionStatusChanged(changedStatusActionIds, changedStatusActions);
    }

    /**
     * Called when action refreshed during live order tracking.
     *  @param refreshedActionIds is the list of action IDs that are refreshed
     *  @param refreshedActions The List of refreshed Actions.
     */
    @Override
    public void onActionRefreshed(List<String> refreshedActionIds, List<Action> refreshedActions) {
        //Write your code here
        super.onActionRefreshed(refreshedActionIds, refreshedActions);
    }
}