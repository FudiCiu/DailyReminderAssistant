package com.latihanandroid.dailyreminderassistant.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JadwalHarianDao {

    @Insert
    public void create(JadwalHarian data);

    @Query("Select * from JadwalHarian")
    public LiveData<List<JadwalHarian>> readAllJadwalHarian();

    @Query("Select * from JadwalHarian Where mHari= :hari")
    public LiveData<List<JadwalHarian>> readJadwalHarianWhereHariIs(int hari);

    @Update
    public void update(JadwalHarian... jadwalHarians);

    @Delete
    public void delete(JadwalHarian jadwalHarian);


}
