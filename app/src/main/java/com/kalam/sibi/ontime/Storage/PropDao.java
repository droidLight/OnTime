package com.kalam.sibi.ontime.Storage;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Sibi on 10-12-2017.
 */
@Dao
public interface PropDao{

    @Query("SELECT * FROM ActionProp")
    LiveData<List<ActionProp>>getAll();

    @Query("SELECT * FROM ActionProp")
    List<ActionProp>getAllInList();

    @Query("SELECT * FROM ActionProp WHERE id LIKE :n")
    ActionProp findId(int n);

    @Insert
    void insertAction(ActionProp... action);

    @Update
    void updateData(ActionProp act);

    @Delete
    void delete(ActionProp action);
}
