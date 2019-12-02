package com.latihanandroid.dailyreminderassistant.model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class JadwalHarianRepository {
    private JadwalHarianDao mJadwalHarianDao;

    JadwalHarianRepository(Application application){
        DailyAsistantRoomDatabase db= DailyAsistantRoomDatabase.getDatabase(application);
        mJadwalHarianDao= db.jadwalHarianDao();
    }

    public void insertJadwalHarian(JadwalHarian jadwalHarian){
        WriteAsyncTask writeAsyncTask=new WriteAsyncTask(mJadwalHarianDao,WriteAsyncTask.RC_ADD);
        writeAsyncTask.execute(jadwalHarian);
    }
    public LiveData<List<JadwalHarian>> getAllJadwalHarian(){
        return mJadwalHarianDao.readAllJadwalHarian();
    }

    public LiveData<List<JadwalHarian>> getJadwalHarianByDay(int day){
        return mJadwalHarianDao.readJadwalHarianWhereHariIs(day);
    }
    public void updateJadwalHarian(JadwalHarian jadwalHarian){
        WriteAsyncTask writeAsyncTask=new WriteAsyncTask(mJadwalHarianDao,WriteAsyncTask.RC_UPDATE);
        writeAsyncTask.execute(jadwalHarian);
    }

    public void deleteJadwalHarian(JadwalHarian jadwalHarian){
        WriteAsyncTask writeAsyncTask=new WriteAsyncTask(mJadwalHarianDao,WriteAsyncTask.RC_DELETE);
        writeAsyncTask.execute(jadwalHarian);
    }

    private static class WriteAsyncTask extends AsyncTask<JadwalHarian,Void,Void>{
        private JadwalHarianDao mJadwalHarianDao;
        private int mRequestCode;
        private static final int RC_ADD=10;
        private static final int RC_UPDATE=11;
        private static final int RC_DELETE=12;

        public WriteAsyncTask(JadwalHarianDao mJadwalHarianDao, int mRequestCode) {
            this.mJadwalHarianDao = mJadwalHarianDao;
            this.mRequestCode = mRequestCode;
        }

        @Override
        protected Void doInBackground(JadwalHarian... jadwalHarians) {
            switch (mRequestCode){
                case RC_ADD:
                    mJadwalHarianDao.create(jadwalHarians[0]);
                    break;
                case RC_UPDATE:
                    try {
                        mJadwalHarianDao.update(jadwalHarians[0]);
                    }catch (Exception e){
                        Log.e(JadwalHarianRepository.class.getSimpleName(), "doInBackground: "+e);
                    }
                    break;
                case RC_DELETE:
                    mJadwalHarianDao.delete(jadwalHarians[0]);
                    break;
            }
            return null;
        }
    }

}
