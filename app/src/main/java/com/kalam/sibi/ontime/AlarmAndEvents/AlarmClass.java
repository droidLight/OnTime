package com.kalam.sibi.ontime.AlarmAndEvents;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Sibi on 24-02-2018.
 */

public class AlarmClass {
    AlarmManager manager;
    Intent i;
    PendingIntent pi;
    int id;


    public AlarmClass(Context context,long time,int id,String event,long interval) {

        manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        i = new Intent(context,AlarmReceiever.class);
        i.putExtra("Event",event);
        //if(event.equals("Ringer Volume") || event.equals("Brightness")|| event.equa) {
        i.putExtra("ID",id);
        //}
        pi = PendingIntent.getBroadcast(context, id, i, PendingIntent.FLAG_UPDATE_CURRENT);
        if (interval == 0) {
            //if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                //manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pi);
                manager.setWindow(AlarmManager.RTC_WAKEUP,time,time+1000,pi);
            //}
            //else{
                //manager.setExact(AlarmManager.RTC_WAKEUP, time, pi);
                //manager.setWindow();
            //}
            Log.d("ALARM", "Non repeating alarm");
        } else {
            manager.setRepeating(AlarmManager.RTC_WAKEUP, time, interval, pi);
            Log.d("ALARM", "Repeating");
        }

    }

    public AlarmClass(Context context,int id){
        this.id=id;
        manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        i = new Intent(context,AlarmReceiever.class);
        i.putExtra("Event","CANCEL");
        pi = PendingIntent.getBroadcast(context,id,i,PendingIntent.FLAG_UPDATE_CURRENT);
        manager.cancel(pi);
        Log.d("ALARM","Alarm cancelled");

    }
}
