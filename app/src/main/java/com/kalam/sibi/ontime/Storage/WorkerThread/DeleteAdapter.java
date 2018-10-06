package com.kalam.sibi.ontime.Storage.WorkerThread;

import android.os.AsyncTask;
import android.util.Log;

import com.kalam.sibi.ontime.Storage.ActionProp;
import com.kalam.sibi.ontime.Storage.AppDatabase;

/**
 * Created by Sibi on 19-12-2017.
 */

public class DeleteAdapter extends AsyncTask<Void,Void,Void> {

    AppDatabase database;
    int id;
    ActionProp act;
    public DeleteAdapter(int id,AppDatabase database){this.id=id;this.database=database;}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        act = database.dao().findId(id);
        database.dao().delete(act);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);

    }
}
