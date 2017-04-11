package com.example.tzadmin.tzsk_windows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
    }

    public void onClick (View view) {
        if(!tb_login.getText().toString().equals("") && !tb_password.getText().toString().equals("")) {
            SaveAuth.set(tb_login.getText().toString(), tb_password.getText().toString());
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            helper.message(this,"Логин и пароль не может быть пустым.");
        }
    }
}
