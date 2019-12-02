package com.latihanandroid.dailyreminderassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.latihanandroid.dailyreminderassistant.model.AfterGetResultListener;
import com.latihanandroid.dailyreminderassistant.model.KegiatanPenting;
import com.latihanandroid.dailyreminderassistant.model.KegiatanPentingViewModel;
import com.latihanandroid.dailyreminderassistant.model.SettingRepository;
import com.latihanandroid.dailyreminderassistant.ui.date_picker_fragment.DatePickerFragment;
import com.latihanandroid.dailyreminderassistant.ui.time_picker_fragment.TimePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahUpdateKegiatanPentingActivity extends AppCompatActivity
        implements View.OnClickListener, DatePickerFragment.DialogDateListener, TimePickerDialogFragment.DialogTimeListener {
    public static final String EXTRA_RC="com.latihanandroid.dailyreminderassistant.EXTRA_REQUEST_CODE";
    public static final String EXTRA_KEGIATAN_PENTING="com.latihanandroid.dailyreminderassistant.EXTRA_KEGIATAN_PENTING";
    private Calendar dateCal,timeCal;
    public static final int RC_INSERT=501;
    public static final int RC_UPDATE=502;
    public static final int RC_READ_ONLY=503;
    private final int ALERT_DIALOG_CLOSE=10;
    private final int ALERT_DIALOG_DELETE=20;
    private TextView txtWaktu,txtTanggal;
    private EditText edtTempat,edtKeterangan;
    private Spinner spnJenisAlarm;
    private Button btnSimpan,btnPilihWaktu,btnPilihTanggal;
    private KegiatanPentingViewModel kegiatanPentingViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_update_kegiatan_penting);
        txtWaktu=findViewById(R.id.txtWaktu);
        txtTanggal=findViewById(R.id.txtTanggal);
        edtTempat=findViewById(R.id.edtTempat);
        edtKeterangan=findViewById(R.id.edtKeterangan);
        btnSimpan=findViewById(R.id.btnSimpan);
        btnPilihTanggal=findViewById(R.id.btnPilihTanggal);
        btnPilihWaktu=findViewById(R.id.btnPilihJam);
        spnJenisAlarm=findViewById(R.id.spnJenisAlarm);
        kegiatanPentingViewModel=new ViewModelProvider(this).get(KegiatanPentingViewModel.class);
        if (savedInstanceState!=null){
            defaultOnSaveState(savedInstanceState);
        }else {
            if (getIntent().getIntExtra(EXTRA_RC,0)==RC_INSERT){
                defaultInsert();
                if (getSupportActionBar()!=null){
                    getSupportActionBar().setTitle(R.string.tambah);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }else if (getIntent().getIntExtra(EXTRA_RC,0)==RC_UPDATE){
                defaultUpdate();
                if (getSupportActionBar()!=null){
                    getSupportActionBar().setTitle(R.string.update);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }else if (getIntent().getIntExtra(EXTRA_RC,0)==RC_READ_ONLY){
                defaultReadOnly();
            }
        }

        btnSimpan.setOnClickListener(this);
        btnPilihWaktu.setOnClickListener(this);
        btnPilihTanggal.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        long tanggal=dateCal.getTimeInMillis();
        long waktu=timeCal.getTimeInMillis();
        String tempat=edtTempat.getText().toString();
        String keterangan=edtKeterangan.getText().toString();
        outState.putLong("tanggal",tanggal);
        outState.putLong("waktu",waktu);
        outState.putString("tempat",tempat);
        outState.putString("keterangan",keterangan);
        outState.putInt("jenis_alarm",spnJenisAlarm.getSelectedItemPosition());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getIntent().getIntExtra(EXTRA_RC,0)==RC_UPDATE){
            getMenuInflater().inflate(R.menu.tambah_update_menu,menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                tampilkanAlertDialog(ALERT_DIALOG_DELETE);
                break;
            case android.R.id.home:
                tampilkanAlertDialog(ALERT_DIALOG_CLOSE);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getIntExtra(EXTRA_RC,0)==RC_READ_ONLY){
            finish();
        }else
        tampilkanAlertDialog(ALERT_DIALOG_CLOSE);
    }

    private void defaultInsert(){
        SettingRepository settingRepository=new SettingRepository(this);
        dateCal=Calendar.getInstance();
        timeCal=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("HH:mm");
        txtTanggal.setText(simpleDateFormat.format(dateCal.getTime()));
        txtWaktu.setText(simpleDateFormat2.format(timeCal.getTime()));
        edtTempat.setText("");
        edtKeterangan.setText("");
        spnJenisAlarm.setSelection(0);
        spnJenisAlarm.setEnabled(settingRepository.getReminderBoolean());
        btnSimpan.setText(R.string.simpan);
    }

    private void defaultUpdate(){
        KegiatanPenting kegiatanPenting= getIntent().getParcelableExtra(EXTRA_KEGIATAN_PENTING);
        if (kegiatanPenting==null){
            defaultInsert();
        }else {
            SettingRepository settingRepository=new SettingRepository(this);
            dateCal=Calendar.getInstance();
            dateCal.setTime(kegiatanPenting.getMTanggal());
            timeCal=Calendar.getInstance();
            timeCal.setTime(kegiatanPenting.getMWaktu());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("HH:mm",Locale.getDefault());
            txtTanggal.setText(kegiatanPenting.getMTanggalAsLString());
            txtWaktu.setText(kegiatanPenting.getMWaktuAsString());
            edtTempat.setText(kegiatanPenting.getMTempat());
            edtKeterangan.setText(kegiatanPenting.getMKeterangan());
            btnSimpan.setText(R.string.update);
            spnJenisAlarm.setSelection(kegiatanPenting.getMJenisAlarm());
            spnJenisAlarm.setEnabled(settingRepository.getReminderBoolean());

        }
    }
    private void defaultReadOnly(){
        KegiatanPenting kegiatanPenting= getIntent().getParcelableExtra(EXTRA_KEGIATAN_PENTING);
        if (kegiatanPenting==null){
            defaultInsert();
        }else {
            dateCal=Calendar.getInstance();
            dateCal.setTime(kegiatanPenting.getMTanggal());
            timeCal=Calendar.getInstance();
            timeCal.setTime(kegiatanPenting.getMWaktu());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("HH:mm",Locale.getDefault());
            txtTanggal.setText(kegiatanPenting.getMTanggalAsLString());
            btnPilihTanggal.setVisibility(View.GONE);
            txtWaktu.setText(kegiatanPenting.getMWaktuAsString());
            btnPilihWaktu.setVisibility(View.GONE);
            edtTempat.setText(kegiatanPenting.getMTempat());
            edtTempat.setEnabled(false);
            edtKeterangan.setText(kegiatanPenting.getMKeterangan());
            edtKeterangan.setEnabled(false);
            btnSimpan.setText(R.string.update);
            btnSimpan.setVisibility(View.GONE);
            spnJenisAlarm.setSelection(kegiatanPenting.getMJenisAlarm());
            spnJenisAlarm.setEnabled(false);
        }
    }
    private void defaultOnSaveState(Bundle savedState){
        dateCal=Calendar.getInstance();
        dateCal.setTimeInMillis(savedState.getLong("tanggal"));
        timeCal=Calendar.getInstance();
        timeCal.setTimeInMillis(savedState.getLong("waktu"));
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("HH:mm",Locale.getDefault());
        txtTanggal.setText(simpleDateFormat.format(dateCal));
        txtWaktu.setText(simpleDateFormat2.format(timeCal));
        edtTempat.setText(savedState.getString("tempat"));
        edtKeterangan.setText(savedState.getString("keterangan"));
        spnJenisAlarm.setSelection(savedState.getInt("jenis_alarm"));
    }
    private void tampilkanDatePickerDialog(int year,int month,int dayOfMonth){
        DatePickerFragment datePickerFragment=DatePickerFragment.newInstance(year,month,dayOfMonth);
        datePickerFragment.show(getSupportFragmentManager(),DatePickerFragment.DATE_PICKER_FOR_KEGIATAN_PENTING);
    }
    private void tampilkanTimePickerDialog(int hours,int minutes){
        TimePickerDialogFragment timePickerDialogFragment=TimePickerDialogFragment.newInstance(hours,minutes);
        timePickerDialogFragment.show(getSupportFragmentManager(),TimePickerDialogFragment.TIME_PICKER_DIALOG_FOR_KEGIATAN_PENTING);
    }
    private void tampilkanAlertDialog(int type){
        final boolean isDialogClose= type==ALERT_DIALOG_CLOSE;
        String dialogTitle,dialogMessage;
        if (isDialogClose){
            dialogTitle=getString(R.string.batal);
            dialogMessage=getString(R.string.apakah_perubahan_form);
        }else {
            dialogTitle=getString(R.string.action_delete);
            dialogMessage=getString(R.string.apakah_hapus);
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ya), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isDialogClose){
                            finish();
                        }else {
                            prosesDelete();
                            finish();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.tidak), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog= builder.create();
        alertDialog.show();
    }
    private boolean prosesValidasi(){
        if (txtTanggal.getText().toString().equals("")||txtWaktu.getText().equals("")||
                edtTempat.getText().toString().isEmpty()||edtKeterangan.getText().toString().isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private void prosesInsert(){
        if (prosesValidasi()){
            final KegiatanPenting kegiatanPenting=new KegiatanPenting(dateCal.getTime(),timeCal.getTime(),edtTempat.getText().toString(),
                    edtKeterangan.getText().toString(),spnJenisAlarm.getSelectedItemPosition());
            try {
                kegiatanPentingViewModel.insert(kegiatanPenting, new AfterGetResultListener<KegiatanPenting>() {
                    @Override
                    public void AfterGet(KegiatanPenting result) {
                        Log.d(TambahUpdateKegiatanPentingActivity.class.getSimpleName(), "AfterGet: "+result.getMId()+" "+
                                result.getMJenisAlarm());
                        AlarmReceiver.setAlarmKegiatanPenting(getApplicationContext(),result);
                    }
                });
//                tampilkanPesanToast(kegiatanPenting.getMTanggalAsLString()+" "+kegiatanPenting.getMWaktuAsString()+" "
//                +kegiatanPenting.getMTempat()+kegiatanPenting.getMKeterangan()+String.valueOf(kegiatanPenting.getMJenisAlarm()));
            }catch (Exception e){
                Log.e(TambahUpdateKegiatanPentingActivity.class.getSimpleName(), "prosesInsert: ",e);
            }
        }
    }
    private void prosesUpdate(){
        if (prosesValidasi()){
            int id= ((KegiatanPenting) getIntent().getParcelableExtra(EXTRA_KEGIATAN_PENTING)).getMId();
            KegiatanPenting kegiatanPenting=new KegiatanPenting(dateCal.getTime(),timeCal.getTime(),edtTempat.getText().toString(),
                    edtKeterangan.getText().toString(),spnJenisAlarm.getSelectedItemPosition());
            kegiatanPenting.setMId(id);
            kegiatanPentingViewModel.update(kegiatanPenting);
            AlarmReceiver.setAlarmKegiatanPenting(this,kegiatanPenting);

        }
    }
    private void prosesDelete(){
        if (prosesValidasi()){
            KegiatanPenting kegiatanPenting= getIntent().getParcelableExtra(EXTRA_KEGIATAN_PENTING);
            kegiatanPentingViewModel.delete(kegiatanPenting);
            AlarmReceiver.cancelAlarmKegiatanPenting(this,kegiatanPenting);
        }
    }
    private void tampilkanPesanToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPilihTanggal:
                tampilkanDatePickerDialog(dateCal.get(Calendar.YEAR),dateCal.get(Calendar.MONTH),dateCal.get(Calendar.DAY_OF_MONTH));
                break;
            case R.id.btnPilihJam :
                tampilkanTimePickerDialog(timeCal.get(Calendar.HOUR_OF_DAY),timeCal.get(Calendar.MINUTE));
                break;
            case R.id.btnSimpan:
                int RC= getIntent().getIntExtra(TambahUpdateKegiatanPentingActivity.EXTRA_RC,0);
                switch (RC){
                    case RC_INSERT:
                        prosesInsert();
                        finish();
                        break;
                    case RC_UPDATE:
                        prosesUpdate();
                        finish();
                        break;
                     default:
                         tampilkanPesanToast("Error in getIntExtra");
                         break;
                }
                break;
        }
    }

    @Override
    public void onDialogDateSet(int year, int month, int dayOfMonth) {
        dateCal=Calendar.getInstance();
        dateCal.set(Calendar.YEAR,year);
        dateCal.set(Calendar.MONTH,month);
        dateCal.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        txtTanggal.setText(simpleDateFormat.format(dateCal.getTime()));
    }

    @Override
    public void onDialogTimeSet(int hours, int minutes) {
        timeCal=Calendar.getInstance();
        timeCal.set(Calendar.HOUR_OF_DAY,hours);
        timeCal.set(Calendar.MINUTE,minutes);
        SimpleDateFormat simpleDateFormat2=new SimpleDateFormat("HH:mm",Locale.getDefault());
        txtWaktu.setText(simpleDateFormat2.format(timeCal.getTime()));
    }
}
