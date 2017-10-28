package com.naanizcustomer.naaniz.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.naanizcustomer.naaniz.app.Config;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hemba on 6/14/2017.
 */

public class Util {
    public static void toastS(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static ProgressDialog getProgDialog(Context context,String title,String msg,boolean cancelable){
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(cancelable);
        progressDialog.setTitle(title);
        return progressDialog;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public static boolean isNetConnected(Context context){
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean b=(networkInfo!=null&&networkInfo.isConnected());
        Log.d("Internet Connectivity:",""+b);
        return b;
    }
    public java.util.Date dateFromString() {


        String dtStart = "2010-10-15T09:27:37Z";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date=new Date();
        try {
        date = format.parse(dtStart);
            System.out.println(date);
        } catch (java.text.ParseException e1) {
            e1.printStackTrace();
        }
        return date;
    }
    private String stringFromDate() {


        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date = new Date();
        String datetime=null;
        try {
            datetime = dateformat.format(date);
            System.out.println("Current Date Time : " + datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return datetime;
    }
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static String getScheduleDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        String time = String.valueOf(calendar.getTimeInMillis());
        Log.d("time", time);
        return time;
    }

}


///make post request with raw data like this {title:'asd',message:'aas',for:'asd',type:'req',regid:'elt45VFF0n8:APA91bHwv4LAYAvQBeJ9YCtmashYshPgMlanueIGyWB_RcMoK0YMF1mUS5ju9MPqwJlfjaMKbR8J6n7Abt7fXMQeU073oaer0BEIWz_MlmsIS6ug7ebkQYOo4E8ft_wUmQ9n6sC7W4F9'}