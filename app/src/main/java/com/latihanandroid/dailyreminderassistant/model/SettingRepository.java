package com.latihanandroid.dailyreminderassistant.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.latihanandroid.dailyreminderassistant.R;

public class SettingRepository {
    private Context context;
    private SharedPreferences sharedPreference;
    private String AUTHENTICATION_KEY;
    private String REMINDER_KEY;

    private Setting setting;
    public SettingRepository(Context context) {
        this.context = context;
        this.sharedPreference= PreferenceManager.getDefaultSharedPreferences(context);
        AUTHENTICATION_KEY=context.getString(R.string.auth_key);
        REMINDER_KEY=context.getString(R.string.reminder_key);
        this.setting=new Setting(sharedPreference.getBoolean(AUTHENTICATION_KEY,false),sharedPreference.getBoolean(REMINDER_KEY,false));
    }
    public boolean getReminderBoolean(){
        return setting.isReminder();
    }
    public boolean getAuthenticationBoolean(){
        return setting.isReminder();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SharedPreferences getSharedPreference() {
        return sharedPreference;
    }

    public void setSharedPreference(SharedPreferences sharedPreference) {
        this.sharedPreference = sharedPreference;
    }

    public String getAUTHENTICATION_KEY() {
        return AUTHENTICATION_KEY;
    }

    public void setAUTHENTICATION_KEY(String AUTHENTICATION_KEY) {
        this.AUTHENTICATION_KEY = AUTHENTICATION_KEY;
    }

    public String getREMINDER_KEY() {
        return REMINDER_KEY;
    }

    public void setREMINDER_KEY(String REMINDER_KEY) {
        this.REMINDER_KEY = REMINDER_KEY;
    }
}
