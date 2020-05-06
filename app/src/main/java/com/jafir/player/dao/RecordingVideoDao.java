package com.jafir.player.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.jafir.player.RecordingModel;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface RecordingVideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdateRecordVideo(RecordingModel... channelTypeModels);

    @Query("SELECT * FROM recording_video  ORDER BY createTime DESC")
    Single<List<RecordingModel>> getAll();

    @Delete
    void delete(RecordingModel... channelTypeModels);
}
