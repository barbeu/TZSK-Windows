package com.example.tzadmin.tzsk_windows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.tzadmin.tzsk_windows.AuthModule.Auth;
import com.example.tzadmin.tzsk_windows.DatabaseModule.Database;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseHelper;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseModels.Meas;
import com.example.tzadmin.tzsk_windows.HttpModule.Http;
import com.example.tzadmin.tzsk_windows.HttpModule.HttpResp;
import com.example.tzadmin.tzsk_windows.JsonModule.JSON;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Database.SetUp(dbHelper.getReadableDatabase());

        ArrayList<Meas> meases = new ArrayList<>();
        Http http = new Http();
        HttpResp resp = http.GET(helper.HTTP_QUERY_GETORDERS, Auth.login, Auth.passwd);
        if(resp.Code == 200) {
            meases = JSON.parse(resp.body);
        }

        ArrayList<Meas> test = meases;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_exit:
                Database.deleteUser(Auth.id);
                startLoginActivity();
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    private void startLoginActivity () {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}