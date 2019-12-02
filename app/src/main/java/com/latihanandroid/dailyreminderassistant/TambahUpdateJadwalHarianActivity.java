package com.latihanandroid.dailyreminderassistant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.latihanandroid.dailyreminderassistant.model.JadwalHarian;
import com.latihanandroid.dailyreminderassistant.model.JadwalHarianViewModel;
import com.latihanandroid.dailyreminderassistant.ui.time_picker_fragment.TimePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahUpdateJadwalHarianActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialogFragment.DialogTimeListener {
    public static final String JADWAL_HARIAN_EXTRA="com.latihanandroid.dailyreminderassistant.JADWAL_HARIAN_EXTRA";
    public static final String HARI_HARIAN_EXTRA="com.latihanandroid.dailyreminderassistant.HARI_HARIAN_EXTRA";
    public static final String GAMBAR_HARIAN_EXTRA="com.latihanandroid.dailyreminderassistant.GAMBAR_HARIAN_EXTRA";
    private final int ALERT_DIALOG_CLOSE=10;
    private final int ALERT_DIALOG_DELETE=20;

    public static final int REQUEST_ADD=100;
    public static final int REQUEST_UPDATE=101;
    public static final int REQUEST_DELETE=102;
    JadwalHarianViewModel mJadwalHarianViewModel;
    public static final int RESULT_ADD=200;
    public static final int RESULT_UPDATE=201;
    public static final int RESULT_DELETE=202;
    private JadwalHarian jadwalHarian;
    private TextView txtWaktu,txtHari;
    private EditText edtTempat,edtKeterangan;
    private Button btnSimpan,btnPilihWaktu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_update_jadwal_harian);
        txtWaktu=findViewById(R.id.txtWaktu);
        txtHari=findViewById(R.id.txtHari);
        edtTempat=findViewById(R.id.edtTempat);
        edtKeterangan=findViewById(R.id.edtKeterangan);
        btnPilihWaktu=findViewById(R.id.btnPilihJam);
        btnSimpan=findViewById(R.id.btnSimpan);
        mJadwalHarianViewModel=new ViewModelProvider(this).get(JadwalHarianViewModel.class);
        btnPilihWaktu.setOnClickListener(this);
        btnSimpan.setOnClickListener(this);
        if (savedInstanceState==null){
            jadwalHarian=getIntent().getParcelableExtra(JADWAL_HARIAN_EXTRA);
            if (jadwalHarian==null){
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK,getIntent().getIntExtra(HARI_HARIAN_EXTRA,0));
                String displayday=calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
                setDefautlValueToJadwalHarianCreate(displayday);
                if (getSupportActionBar()!=null){
                    getSupportActionBar().setTitle(R.string.tambah);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }else {
                setDefaultValueToJadwalHarianUpdate(jadwalHarian);
                if (getSupportActionBar()!=null){
                    getSupportActionBar().setTitle(R.string.update);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        }else {
            txtHari.setText(savedInstanceState.getString("hari"));
            txtWaktu.setText(savedInstanceState.getString("waktu"));
            edtTempat.setText(savedInstanceState.getString("tempat"));
            edtKeterangan.setText(savedInstanceState.getString("keterangan"));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (jadwalHarian!=null){
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
        tampilkanAlertDialog(ALERT_DIALOG_CLOSE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String hari=txtHari.getText().toString();
        String waktu=txtWaktu.getText().toString();
        String tempat=edtTempat.getText().toString();
        String keterangan=edtKeterangan.getText().toString();
        outState.putString("hari",hari);
        outState.putString("waktu",waktu);
        outState.putString("tempat",tempat);
        outState.putString("keterangan",keterangan);
    }

    private void setDefautlValueToJadwalHarianCreate(String day){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm",Locale.getDefault());
        txtHari.setText(day);
        txtWaktu.setText(simpleDateFormat.format(calendar.getTime()));
        edtTempat.setText("");
        edtKeterangan.setText("");
        btnSimpan.setText(R.string.simpan);
    }
    private void setDefaultValueToJadwalHarianUpdate(JadwalHarian jadwalHarian){
        txtHari.setText(jadwalHarian.getMHariAsString()==null?"Hari":jadwalHarian.getMHariAsString());
        txtWaktu.setText(jadwalHarian.getMWaktuAsString()==null?"hh:mm":jadwalHarian.getMWaktuAsString());
        edtTempat.setText(jadwalHarian.getMTempat()==null?"":jadwalHarian.getMTempat());
        edtKeterangan.setText(jadwalHarian.getMKeterangan()==null?"":jadwalHarian.getMKeterangan());
        btnSimpan.setText(R.string.update);
    }

    private void tampilkanTimePickerDialog(int hours,int minutes){
        TimePickerDialogFragment timePickerDialogFragment=TimePickerDialogFragment.newInstance(hours,minutes);
        timePickerDialogFragment.show(getSupportFragmentManager(),TimePickerDialogFragment.TIME_PICKER_DIALOG_FOR_JADWAL_HARIAN);
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
//                            Toast.makeText(getApplicationContext(),R.string.fail_add,Toast.LENGTH_SHORT).show();
                            jadwalHarian=prosesHapus();
                            if (jadwalHarian==null){
                                Toast.makeText(getApplicationContext(),R.string.fail_add,Toast.LENGTH_SHORT).show();
                                dialogInterface.cancel();
                            }
                            Intent result=new Intent();
                            result.putExtra(JADWAL_HARIAN_EXTRA,jadwalHarian);
                            setResult(RESULT_DELETE,result);
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
        if (txtHari.getText().toString().equals("")||txtWaktu.getText().equals("hh:mm")||
            edtTempat.getText().toString().isEmpty()||edtKeterangan.getText().toString().isEmpty()){
            return false;
        }else {
            return true;
        }
    }
    private JadwalHarian prosesSimpan(){
        if (prosesValidasi()){
            Calendar calendar=Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK,getIntent().getIntExtra(HARI_HARIAN_EXTRA,0));
            int photo= getIntent().getIntExtra(TambahUpdateJadwalHarianActivity.GAMBAR_HARIAN_EXTRA,0);
            String[] waktu= txtWaktu.getText().toString().split(":");
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(waktu[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(waktu[1]));
            JadwalHarian jadwalHarian=new JadwalHarian(getIntent().getIntExtra(HARI_HARIAN_EXTRA,0),calendar.getTime(),edtTempat.getText().toString(),edtKeterangan.getText().toString());
            jadwalHarian.setMImageId(photo);
//            ShowMessageHelper.showToastJabatan(getApplicationContext(),jadwalHarian);
            mJadwalHarianViewModel.insert(jadwalHarian);
            return jadwalHarian;
        }else {
            Toast.makeText(getApplicationContext(),R.string.error_in_validation,Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    private JadwalHarian prosesUpdate(){
        if (prosesValidasi()){
            int id= ((JadwalHarian) getIntent().getParcelableExtra(JADWAL_HARIAN_EXTRA)).getMId();
            int hari=((JadwalHarian) getIntent().getParcelableExtra(JADWAL_HARIAN_EXTRA)).getMHari();
            int photo= ((JadwalHarian) getIntent().getParcelableExtra(JADWAL_HARIAN_EXTRA)).getMImageId();
            Calendar calendar=Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_WEEK,((JadwalHarian) getIntent().getParcelableExtra(JADWAL_HARIAN_EXTRA)).getMHari(),0);
            String[] waktu= txtWaktu.getText().toString().split(":");
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(waktu[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(waktu[1]));
            JadwalHarian jadwalHarian=new JadwalHarian(hari,calendar.getTime(),edtTempat.getText().toString(),edtKeterangan.getText().toString());
            jadwalHarian.setMId(id);
            jadwalHarian.setMImageId(photo);
            mJadwalHarianViewModel.update(jadwalHarian);
            return jadwalHarian;
        }else {
            Toast.makeText(getApplicationContext(),R.string.error_in_validation,Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    private JadwalHarian prosesHapus(){
        if (prosesValidasi()){
            JadwalHarian jadwalHarian=getIntent().getParcelableExtra(JADWAL_HARIAN_EXTRA);
            mJadwalHarianViewModel.delete(jadwalHarian);
            return jadwalHarian;
        }else {
            Toast.makeText(getApplicationContext(),R.string.error_in_validation,Toast.LENGTH_SHORT).show();
        }
        return null;
    }
    @Override
    public void onClick(View view) {
        Calendar calendar=Calendar.getInstance();
        switch (view.getId()){
            case R.id.btnPilihJam:
                if (!txtWaktu.getText().equals("hh:mm")){
                    String[] waktu=txtWaktu.getText().toString().split(":");
                    calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(waktu[0]));
                    calendar.set(Calendar.MINUTE, Integer.parseInt(waktu[1]));
                }
                tampilkanTimePickerDialog(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE));
                break;
            case R.id.btnSimpan:
                int hsl = 0;
                if(btnSimpan.getText().toString().equals(getString(R.string.simpan))){
                    jadwalHarian=prosesSimpan();
                    hsl=RESULT_ADD;
//                    Toast.makeText(getApplicationContext(),R.string.simpan,Toast.LENGTH_SHORT).show();
                }else if (btnSimpan.getText().toString().equals(getString(R.string.update))){
                    jadwalHarian=prosesUpdate();
                    hsl=RESULT_UPDATE;
//                    Toast.makeText(getApplicationContext(),R.string.update+" "+jadwalHarian.getMTempat(),Toast.LENGTH_SHORT).show();
                }
                if (jadwalHarian==null){
                    Toast.makeText(getApplicationContext(),R.string.fail_add,Toast.LENGTH_SHORT).show();
                    break;
                }
//                Intent result=new Intent();
//                result.putExtra(JADWAL_HARIAN_EXTRA,jadwalHarian);
//                setResult(hsl,result);
                finish();
                break;
        }
    }

    @Override
    public void onDialogTimeSet(int hours, int minutes) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE,minutes);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm", Locale.getDefault());
        txtWaktu.setText(simpleDateFormat.format(calendar.getTime()));
    }
}
