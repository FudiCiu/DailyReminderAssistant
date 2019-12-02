package com.latihanandroid.dailyreminderassistant;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.latihanandroid.dailyreminderassistant.helper.LogInHelper;

public class LogInSignUpActivity extends AppCompatActivity {
    private int requestCode=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_sign_up);
        requestCode=getIntent().getIntExtra(LogInHelper.RC_EXTRA,-1);
        switch (requestCode){
            case LogInHelper.LOG_IN_RC:
                break;
            case LogInHelper.SIGN_UP_RC:
                break;
            case -1:
                Toast.makeText(getApplicationContext(),R.string.login_failed,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
