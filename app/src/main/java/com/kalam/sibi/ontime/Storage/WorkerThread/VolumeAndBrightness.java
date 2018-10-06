package com.kalam.sibi.ontime.Storage.WorkerThread;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.kalam.sibi.ontime.AlarmAndEvents.AlarmReceiever;
import com.kalam.sibi.ontime.Storage.ActionProp;
import com.kalam.sibi.ontime.Storage.AppDatabase;

/**
 * Created by Sibi on 25-12-2017.
 */

public class VolumeAndBrightness extends AsyncTask<Void,Void,Void> {

    int id;
    AppDatabase data;
    int value;
    AlarmReceiever alr;
    String act;
    ActionProp a;

    public VolumeAndBrightness(Context ctx, int id, AlarmReceiever alr,String act){
        this.id=id;
        this.act=act;
        this.alr=alr;
        data = AppDatabase.getDatabse(ctx);
    }
    @Override
    protected Void doInBackground(Void... params) {

        a = data.dao().findId(id);
        value = a.get_Bar();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(act.equals("VOL"))
            Log.d("VOL","reac");
            alr.setVolume(value);
        if(act.equals("BRI"))
            alr.setBrightness(value);
        if(act.equals("NOTI"))
            alr.notificationVol(value);
    }
}
