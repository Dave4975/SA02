package com.example.SA02;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ValuesDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Values mileage, Values cost, Values amount);

    @Query("DELETE FROM `value_table`")
    void deleteAll();

    @Query("SELECT * from `value_table` ORDER BY id ASC")
    LiveData<List<Values>> getAllValues();

    @Query("SELECT * from `value_table` LIMIT 1")
    Values[] getAnyValue();

    @Delete
    void deleteValue(Values id);
}
