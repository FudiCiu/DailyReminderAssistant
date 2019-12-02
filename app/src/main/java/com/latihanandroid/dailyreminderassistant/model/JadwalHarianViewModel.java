package com.latihanandroid.dailyreminderassistant.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class JadwalHarianViewModel extends AndroidViewModel {
    private JadwalHarianRepository mJadwalHarianRepository;
    private LiveData<List<JadwalHarian>> mListJadwalHarian;
    public JadwalHarianViewModel(@NonNull Application application) {
        super(application);
        mJadwalHarianRepository=new JadwalHarianRepository(application);
    }
    public void insert(JadwalHarian jadwalHarian){
        mJadwalHarianRepository.insertJadwalHarian(jadwalHarian);
    }

    public LiveData<List<JadwalHarian>> getAll(){
        mListJadwalHarian=mJadwalHarianRepository.getAllJadwalHarian();
        return mListJadwalHarian;
    }

    public LiveData<List<JadwalHarian>> getAllByDay(int day){
        mListJadwalHarian=mJadwalHarianRepository.getJadwalHarianByDay(day);
        return mListJadwalHarian;
    }
    public void update(JadwalHarian jadwalHarian){
        mJadwalHarianRepository.updateJadwalHarian(jadwalHarian);
    }
    public void delete(JadwalHarian jadwalHarian){
        mJadwalHarianRepository.deleteJadwalHarian(jadwalHarian);
    }
}
