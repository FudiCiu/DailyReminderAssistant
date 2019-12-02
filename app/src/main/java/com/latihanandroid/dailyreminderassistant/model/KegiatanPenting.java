package com.latihanandroid.dailyreminderassistant.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity(tableName = "KegiatanPenting")
public class KegiatanPenting implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;

    private Date mTanggal;
    private Date mWaktu;
    private String mTempat;
    private String mKeterangan;
    private int mJenisAlarm;

    public KegiatanPenting(Date mTanggal, Date mWaktu, String mTempat, String mKeterangan, int mJenisAlarm) {
        this.mTanggal = mTanggal;
        this.mWaktu = mWaktu;
        this.mTempat = mTempat;
        this.mKeterangan = mKeterangan;
        this.mJenisAlarm = mJenisAlarm;
    }


    public int getMId() {
        return mId;
    }

    public void setMId(int mId) {
        this.mId = mId;
    }

    public Date getMTanggal() {
        return mTanggal;
    }

    public String getMTanggalAsSString(){
        SimpleDateFormat mWaktuFormatter=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return mWaktuFormatter.format(this.mTanggal);
    }
    public String getMTanggalAsLString(){
        SimpleDateFormat mWaktuFormatter=new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return mWaktuFormatter.format(this.mTanggal);
    }
    public void setMTanggal(Date mTanggal) {
        this.mTanggal = mTanggal;
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

    public int getMJenisAlarm() {
        return mJenisAlarm;
    }

    public void setMJenisAlarm(int mJenisAlarm) {
        this.mJenisAlarm = mJenisAlarm;
    }

    protected KegiatanPenting(Parcel in) {
        mId = in.readInt();
        mTanggal=new Date(in.readLong());
        mWaktu=new Date(in.readLong());
        mTempat = in.readString();
        mKeterangan = in.readString();
        mJenisAlarm=in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeLong(mTanggal.getTime());
        dest.writeLong(mWaktu.getTime());
        dest.writeString(mTempat);
        dest.writeString(mKeterangan);
        dest.writeInt(mJenisAlarm);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KegiatanPenting> CREATOR = new Creator<KegiatanPenting>() {
        @Override
        public KegiatanPenting createFromParcel(Parcel in) {
            return new KegiatanPenting(in);
        }

        @Override
        public KegiatanPenting[] newArray(int size) {
            return new KegiatanPenting[size];
        }
    };
}
