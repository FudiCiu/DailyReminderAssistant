package com.latihanandroid.dailyreminderassistant.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.latihanandroid.dailyreminderassistant.helper.DateToLongHelper;

@Database(entities = {JadwalHarian.class,KegiatanPenting.class},version = 4)
@TypeConverters(DateToLongHelper.class)
public abstract class DailyAsistantRoomDatabase extends RoomDatabase {
    public abstract JadwalHarianDao jadwalHarianDao();
    public abstract KegiatanPentingDao kegiatanPentingDao();
    private static volatile DailyAsistantRoomDatabase INSTANCE;
    public static DailyAsistantRoomDatabase getDatabase(Context context){
        if (INSTANCE==null){
            synchronized (DailyAsistantRoomDatabase.class){
                INSTANCE= Room.databaseBuilder(context.getApplicationContext(),DailyAsistantRoomDatabase.class,"daily_assistant_database")
                        .build();
            }
        }
        return INSTANCE;
    }
}
