package com.example.tzadmin.tzsk_windows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.tzadmin.tzsk_windows.SaveAuthModule.SaveAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        SaveAuth.SetUp(this);
    }

    public void onClick (View view) {
        switch (view.getId()) {
            case R.id.btn_exit:
                exit();
                break;
        }
    }

    private void exit() {
        SaveAuth.clear();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}