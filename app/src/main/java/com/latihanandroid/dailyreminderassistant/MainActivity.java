package com.latihanandroid.dailyreminderassistant;

import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.latihanandroid.dailyreminderassistant.ui.settings.SettingsActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    NavController navController;
    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar toolbar;
    FloatingActionButton fab;
    private static int CODE_AUTHENTICATION_VERIFICATION=241;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_senin,R.id.nav_selasa,
                R.id.nav_rabu, R.id.nav_kamis,
                R.id.nav_jumat,R.id.nav_sabtu,
                R.id.nav_minggu,R.id.nav_kegiatan_penting)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        final Intent intentTambahUpdate=new Intent(MainActivity.this,TambahUpdateJadwalHarianActivity.class);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (navController.getCurrentDestination().getId()){
                    case R.id.nav_senin:
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.HARI_HARIAN_EXTRA,
                                Calendar.MONDAY);
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.GAMBAR_HARIAN_EXTRA,R.drawable.ic_monday);
                        startActivityForResult(intentTambahUpdate,TambahUpdateJadwalHarianActivity.REQUEST_ADD);
                        break;
                    case R.id.nav_selasa:
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.HARI_HARIAN_EXTRA,
                                Calendar.TUESDAY);
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.GAMBAR_HARIAN_EXTRA,R.drawable.ic_tuesday);
                        startActivityForResult(intentTambahUpdate,TambahUpdateJadwalHarianActivity.REQUEST_ADD);
                        break;
                    case R.id.nav_rabu:
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.HARI_HARIAN_EXTRA,
                                Calendar.WEDNESDAY);
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.GAMBAR_HARIAN_EXTRA,R.drawable.ic_wednesday);
                        startActivityForResult(intentTambahUpdate,TambahUpdateJadwalHarianActivity.REQUEST_ADD);
                        break;
                    case R.id.nav_kamis:
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.HARI_HARIAN_EXTRA,
                                Calendar.THURSDAY);
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.GAMBAR_HARIAN_EXTRA,R.drawable.ic_thursday);
                        startActivityForResult(intentTambahUpdate,TambahUpdateJadwalHarianActivity.REQUEST_ADD);
                        break;
                    case R.id.nav_jumat:
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.HARI_HARIAN_EXTRA,
                                Calendar.FRIDAY);
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.GAMBAR_HARIAN_EXTRA,R.drawable.ic_friday);
                        startActivityForResult(intentTambahUpdate,TambahUpdateJadwalHarianActivity.REQUEST_ADD);
                        break;
                    case R.id.nav_sabtu:
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.HARI_HARIAN_EXTRA,
                                Calendar.SATURDAY);
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.GAMBAR_HARIAN_EXTRA,R.drawable.ic_saturday);
                        startActivityForResult(intentTambahUpdate,TambahUpdateJadwalHarianActivity.REQUEST_ADD);
                        break;
                    case R.id.nav_minggu:
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.HARI_HARIAN_EXTRA,
                                Calendar.SUNDAY);
                        intentTambahUpdate.putExtra(TambahUpdateJadwalHarianActivity.GAMBAR_HARIAN_EXTRA,R.drawable.ic_sunday);
                        startActivityForResult(intentTambahUpdate,TambahUpdateJadwalHarianActivity.REQUEST_ADD);
//                        Snackbar.make(view, "Minggu ", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
                        break;
                    case R.id.nav_kegiatan_penting:
                        Intent tambahUpdateKegiatanPenting=new Intent(MainActivity.this,TambahUpdateKegiatanPentingActivity.class);
                        tambahUpdateKegiatanPenting.putExtra(TambahUpdateKegiatanPentingActivity.EXTRA_RC,TambahUpdateKegiatanPentingActivity.RC_INSERT);
                        startActivity(tambahUpdateKegiatanPenting);
                        break;
                    default:
                        Snackbar.make(view, "Default ", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        break;
                }
            }
        });
        if (savedInstanceState==null){
            SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
            if (sharedPreferences.getBoolean(getString(R.string.auth_key),false)){
                KeyguardManager km= (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                if (km.isKeyguardSecure()){
                    String title=getString(R.string.password_required);
                    String desc="Password";
                    Intent intent= km.createConfirmDeviceCredentialIntent(title,desc);
                    startActivityForResult(intent,CODE_AUTHENTICATION_VERIFICATION);
                } else {
                    Toast.makeText(this,R.string.no_security,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent=new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==CODE_AUTHENTICATION_VERIFICATION)
        {
            Toast.makeText(this, R.string.success_auth, Toast.LENGTH_SHORT).show();
        }
//        else if (resultCode==TambahUpdateJadwalHarianActivity.RESULT_ADD&& requestCode==
//        TambahUpdateJadwalHarianActivity.REQUEST_ADD){
//            Toast.makeText(this, R.string.success_add, Toast.LENGTH_SHORT).show();
//
//        }
//        else if (resultCode==TambahUpdateJadwalHarianActivity.RESULT_UPDATE&&requestCode==TambahUpdateJadwalHarianActivity.REQUEST_UPDATE){
//            Toast.makeText(this, R.string.success_update, Toast.LENGTH_SHORT).show();
//        }
//        else if (resultCode==TambahUpdateJadwalHarianActivity.RESULT_DELETE&&requestCode==TambahUpdateJadwalHarianActivity.REQUEST_DELETE){
//            Toast.makeText(this, R.string.success_delete, Toast.LENGTH_SHORT).show();
//        }
        else if (resultCode==RESULT_CANCELED && requestCode==CODE_AUTHENTICATION_VERIFICATION)
        {
            Toast.makeText(this, R.string.failure_auth, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void refreshFragment(){
        if (navController==null) return;
        int currentId=navController.getCurrentDestination().getId();

        navController.popBackStack();
        navController.navigate(currentId);

    }
}
