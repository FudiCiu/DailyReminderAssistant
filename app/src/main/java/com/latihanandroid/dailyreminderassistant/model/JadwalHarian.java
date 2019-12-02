package com.latihanandroid.dailyreminderassistant.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
@Entity(tableName = "JadwalHarian")
public class JadwalHarian implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;

    private int mHari;
    private Date mWaktu;
    private String mTempat;
    private String mKeterangan;
    private int mImageId;


    public JadwalHarian() {
    }

    public JadwalHarian(int mHari,Date mWaktu, String mTempat, String mKeterangan) {
        this.mHari=mHari;
        this.mWaktu = mWaktu;
        this.mTempat = mTempat;
        this.mKeterangan = mKeterangan;
    }

    public JadwalHarian(int mHari,Date mWaktu, String mTempat, String mKeterangan, int mImageId) {
        this.mHari=mHari;
        this.mWaktu = mWaktu;
        this.mTempat = mTempat;
        this.mKeterangan = mKeterangan;
        this.mImageId = mImageId;
    }

    public JadwalHarian(int mId, int mHari, Date mWaktu, String mTempat, String mKeterangan, int mImageId) {
        this.mId = mId;
        this.mHari = mHari;
        this.mWaktu = mWaktu;
        this.mTempat = mTempat;
        this.mKeterangan = mKeterangan;
        this.mImageId = mImageId;
    }

    protected JadwalHarian(Parcel in) {
        mId=in.readInt();
        mHari=in.readInt();
        mWaktu=new Date(in.readLong());
        mTempat = in.readString();
        mKeterangan = in.readString();
        mImageId=in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeInt(mHari);
        dest.writeLong(mWaktu==null?0:mWaktu.getTime());
        dest.writeString(mTempat==null?"":mTempat);
        dest.writeString(mKeterangan==null?"":mKeterangan);
        dest.writeInt(mImageId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<JadwalHarian> CREATOR = new Creator<JadwalHarian>() {
        @Override
        public JadwalHarian createFromParcel(Parcel in) {
            return new JadwalHarian(in);
        }

        @Override
        public JadwalHarian[] newArray(int size) {
            return new JadwalHarian[size];
        }
    };

    public int getMId() {
        return mId;
    }

    public void setMId(int mId) {
        this.mId = mId;
    }

    public int getMHari() {
        return mHari;
    }

    public void setMHari(int mHari) {
        this.mHari = mHari;
    }
    public String getMHariAsString(){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK,mHari);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG,Locale.getDefault());

    }
    public Date getMWaktu() {
        return mWaktu;
    }

    public void setMWaktu(Date mWaktu) {
        this.mWaktu = mWaktu;
    }

    public String getMWaktuAsString(){
        SimpleDateFormat mWaktuFormatter=new SimpleDateFormat("HH:mm", Locale.getDefault());
        return mWaktuFormatter.format(this.mWaktu);
    }
    public String getMTempat() {
        return mTempat;
    }

    public void setMTempat(String mTempat) {
        this.mTempat = mTempat;
    }

    public String getMKeterangan() {
        return mKeterangan;
    }

    public void setMKeterangan(String mKeterangan) {
        this.mKeterangan = mKeterangan;
    }

    public int getMImageId() {
        return mImageId;
    }

    public void setMImageId(int mImageId) {
        this.mImageId = mImageId;
    }
}
