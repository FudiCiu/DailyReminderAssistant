package com.latihanandroid.dailyreminderassistant;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.latihanandroid.dailyreminderassistant.model.KegiatanPenting;
import com.latihanandroid.dailyreminderassistant.model.SettingRepository;

import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String EXTRA_KEGIATAN_PENTING="com.latihanandroid.dailyreminderassistant.EXTRA_KEGIATAN_PENTING";
    private static final String GROUP_NOTIFICATION="com.latihanandroid.dailyreminderassistant.GROUP_NOTIFICATION";
    private String DATE_FORMAT="yyyy-MM-dd";
    private String TIME_FORMAT="HH:mm";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras=intent.getBundleExtra(EXTRA_KEGIATAN_PENTING);
        KegiatanPenting kegiatanPenting= extras.getParcelable(EXTRA_KEGIATAN_PENTING);
        if (kegiatanPenting==null){
            kegiatanPenting=new KegiatanPenting(Calendar.getInstance().getTime(),Calendar.getInstance().getTime(),"Null"
            ,"Null",1);
        }
        showNotification(context.getApplicationContext(),kegiatanPenting);
        tampilkanToast(context.getApplicationContext(),kegiatanPenting.getMKeterangan());
    }

    public static void setAlarmKegiatanPenting(Context context,KegiatanPenting kegiatanPenting){
        if (kegiatanPenting.getMJenisAlarm()==0) {
            cancelAlarmKegiatanPenting(context,kegiatanPenting);
            return;
        }
        Toast.makeText(context,kegiatanPenting.getMKeterangan(),Toast.LENGTH_SHORT).show();
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context,AlarmReceiver.class);
        Bundle extras= new Bundle();
        extras.putParcelable(EXTRA_KEGIATAN_PENTING,kegiatanPenting);
        intent.putExtra(AlarmReceiver.EXTRA_KEGIATAN_PENTING,extras);
        int requestCodeAlarm=kegiatanPenting.getMId()+1000;
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,requestCodeAlarm,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        SettingRepository settingRepository=new SettingRepository(context);
        if (alarmManager!=null){
            if (settingRepository.getReminderBoolean()){
                Calendar calendar=Calendar.getInstance();
                Date now=calendar.getTime();
                String[] dateAlarm=kegiatanPenting.getMTanggalAsSString().split("-");
                String[] timeAlarm=kegiatanPenting.getMWaktuAsString().split(":");
                calendar.set(Calendar.YEAR,Integer.parseInt(dateAlarm[0]));
                calendar.set(Calendar.MONTH,Integer.parseInt(dateAlarm[1])-1);
                calendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dateAlarm[2]));
                calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(timeAlarm[0]));
                calendar.set(Calendar.MINUTE,Integer.parseInt(timeAlarm[1]));
                calendar.set(Calendar.SECOND,0);
                if (now.getTime()>calendar.getTime().getTime()){
//                    ShowMessageHelper.showToast(context,"Alarm has been expired");
                    return;
                }
                boolean hasSet=(PendingIntent.getBroadcast(context,requestCodeAlarm,intent,PendingIntent.FLAG_NO_CREATE)!=null);
                if (hasSet){
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                }else {
                    Log.d(AlarmReceiver.class.getSimpleName(), "setAlarmKegiatanPenting: alarm has set before");
                }
            }
        }
    };
    public static void cancelAlarmKegiatanPenting(Context context,KegiatanPenting kegiatanPenting){
        AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(context,AlarmReceiver.class);
        intent.putExtra(AlarmReceiver.EXTRA_KEGIATAN_PENTING,kegiatanPenting);
        int requestCodeAlarm=kegiatanPenting.getMId()+1000;
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,requestCodeAlarm,intent,0);
        pendingIntent.cancel();
        if (alarmManager!=null){
            alarmManager.cancel(pendingIntent);
        }
        Log.d(AlarmReceiver.class.getSimpleName(), "cancelAlarmKegiatanPenting: "+"Alarm canceled id: " +String.valueOf(kegiatanPenting.getMId()));
    }
    public static void showNotification(Context context,KegiatanPenting kegiatanPenting){
        int notifId=kegiatanPenting.getMId();
        String channelId="Channel_KP";
        String channelName="Channel_Kegiatan_Penting";
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon= BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_event_note_black_32dp);
        Intent intent= new Intent(context,TambahUpdateKegiatanPentingActivity.class);
        intent.putExtra(TambahUpdateKegiatanPentingActivity.EXTRA_RC,TambahUpdateKegiatanPentingActivity.RC_READ_ONLY);
        intent.putExtra(TambahUpdateKegiatanPentingActivity.EXTRA_KEGIATAN_PENTING,kegiatanPenting);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,notifId,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri alarmSound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder;
        mBuilder=new NotificationCompat.Builder(context,channelId)
                .setContentTitle(kegiatanPenting.getMTempat()+":"+kegiatanPenting.getMWaktuAsString())
                .setContentText(kegiatanPenting.getMKeterangan())
                .setSmallIcon(R.drawable.ic_event_note_black_32dp)
                .setLargeIcon(largeIcon)
                .setGroup(GROUP_NOTIFICATION)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        switch (kegiatanPenting.getMJenisAlarm()){
            case 1:
                mBuilder.setVibrate(new long[]{1000,1000,1000,1000,1000});
                mBuilder.setSound(alarmSound);
                break;
            case 2:
                break;
            case 3:
                mBuilder.setSound(alarmSound);
                break;
            case 4:
                mBuilder.setVibrate(new long[]{1000,1000,1000,1000,1000});
                break;
        }
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(channelId);
            if (notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification=mBuilder.build();
        if (notificationManager!=null){
            notificationManager.notify(notifId,notification);
        }
    }

    private void tampilkanToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
