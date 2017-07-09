package com.naanizcustomer.naaniz.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.naanizcustomer.naaniz.R;
import com.naanizcustomer.naaniz.activities.LandingActivity;
import com.naanizcustomer.naaniz.activities.MyOrdersActivity;
import com.naanizcustomer.naaniz.database.DbHelper;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by hemba on 6/24/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();


    public MyFirebaseMessagingService() {
        super();
        Log.d("MS","Started");    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
        }
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                String type = json.getJSONObject("data").getString("Type");
                if(type.equals("2")){
                    handleDataMessage(json.getJSONObject("data"));
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            Log.d("datasadsa",json.toString());

            if (!isAppIsInBackground(getApplicationContext())) {
                String status = json.getString("Status");
                if(status.equals("Accepted")){
                    String actionID = json.getString("ActionId");
                    String vendorName = json.getString("VendorName");
                    String vendorLookupID = json.getString("VendorLookupId");
                    DbHelper dbHelper = new DbHelper(getApplicationContext());
                    dbHelper.setAcceptedAndVendorDetails(actionID, vendorName, vendorLookupID);
                    sendNotification(json.getString("VendorName") + " has accepted the order", "Accepted");
                }

            } else {
                String status = json.getString("Status");
                if(status.equals("Accepted")){
                    String actionID = json.getString("ActionId");
                    String vendorName = json.getString("VendorName");
                    String vendorLookupID = json.getString("VendorLookupId");
                    DbHelper dbHelper = new DbHelper(getApplicationContext());
                    dbHelper.setAcceptedAndVendorDetails(actionID, vendorName, vendorLookupID);
                    sendNotification(json.getString("VendorName") + " has accepted the order", "Accepted");
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }
        return isInBackground;
    }

    private void sendNotification(String messageBody, String heading) {
        Intent intent = new Intent(this, MyOrdersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new android.support.v7.app.NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(heading)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }}
