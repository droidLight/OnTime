package com.kalam.sibi.ontime.Storage;

import android.app.PendingIntent;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by Sibi on 07-12-2017.
 */

@Entity(tableName = "ActionProp")
public class ActionProp {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    int id;

    //@ColumnInfo(name="name")
   // @NonNull
    //String action_name;

    @ColumnInfo(name="event")
     String event;

    @ColumnInfo(name="time")
     long time;

    @ColumnInfo(name="state")
     boolean state=false;//whether button is on or off

    @ColumnInfo(name="interval")
    long interval;

    @ColumnInfo(name="seekbar")
    int barValue;

    @ColumnInfo(name="Repeating")
    boolean repeat;

    @ColumnInfo(name="hour")
    int hour;

    @ColumnInfo(name="min")
    int min;

    @ColumnInfo(name="msg")
    String msg;




    public ActionProp(){}

    //@Ignore
   // public void set_Name(String name){
    //    this.action_name = name;
    //}
   // @Ignore
    //public String get_Name(){
        //return action_name;
    //}

    @Ignore
    public void set_Event(String event){this.event=event;}
    @Ignore
    public String get_Event(){return event;}

    @Ignore
    public void set_Time(long times){this.time=times;}
    @Ignore
    public long get_Time(){return time;}

    @Ignore
    public void set_Interval(long interval){this.interval=interval;}
    @Ignore
    public long get_interval(){return interval;}

    @Ignore
    public void set_BarValue(int value){this.barValue=value;}
    @Ignore
    public int get_Bar(){return barValue;}

    @Ignore
    public void set_State(boolean st){this.state=st;}
    @Ignore
    public boolean get_State(){return state;}

    @Ignore
    public void set_Repeat(boolean repeat){this.repeat=repeat;}
    @Ignore
    public boolean isRepeat(){return repeat;}

    @Ignore
    public int get_Hour() {
        return hour;
    }
    @Ignore
    public void set_Hour(int hour) {
        this.hour = hour;
    }

    @Ignore
    public int get_Min() {
        return min;
    }
    @Ignore
    public void set_Min(int min) {
        this.min = min;
    }

    @Ignore
    public int get_Id(){return this.id;}

    @Ignore
    public String get_msg(){return msg;}
    @Ignore
    public void set_msg(String msg){this.msg=msg;}



}
