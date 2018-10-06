package com.kalam.sibi.ontime.Storage.WorkerThread;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kalam.sibi.ontime.AlarmAndEvents.AlarmClass;
import com.kalam.sibi.ontime.Storage.ActionProp;
import com.kalam.sibi.ontime.Storage.AppDatabase;

import java.util.List;

/**
 * Created by Sibi on 31-12-2017.
 */

public class UpdateAdapter extends AsyncTask<Void,Void,Void> {

    private int id;
    private String event;
    private long time,interval;
    private boolean repeating,state;
    private int hour,min,barValue;
    private int option=0;
    private Context ctx;
    private String msg;
    private String TAG="TAG";



    ActionProp act;
    AppDatabase database;

    public UpdateAdapter(int id,AppDatabase database, String event, long time,String msg,int hour, int min, @Nullable long interval, @Nullable int barValue, boolean repeating, boolean state){
        this.id=id;
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
    }

    public UpdateAdapter(Context ctx, int option, AppDatabase database){
        this.ctx=ctx;
        this.option=option;
        this.database=database;
    }

    @Override
    protected Void doInBackground(Void... params) {

        if(option==0) {
            act = database.dao().findId(id);
            Log.d("UPDATE", act.get_Event());
            act.set_Event(event);
            act.set_Time(time);
            act.set_Interval(interval);
            act.set_BarValue(barValue);
            act.set_State(state);
            act.set_Repeat(repeating);
            act.set_Hour(hour);
            act.set_Min(min);
            act.set_msg(msg);


            database.dao().updateData(act);
        }
        if(option==1){
            List<ActionProp>temp = database.dao().getAllInList();
            for(int i=0;i<database.dao().getAllInList().size();i++){
                temp.get(i).set_State(false);
                new AlarmClass(ctx,temp.get(i).get_Id());
                database.dao().updateData(temp.get(i));


            }
        }

        if(option==2){
            List<ActionProp>temp = database.dao().getAllInList();
            for(int i=0;i<database.dao().getAllInList().size();i++){
                temp.get(i).set_State(true);
                new AlarmClass(ctx,temp.get(i).get_Time(),temp.get(i).get_Id(),temp.get(i).get_Event(),temp.get(i).get_interval());
                database.dao().updateData(temp.get(i));

            }
        }

        return null;
    }
}
