package com.kalam.sibi.ontime.Storage.WorkerThread;

import android.app.PendingIntent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kalam.sibi.ontime.Storage.ActionProp;
import com.kalam.sibi.ontime.Storage.AppDatabase;

import java.util.ArrayList;


/**
 * Created by Sibi on 14-12-2017.
 */

public class AsyncAdapter extends AsyncTask<Void, Void, Void>{

    private String name,event;
    private long time,interval;
    private boolean repeating,state;
    private int hour,min,barValue;
    private String msg;
    private String TAG="TAG";


    ActionProp act;
    AppDatabase database;

    //option=1 for writing data
    // option=2 for reading all data
    // option=3 for reading particular data
    public AsyncAdapter(AppDatabase database, String event,long time,String msg,int hour,int min,@Nullable long interval,@Nullable int barValue, boolean repeating,boolean state){

        this.event=event;
        this.time=time;
        this.hour=hour;
        this.min=min;
        this.interval=interval;
        this.barValue=barValue;
        this.repeating=repeating;
        this.database=database;
        this.state=state;


        if(msg==null){
            this.msg=event;
        }
        else{
            this.msg=msg;
        }
        act=new ActionProp();
        }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params){
        //writing data on database
        Log.d(TAG,"FROM ASYNC ADAPTER");
        Log.d(TAG,""+barValue);
        //Log.d(TAG,this.event);
        //act.set_Name(name);
        act.set_Event(event);
        act.set_Time(time);
        act.set_Interval(interval);
        act.set_BarValue(barValue);
        act.set_State(state);
        act.set_Repeat(repeating);
        act.set_Hour(hour);
        act.set_Min(min);
        act.set_msg(msg);
        database.dao().insertAction(act);


        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);

    }
}