package com.latihanandroid.dailyreminderassistant.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.latihanandroid.dailyreminderassistant.AlarmReceiver;
import com.latihanandroid.dailyreminderassistant.R;
import com.latihanandroid.dailyreminderassistant.model.KegiatanPenting;
import com.latihanandroid.dailyreminderassistant.model.KegiatanPentingRepository;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
        private String AUTHENTICATION_KEY;
        private String REMINDER_KEY;
        private SwitchPreferenceCompat authentication_switch,reminder_switch;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            inisiasi();
            buatSumarry();
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        private void inisiasi(){
            AUTHENTICATION_KEY=getString(R.string.auth_key);
            REMINDER_KEY=getString(R.string.reminder_key);
            authentication_switch= (SwitchPreferenceCompat) findPreference(AUTHENTICATION_KEY);
            reminder_switch= (SwitchPreferenceCompat) findPreference(REMINDER_KEY);
        }

        private void buatSumarry(){
            SharedPreferences sharedPreferences= getPreferenceManager().getSharedPreferences();
            authentication_switch.setChecked(sharedPreferences.getBoolean(AUTHENTICATION_KEY,false));
            reminder_switch.setChecked(sharedPreferences.getBoolean(REMINDER_KEY,false));
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals(AUTHENTICATION_KEY)){
                boolean auth_state= sharedPreferences.getBoolean(AUTHENTICATION_KEY,false);
                authentication_switch.setChecked(auth_state);
                if (auth_state){
                    Toast.makeText(getContext(),"Authentication activated ",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getContext(),"Authentication not activated ",Toast.LENGTH_SHORT).show();
                }
            }else if (key.equals(REMINDER_KEY)){
                boolean reminder_state= sharedPreferences.getBoolean(REMINDER_KEY,false);
                reminder_switch.setChecked(reminder_state);
                if (reminder_state){
                    setAllAlarm();
                    Toast.makeText(getContext(),"Reminder activated ",Toast.LENGTH_SHORT).show();
                }else {
                    cancelAllAlarm();
                    Toast.makeText(getContext(),"Reminder not activated ",Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void setAllAlarm(){
            if (getActivity()!=null){
                KegiatanPentingRepository kegiatanPentingRepository=new KegiatanPentingRepository(getActivity().getApplication());
                kegiatanPentingRepository.getAllKegiatanPenting().observe(this, new Observer<List<KegiatanPenting>>() {
                    @Override
                    public void onChanged(List<KegiatanPenting> kegiatanPentings) {
                        for (int i=0;i<kegiatanPentings.size();i++){
                            setOneAlarm(kegiatanPentings.get(i));
                        }
                    }
                });
            }else {
                Toast.makeText(getContext(),"Error in set All Alarm",Toast.LENGTH_SHORT).show();
            }
        }
        private void setOneAlarm(KegiatanPenting kegiatanPenting){
            if (getContext()==null){
                Toast.makeText(getContext(),"Error in set One Alarm",Toast.LENGTH_SHORT).show();
                return;
            }
            AlarmReceiver.setAlarmKegiatanPenting(getContext(),kegiatanPenting);
        }

        private void cancelAllAlarm(){
            if (getActivity()!=null){
                KegiatanPentingRepository kegiatanPentingRepository=new KegiatanPentingRepository(getActivity().getApplication());
                kegiatanPentingRepository.getAllKegiatanPenting().observe(this, new Observer<List<KegiatanPenting>>() {
                    @Override
                    public void onChanged(List<KegiatanPenting> kegiatanPentings) {
                        for (int i=0;i<kegiatanPentings.size();i++){
                            cancelOneAlarm(kegiatanPentings.get(i));
                        }
                    }
                });
            }else {
                Toast.makeText(getContext(),"Error in set All Alarm",Toast.LENGTH_SHORT).show();
            }
        }
        private void cancelOneAlarm(KegiatanPenting kegiatanPenting){
            if (getContext()==null){
                Toast.makeText(getContext(),"Error in set One Alarm",Toast.LENGTH_SHORT).show();
                return;
            }
            AlarmReceiver.cancelAlarmKegiatanPenting(getContext(),kegiatanPenting);
        }
    }
}