package com.kalam.sibi.ontime.Storage.CustomData;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.kalam.sibi.ontime.Storage.ActionProp;
import com.kalam.sibi.ontime.Storage.AppDatabase;

import java.util.List;

/**
 * Created by Sibi on 15-12-2017.
 */

public class MyViewModel extends AndroidViewModel {

    private final LiveData<List<ActionProp>> listOfData;

    public MyViewModel(@NonNull Application application) {
        super(application);
        listOfData =AppDatabase.getDatabse(this.getApplication()).dao().getAll();
    }

    public LiveData<List<ActionProp>>getListOfData(){
        return listOfData;
    }
}
