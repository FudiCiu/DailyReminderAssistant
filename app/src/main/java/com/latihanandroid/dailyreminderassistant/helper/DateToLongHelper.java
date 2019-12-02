package com.latihanandroid.dailyreminderassistant.helper;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateToLongHelper {
    @TypeConverter
    public static long convertDateToLong(Date date){
        return date==null?null:date.getTime();
    }

    @TypeConverter
    public static Date convertLongToDate(long date){
        return new Date(date);
    }
}
