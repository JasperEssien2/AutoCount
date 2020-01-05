package com.example.autocount;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ButtonModelDao {
    @Query("Select * from auto_count")
    LiveData<List<ButtonModel>> getAutoCountList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ButtonModel buttonModel);

    @Insert
    void insert(List<ButtonModel> buttonModels);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ButtonModel buttonModel);
}
