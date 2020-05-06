package com.jafir.player.dao;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jafir.player.RecordingModel;


@Database(
        entities = {
                RecordingModel.class,
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RecordingVideoDao recordingVideoDao();

    public static AppDatabase create(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "jafir.db")
                .build();
    }
}