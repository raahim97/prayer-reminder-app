package com.example.mad.prayerreminderapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.io.InputStream;

import static android.content.Context.NOTIFICATION_SERVICE;

//import static androidx.core.graphics.drawable.IconCompat.getResources;

public class PrayerReminderReceiver extends BroadcastReceiver
{
    private final String TAG = "key123";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "Prayer time", Toast.LENGTH_LONG).show();
//        Uri alarmUri = Uri.parse("android.resource://" + context.getPackageName() + "/raw/azan1.mp3");



//        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                   + "://" + context.getPackageName()+ "/raw/azan1");
        Log.d(TAG,"uri:"+alarmSound.toString());
        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder noti_build = new Notification.Builder(context.getApplicationContext());
        noti_build.setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Its prayer time")
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setStyle(new Notification.BigTextStyle()
                        .bigText("Salah Time"));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "REMINDERS";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Prayer Reminder",
                    NotificationManager.IMPORTANCE_HIGH);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            channel.setSound(alarmSound, audioAttributes);
            manager.createNotificationChannel(channel);
            noti_build.setChannelId(channelId);
        }
        manager.notify(1, noti_build.build());



//        Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                   + "://" + context.getPackageName()+ "/raw/azan1");
//        Log.d(TAG,"uri:"+alarmSound.toString());
//
//        if (alarmSound == null)
//        {
//            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        }
//        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmSound);
//        ringtone.play();
    }
}

