package com.latihanandroid.dailyreminderassistant.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class KegiatanPentingViewModel extends AndroidViewModel {
    private KegiatanPentingRepository kegiatanPentingRepository;
    private LiveData<List<KegiatanPenting>> mListKegiatanPenting;
    public KegiatanPentingViewModel(@NonNull Application application) {
        super(application);
        kegiatanPentingRepository=new KegiatanPentingRepository(application);
    }
    public void insert(KegiatanPenting kegiatanPenting){
        kegiatanPentingRepository.insert(kegiatanPenting);
    }
    public void update(KegiatanPenting kegiatanPenting){
        kegiatanPentingRepository.update(kegiatanPenting);
    }
    public void delete(KegiatanPenting kegiatanPenting){
        kegiatanPentingRepository.delete(kegiatanPenting);
    }
    public void insert(KegiatanPenting kegiatanPenting,AfterGetResultListener<KegiatanPenting> kegiatanPentingAfterGetResultListener){
        kegiatanPentingRepository.insert(kegiatanPenting,kegiatanPentingAfterGetResultListener);
    }
    public void update(KegiatanPenting kegiatanPenting,AfterGetResultListener<KegiatanPenting> kegiatanPentingAfterGetResultListener){
        kegiatanPentingRepository.update(kegiatanPenting,kegiatanPentingAfterGetResultListener);
    }
    public void delete(KegiatanPenting kegiatanPenting,AfterGetResultListener<KegiatanPenting> kegiatanPentingAfterGetResultListener){
        kegiatanPentingRepository.delete(kegiatanPenting,kegiatanPentingAfterGetResultListener);
    }
    public LiveData<List<KegiatanPenting>> getAllKegiatanPenting(){
        mListKegiatanPenting=kegiatanPentingRepository.getAllKegiatanPenting();
        return mListKegiatanPenting;
    }
    public LiveData<List<KegiatanPenting>> getAllKegiatanPentingWhereIdIs(int id){
        mListKegiatanPenting=kegiatanPentingRepository.getAllKegiatanPentingWhereId(id);
        return mListKegiatanPenting;
    }
    public LiveData<List<KegiatanPenting>> getAllKegiatanPentingWhereAlarmIs(int jenisAlarm){
        mListKegiatanPenting=kegiatanPentingRepository.getAllKegiatanPentingWhereJenisAlarm(jenisAlarm);
        return mListKegiatanPenting;
    }
}
