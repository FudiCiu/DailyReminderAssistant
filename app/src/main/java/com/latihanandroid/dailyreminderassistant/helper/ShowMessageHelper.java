package com.latihanandroid.dailyreminderassistant.helper;

import android.content.Context;
import android.widget.Toast;

import com.latihanandroid.dailyreminderassistant.model.JadwalHarian;

public class ShowMessageHelper {
    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
    public static void showToastJabatan(Context context, JadwalHarian jadwalHarian){
        Toast.makeText(context,String.valueOf(jadwalHarian.getMId())+" "+
                String.valueOf(jadwalHarian.getMHari())+" "+
                jadwalHarian.getMHariAsString()+" "+
                jadwalHarian.getMWaktuAsString()+" "+
                jadwalHarian.getMTempat()+" "+
                String.valueOf(jadwalHarian.getMImageId())+" "+
                jadwalHarian.getMKeterangan(),Toast.LENGTH_SHORT).show();
    }
}
