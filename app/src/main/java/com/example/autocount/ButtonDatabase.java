package com.example.autocount;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = ButtonModel.class, version = 1, exportSchema = false)
public abstract class ButtonDatabase extends RoomDatabase {

    public static final String DB_NAME = "button_db";
    private static ButtonDatabase instance;

    public static synchronized ButtonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ButtonDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ButtonModelDao buttonModelDao();
}
