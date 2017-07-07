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
                long time = remoteMessage.getSentTime();

                handleDataMessage(json,time);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleDataMessage(JSONObject json,long time) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            Log.d("datasadsa",json.toString());
            //JSONObject data = json.getJSONObject("data");

            if (!isAppIsInBackground(getApplicationContext())) {
                //foreground
                Intent resultIntent = new Intent(getApplicationContext(), LandingActivity.class);
                resultIntent.putExtra("message", json.toString());
                PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 7190, resultIntent, PendingIntent.FLAG_ONE_SHOT);
                showNotification(getApplicationContext(), intent, json.toString(), "title");
                //showNotification(getApplicationContext(),null,message,title);
                /*DbHelper dbHelper = new DbHelper(getApplicationContext());
                dbHelper.saveNotification(title, message, tTime, tDate);*/
            } else {
                Intent resultIntent = new Intent(getApplicationContext(), LandingActivity.class);
                resultIntent.putExtra("message", json.toString());
                PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 7190, resultIntent, PendingIntent.FLAG_ONE_SHOT);
                showNotification(getApplicationContext(), intent, json.toString(), "title");

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

    private void showNotification(Context context,PendingIntent intent,String mssg,String head){

        Notification.Builder builder = new Notification.Builder(context);
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(mssg);
        long pattern[] = {100,200,300,400};
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new Notification();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification = builder.
                    setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("Elements Culmyca").setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(head)
                    .setContentIntent(intent)
                    .setSound(defaultSoundUri)

                    .setVibrate(pattern)
                    .setContentText(mssg)
                    .build();
        }
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(7190, notification);
    }
}
