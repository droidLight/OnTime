package com.kalam.sibi.ontime.AlarmAndEvents;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.kalam.sibi.ontime.R;
import com.kalam.sibi.ontime.Storage.AppDatabase;
import com.kalam.sibi.ontime.Storage.WorkerThread.VolumeAndBrightness;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Notification.VISIBILITY_PUBLIC;

/**
 * Created by Sibi on 24-02-2018.
 */

public class AlarmReceiever extends BroadcastReceiver {

    AudioManager am;
    static Context ctx;
    ContentResolver c;
    static NotificationCompat.Builder builder;
    SharedPreferences shared;
    static boolean showNotifi,skip;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.ctx = context;
        shared = ctx.getSharedPreferences("ROOTANDNOTIFI",Context.MODE_PRIVATE);
        if (shared.getInt("NOTIFI", 0) == 1) {
            showNotifi = true;
            Log.d("NO","GOING TO SHOW");
        }


        switch (intent.getStringExtra("Event")) {

            case "Do Not Disturb":
                doNotDisturbMode();
                break;

            case "Silent":
                silentMode();
                break;

            case "Normal":
                normalMode();
                break;

            case "Wifi On":
                wifiMode(true);
                break;

            case "Wifi Off":
                wifiMode(false);
                break;
            case "Ringer Volume":
                new VolumeAndBrightness(context, intent.getIntExtra("ID", 0), this, "VOL").execute();
                break;

            case "Notification Volume":
                new VolumeAndBrightness(context, intent.getIntExtra("ID", 0), this, "NOTI").execute();
                break;

            case "Brightness":
                new VolumeAndBrightness(context, intent.getIntExtra("ID", 0), this, "BRI").execute();
                break;

            case "Remainder":
                new NotifyThread(AppDatabase.getDatabse(ctx), intent.getIntExtra("ID", 0)).execute();
                break;

            case "MobileData Off":
                dataToggle(0);
                break;

            case "MobileData On":
                dataToggle(1);
                break;

            case "Airplane mode On":
                airplaneToggle(1);
                break;

            case "Airplane mode Off":
                airplaneToggle(0);
                break;

        }


    }

    public void genNotification(String msg) {
        builder = new NotificationCompat.Builder(ctx, "GEN");
        builder.setSmallIcon(R.drawable.ic_stat_iconreal);
        builder.setColorized(true);
        builder.setContentTitle("OnTime");
        builder.setAutoCancel(false);
        builder.setContentText(msg);
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        builder.setVisibility(VISIBILITY_PUBLIC);
        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    public void doNotDisturbMode() {
        am = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        if (showNotifi) genNotification("Do Not Disturb On");
    }

    public void silentMode() {
        am = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        if (showNotifi) genNotification("Silent On");
    }

    public void normalMode() {
        am = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        if (showNotifi) genNotification("Normal mode On");
    }

    public void setVolume(int value) {
        am = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_RING, value, 0);
        if (showNotifi) genNotification("Volume set");
    }

    public void notificationVol(int value){
        am = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_NOTIFICATION, value, 0);
        if (showNotifi) genNotification(" Notification Volume set");
    }

    public void setBrightness(int value) {
        c = ctx.getContentResolver();
        Settings.System.putInt(c, Settings.System.SCREEN_BRIGHTNESS, value);
        if (showNotifi) genNotification("Brightness set");
    }

    public void wifiMode(boolean state) {
        WifiManager wifiManager = (WifiManager) ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(state);
        if (showNotifi) genNotification("Wifi state changed");
    }

    public static void notificationShow(String msg) {
        builder = new NotificationCompat.Builder(ctx, "REMAINDER");
        builder.setSmallIcon(R.drawable.ic_stat_iconreal);
        builder.setContentTitle("OnTime");
        builder.setAutoCancel(false);
        builder.setContentText(msg);
        builder.setColorized(true);
        builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        builder.setVisibility(VISIBILITY_PUBLIC);
        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());

    }

    public void dataToggle(int option) {
        try {
            if (option == 1) {
                Runtime.getRuntime().exec("su -c svc data enable");
                if (showNotifi) genNotification("Mobile data On");

            } else {
                Runtime.getRuntime().exec("su -c svc data disable");
                if (showNotifi) genNotification("Mobile data Off");

            }
        } catch (Exception e) {
            Log.d("DATA", "NO ROOT FOUND");
        }
    }

    public void airplaneToggle(int option){
        try {
            if(option==1) {
                Runtime.getRuntime().exec("su -c settings put global airplane_mode_on 1");
                Runtime.getRuntime().exec("su -c am broadcast -a android.intent.action.AIRPLANE_MODE --ez state true");
                if(showNotifi) genNotification("Airplane Mode On");
            }
            else{
                Runtime.getRuntime().exec("su -c settings put global airplane_mode_on 0");
                Runtime.getRuntime().exec("su -c am broadcast -a android.intent.action.AIRPLANE_MODE --ez state false");
                if(showNotifi) genNotification("Airplane Mode Off");
            }
        }catch (Exception e){
            Toast.makeText(ctx,"ROOT NOT FOUND",Toast.LENGTH_SHORT).show();
        }

    }


    private static class NotifyThread extends AsyncTask<Void, String, String> {

        AppDatabase data;
        int id;
        String msg;

        public NotifyThread(AppDatabase data, int id) {
            this.data = data;
            this.id = id;

        }

        @Override
        protected String doInBackground(Void... params) {

            msg = data.dao().findId(id).get_msg();

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            notificationShow(msg);
        }


    }
}
