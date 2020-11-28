package com.example.mad.prayerreminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TimePicker timePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    SQLiteDatabase db;
    Button btn_fajr, btn_zuhr, btn_asr, btn_maghrib, btn_isha;
    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        btn_fajr = findViewById(R.id.button);
        btn_zuhr = findViewById(R.id.button2);
        btn_asr = findViewById(R.id.button3);
        btn_maghrib = findViewById(R.id.button4);
        btn_isha = findViewById(R.id.button5);
        timePicker = findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        btn_fajr.setOnClickListener(this);
        btn_zuhr.setOnClickListener(this);
        btn_asr.setOnClickListener(this);
        btn_maghrib.setOnClickListener(this);
        btn_isha.setOnClickListener(this);
        db=openOrCreateDatabase("PrayerTimeDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS PrayerTime(prayer VARCHAR,time VARCHAR);");
        getAllData();

    }
    public void onClick(View view) {
        if (view == btn_fajr) {

            Toast.makeText(MainActivity.this, "Prayer Time set", Toast.LENGTH_SHORT).show();
            Cursor c=db.rawQuery("SELECT * FROM PrayerTime WHERE prayer='Fajr'", null);
            if(c.moveToFirst())
            {
                try{
                    db.execSQL("UPDATE PrayerTime SET time='" + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + "' WHERE prayer='Fajr'");
                    Toast.makeText(this, "Successfully updated time of Fajr ", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
            else
            {
                try{
                    db.execSQL("INSERT INTO PrayerTime VALUES('Fajr','"+ timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + "');");
                    Toast.makeText(this, "Successfully inserted time of Fajr ", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }


            long time;
            Calendar calendar = Calendar.getInstance();
            //Get time of prayer from DB
            c=db.rawQuery("SELECT time FROM PrayerTime WHERE prayer='Fajr'", null);
            int hours = 0, min = 0;
            if (c.moveToFirst()){
                String tempTime = c.getString(0);
                 hours = Integer.parseInt(tempTime.substring(0, tempTime.indexOf(':')));
                 min = Integer.parseInt(tempTime.substring(tempTime.indexOf(':')+1));
                Toast.makeText(this, ""+ hours + ":" +min, Toast.LENGTH_SHORT).show();
            }
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, min);
            Intent intent = new Intent(this, PrayerReminderReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//            Notification noti = new Notification.Builder(MainActivity.this).setContentTitle("Prayer Alarm").setContentText("It's Fajr Prayer Time").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            noti.flags |= Notification.FLAG_AUTO_CANCEL;
//            manager.notify(0, noti);

            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                if (calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            getAllData();
        }
//        else {
//            alarmManager.cancel(pendingIntent);
//            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
//        }




        if (view == btn_zuhr){

            Toast.makeText(MainActivity.this, "Zuhr Prayer Time set", Toast.LENGTH_SHORT).show();
            Cursor c=db.rawQuery("SELECT * FROM PrayerTime WHERE prayer='Zuhr'", null);
            if(c.moveToFirst())
            {
                try{
                    db.execSQL("UPDATE PrayerTime SET time='" + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + "' WHERE prayer='Zuhr'");
                    Toast.makeText(this, "Successfully updated time of Zuhr ", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
            else
            {
                try{
                    db.execSQL("INSERT INTO PrayerTime VALUES('Zuhr','"+ timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + "');");
                    Toast.makeText(this, "Successfully inserted time of Zuhr ", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }


            long time;
            Calendar calendar = Calendar.getInstance();
            //Get time of prayer from DB
            c=db.rawQuery("SELECT time FROM PrayerTime WHERE prayer='Zuhr'", null);
            int hours = 0, min = 0;
            if (c.moveToFirst()){
                String tempTime = c.getString(0);
                hours = Integer.parseInt(tempTime.substring(0, tempTime.indexOf(':')));
                min = Integer.parseInt(tempTime.substring(tempTime.indexOf(':')+1));
                Toast.makeText(this, ""+ hours + ":" +min, Toast.LENGTH_SHORT).show();
            }
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, min);
            Intent intent = new Intent(this, PrayerReminderReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//            Notification noti = new Notification.Builder(MainActivity.this).setContentTitle("Prayer Alarm").setContentText("It's Zuhr Prayer Time").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            noti.flags |= Notification.FLAG_AUTO_CANCEL;
//            manager.notify(0, noti);
            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                if (calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            getAllData();
        }
//        else {
//            alarmManager.cancel(pendingIntent);
//            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
//        }
        if (view == btn_asr){

            Toast.makeText(MainActivity.this, "Asr Prayer Time set", Toast.LENGTH_SHORT).show();
            Cursor c=db.rawQuery("SELECT * FROM PrayerTime WHERE prayer='Asr'", null);
            if(c.moveToFirst())
            {
                try{
                    db.execSQL("UPDATE PrayerTime SET time='" + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + "' WHERE prayer='Asr'");
                    Toast.makeText(this, "Successfully updated time of Asr ", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
            else
            {
                try{
                    db.execSQL("INSERT INTO PrayerTime VALUES('Asr','"+ timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + "');");
                    Toast.makeText(this, "Successfully inserted time of Asr ", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }


            long time;
            Calendar calendar = Calendar.getInstance();
            //Get time of prayer from DB
            c=db.rawQuery("SELECT time FROM PrayerTime WHERE prayer='Asr'", null);
            int hours = 0, min = 0;
            if (c.moveToFirst()){
                String tempTime = c.getString(0);
                hours = Integer.parseInt(tempTime.substring(0, tempTime.indexOf(':')));
                min = Integer.parseInt(tempTime.substring(tempTime.indexOf(':')+1));
                Toast.makeText(this, ""+ hours + ":" +min, Toast.LENGTH_SHORT).show();
            }
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, min);
            Intent intent = new Intent(this, PrayerReminderReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//            Notification noti = new Notification.Builder(MainActivity.this).setContentTitle("Prayer Alarm").setContentText("It's Asr Prayer Time").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            noti.flags |= Notification.FLAG_AUTO_CANCEL;
//            manager.notify(0, noti);
            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                if (calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            getAllData();
        }


        if (view == btn_maghrib){

            Toast.makeText(MainActivity.this, "Maghrib Prayer Time set", Toast.LENGTH_SHORT).show();
            Cursor c=db.rawQuery("SELECT * FROM PrayerTime WHERE prayer='Maghrib'", null);
            if(c.moveToFirst())
            {
                try{
                    db.execSQL("UPDATE PrayerTime SET time='" + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + "' WHERE prayer='Maghrib'");
                    Toast.makeText(this, "Successfully updated time of Maghrib ", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
            else
            {
                try{
                    db.execSQL("INSERT INTO PrayerTime VALUES('Maghrib','"+ timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + "');");
                    Toast.makeText(this, "Successfully inserted time of Maghrib ", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }


            long time;
            Calendar calendar = Calendar.getInstance();
            //Get time of prayer from DB
            c=db.rawQuery("SELECT time FROM PrayerTime WHERE prayer='Maghrib'", null);
            int hours = 0, min = 0;
            if (c.moveToFirst()){
                String tempTime = c.getString(0);
                hours = Integer.parseInt(tempTime.substring(0, tempTime.indexOf(':')));
                min = Integer.parseInt(tempTime.substring(tempTime.indexOf(':')+1));
                Toast.makeText(this, ""+ hours + ":" +min, Toast.LENGTH_SHORT).show();
            }
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, min);
            Intent intent = new Intent(this, PrayerReminderReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//            Notification noti = new Notification.Builder(MainActivity.this).setContentTitle("Prayer Alarm").setContentText("It's Maghrib Prayer Time").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            noti.flags |= Notification.FLAG_AUTO_CANCEL;
//            manager.notify(0, noti);
            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                if (calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            getAllData();
        }


        if (view == btn_isha){

            Toast.makeText(MainActivity.this, "Isha Prayer Time set", Toast.LENGTH_SHORT).show();
            Cursor c=db.rawQuery("SELECT * FROM PrayerTime WHERE prayer='Isha'", null);
            if(c.moveToFirst())
            {
                try{
                    db.execSQL("UPDATE PrayerTime SET time='" + timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + "' WHERE prayer='Isha'");
                    Toast.makeText(this, "Successfully updated time of Isha ", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
            else
            {
                try{
                    db.execSQL("INSERT INTO PrayerTime VALUES('Isha','"+ timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + "');");
                    Toast.makeText(this, "Successfully inserted time of Isha ", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }


            long time;
            Calendar calendar = Calendar.getInstance();
            //Get time of prayer from DB
            c=db.rawQuery("SELECT time FROM PrayerTime WHERE prayer='Isha'", null);
            int hours = 0, min = 0;
            if (c.moveToFirst()){
                String tempTime = c.getString(0);
                hours = Integer.parseInt(tempTime.substring(0, tempTime.indexOf(':')));
                min = Integer.parseInt(tempTime.substring(tempTime.indexOf(':')+1));
                Toast.makeText(this, ""+ hours + ":" +min, Toast.LENGTH_SHORT).show();
            }
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, min);
            Intent intent = new Intent(this, PrayerReminderReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(
//                    this).setSmallIcon(R.drawable.ic_launcher_foreground)
//                    .setContentTitle("Prayer Time").setAutoCancel(true);
//            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                    + "://" + getPackageName() + "/res/raw/azan1.mp3");
//            builder.setSound(alarmSound);
//
//            builder.setContentIntent(pendingIntent);
//            NotificationManager manager = (NotificationManager)
//                    getSystemService(Context.NOTIFICATION_SERVICE);
//            manager.notify(1, builder.build());
//            PendingIntent pending = PendingIntent.getActivity(MainActivity.this,0, intent, 0);
//            Notification noti = new Notification.Builder(MainActivity.this).setContentTitle("New Message").setContentText("Its prayer time ").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build();
//            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                    + "://" + getPackageName() + R.raw.azan1);
////            InputStream alarmSound =  this.getResources().openRawResource(R.raw.azan1);
//            noti.sound = alarmSound;
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            noti.flags |= Notification.FLAG_AUTO_CANCEL;
//            manager.notify(0, noti);



            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                if (calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }

            alarmManager.set(AlarmManager.RTC, time, pendingIntent);
            getAllData();
        }

    }

    public void getAllData(){
        arrayList = new ArrayList<String>();
        Cursor cursor= db.rawQuery("SELECT * From PrayerTime",null);
        cursor.toString();
        while(cursor.moveToNext()){
            String prayer = cursor.getString(0);
            String prayer_time = cursor.getString(1);

            arrayList.add(prayer + "\t" + prayer_time);
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  arrayList);
        listView.setAdapter(arrayAdapter);
        //return arrayList;

    }
}
