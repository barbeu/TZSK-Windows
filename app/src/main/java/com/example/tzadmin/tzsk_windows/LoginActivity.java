package com.example.tzadmin.tzsk_windows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.tzadmin.tzsk_windows.HttpModule.Http;
import com.example.tzadmin.tzsk_windows.HttpModule.HttpResp;
import com.example.tzadmin.tzsk_windows.SaveAuthModule.SaveAuth;

public class LoginActivity extends AppCompatActivity {

    EditText tb_login, tb_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tb_login = (EditText)findViewById(R.id.tb_login);
        tb_password = (EditText)findViewById(R.id.tb_password);
        SaveAuth.SetUp(this);
        isStartMainActivity();
    }

    public void onClick (View view) {
        if(!tb_login.getText().toString().equals("") && !tb_password.getText().toString().equals("")) {
            SaveAuth.set(tb_login.getText().toString(), tb_password.getText().toString());
            isStartMainActivity();
        } else {
            helper.message(this, helper.MSG.EMPTY_AUTH_DATA, Toast.LENGTH_SHORT);
        }
    }

    private void isStartMainActivity () {
        if(checkAuth()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private boolean checkAuth () {
        try {
            Http http = new Http();
            if(SaveAuth.get()) {
                HttpResp resp = http.GET("auth");
                switch (resp.Code) {
                    case 200 :
                        return true;
                    case 401 :
                        helper.message(this, helper.MSG.INCORRECT_AUTH_DATA, Toast.LENGTH_SHORT);
                        break;
                }
            }
        } catch (Exception ex) {
            helper.message(this, helper.MSG.INTERNET_NOT_CONNECTING, Toast.LENGTH_SHORT);
            tb_login.setText(SaveAuth.login);
            tb_password.setText(SaveAuth.psswd);
        }
        return false;
    }
}