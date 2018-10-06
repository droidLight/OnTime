package com.kalam.sibi.ontime.Storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by Sibi on 11-12-2017.
 */

@Database(entities = {ActionProp.class},version = 1)


public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase instance;

    public abstract PropDao dao();

    public static AppDatabase getDatabse(Context context){


        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"Actions_DB").build();

        }
        return instance;
    }
}
