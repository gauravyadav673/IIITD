package com.naanizcustomer.naaniz.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.naanizcustomer.naaniz.app.Config;

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

}


