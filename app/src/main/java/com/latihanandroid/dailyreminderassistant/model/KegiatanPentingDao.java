package com.latihanandroid.dailyreminderassistant.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface KegiatanPentingDao {
    @Insert
    public long insert(KegiatanPenting kegiatanPenting);

    @Update
    public void update(KegiatanPenting... kegiatanPentings);

    @Delete
    public void delete(KegiatanPenting kegiatanPenting);

    @Query("Select * from KegiatanPenting")
    public LiveData<List<KegiatanPenting>> readAllKegiatanPenting();

    @Query("Select * from KegiatanPenting where mId= :mId")
    public LiveData<List<KegiatanPenting>> readAllKegiatanPentingWhereIdIs(int mId);

    @Query("Select * from KegiatanPenting where mJenisAlarm= :mJenisAlarm")
    public LiveData<List<KegiatanPenting>> readAllKegiatanPentingWhereJenisAlarmIs(int mJenisAlarm);
}
