package com.latihanandroid.dailyreminderassistant.model;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class KegiatanPentingRepository {
    private KegiatanPentingDao kegiatanPentingDao;
    public Context context;
    public KegiatanPentingRepository(Application application){
        DailyAsistantRoomDatabase db= DailyAsistantRoomDatabase.getDatabase(application);
        context=application.getApplicationContext();
        kegiatanPentingDao=db.kegiatanPentingDao();
        if (kegiatanPentingDao==null) Log.d(KegiatanPentingRepository.class.getSimpleName(), "KegiatanPentingRepository: is null kpdao ");
    }
    public KegiatanPentingRepository(Context context){
        DailyAsistantRoomDatabase db= DailyAsistantRoomDatabase.getDatabase(context);
//        context=context.getApplicationContext();
        kegiatanPentingDao=db.kegiatanPentingDao();
        if (kegiatanPentingDao==null) Log.d(KegiatanPentingRepository.class.getSimpleName(), "KegiatanPentingRepository: is null kpdao ");
    }

    public void insert(KegiatanPenting kegiatanPenting){
        WriteAsyncTask writeAsyncTask=new WriteAsyncTask(kegiatanPentingDao,WriteAsyncTask.RC_ADD);
        writeAsyncTask.execute(kegiatanPenting);
    }
    public void insert(KegiatanPenting kegiatanPenting,AfterGetResultListener<KegiatanPenting> afterGetResultListener){
        WriteAsyncTask writeAsyncTask=new WriteAsyncTask(kegiatanPentingDao,WriteAsyncTask.RC_ADD);
        writeAsyncTask.setKegiatanPentingAfterGetResultListener(afterGetResultListener);
        writeAsyncTask.execute(kegiatanPenting);
    }
    public LiveData<List<KegiatanPenting>> getAllKegiatanPenting(){
        LiveData<List<KegiatanPenting>> res=kegiatanPentingDao.readAllKegiatanPenting();
        if (res.getValue()==null) Log.d(KegiatanPentingRepository.class.getSimpleName(), "getAllKegiatanPenting: is null kp");
        return res;
    }
    public LiveData<List<KegiatanPenting>> getAllKegiatanPentingWhereId(int id){
        return kegiatanPentingDao.readAllKegiatanPentingWhereIdIs(id);
    }
    public LiveData<List<KegiatanPenting>> getAllKegiatanPentingWhereJenisAlarm(int jnsAlarm){
        return kegiatanPentingDao.readAllKegiatanPentingWhereJenisAlarmIs(jnsAlarm);
    }
    public void delete(KegiatanPenting kegiatanPenting){
        WriteAsyncTask writeAsyncTask= new WriteAsyncTask(kegiatanPentingDao,WriteAsyncTask.RC_DELETE);
        writeAsyncTask.execute(kegiatanPenting);
    }
    public void delete(KegiatanPenting kegiatanPenting,AfterGetResultListener<KegiatanPenting> kegiatanPentingAfterGetResultListener){
        WriteAsyncTask writeAsyncTask= new WriteAsyncTask(kegiatanPentingDao,WriteAsyncTask.RC_DELETE);
        writeAsyncTask.setKegiatanPentingAfterGetResultListener(kegiatanPentingAfterGetResultListener);
        writeAsyncTask.execute(kegiatanPenting);
    }
    public void update(KegiatanPenting kegiatanPenting){
        WriteAsyncTask writeAsyncTask=new WriteAsyncTask(kegiatanPentingDao,WriteAsyncTask.RC_UPDATE);
        writeAsyncTask.execute(kegiatanPenting);
    }
    public void update(KegiatanPenting kegiatanPenting,AfterGetResultListener<KegiatanPenting> kegiatanPentingAfterGetResultListener){
        WriteAsyncTask writeAsyncTask=new WriteAsyncTask(kegiatanPentingDao,WriteAsyncTask.RC_UPDATE);
        writeAsyncTask.setKegiatanPentingAfterGetResultListener(kegiatanPentingAfterGetResultListener);
        writeAsyncTask.execute(kegiatanPenting);
    }
    private static class WriteAsyncTask extends AsyncTask<KegiatanPenting,Void,KegiatanPenting> {
        private KegiatanPentingDao mKegiatanPentingDao;
        private int mRequestCode;
        private static final int RC_ADD=10;
        private static final int RC_UPDATE=11;
        private static final int RC_DELETE=12;
        private AfterGetResultListener<KegiatanPenting> kegiatanPentingAfterGetResultListener;
        public WriteAsyncTask( KegiatanPentingDao mKegiatanPentingDao, int mRequestCode) {
            this.mKegiatanPentingDao=mKegiatanPentingDao;
            this.mRequestCode = mRequestCode;
        }

        @Override
        protected KegiatanPenting doInBackground(KegiatanPenting... kegiatanPentings) {
            int id;
            switch (mRequestCode){
                case RC_ADD:
                    try {
                        id= (int) mKegiatanPentingDao.insert(kegiatanPentings[0]);
                        Log.d(KegiatanPentingRepository.class.getSimpleName(), "doInBackground: "+String.valueOf(id));
                        kegiatanPentings[0].setMId(id);
                    }catch (Exception e){
                        Log.e(KegiatanPentingRepository.class.getSimpleName(), "doInBackground: "+e);
                    }
                    break;
                case RC_UPDATE:
                    try {
                        mKegiatanPentingDao.update(kegiatanPentings[0]);
                    }catch (Exception e){
                        Log.e(KegiatanPentingRepository.class.getSimpleName(), "doInBackground: "+e);
                    }
                    break;
                case RC_DELETE:
                    mKegiatanPentingDao.delete(kegiatanPentings[0]);
                    break;
            }
            return kegiatanPentings[0];
        }

        @Override
        protected void onPostExecute(KegiatanPenting kegiatanPenting) {
            super.onPostExecute(kegiatanPenting);
            if (kegiatanPentingAfterGetResultListener!=null)
                kegiatanPentingAfterGetResultListener.AfterGet(kegiatanPenting);
        }

        public AfterGetResultListener<KegiatanPenting> getKegiatanPentingAfterGetResultListener() {
            return kegiatanPentingAfterGetResultListener;
        }

        public void setKegiatanPentingAfterGetResultListener(AfterGetResultListener<KegiatanPenting> kegiatanPentingAfterGetResultListener) {
            this.kegiatanPentingAfterGetResultListener = kegiatanPentingAfterGetResultListener;
        }
    }

}
