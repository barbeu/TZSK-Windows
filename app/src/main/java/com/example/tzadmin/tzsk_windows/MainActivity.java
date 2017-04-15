package com.example.tzadmin.tzsk_windows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.tzadmin.tzsk_windows.AuthModule.Auth;
import com.example.tzadmin.tzsk_windows.DatabaseModule.Database;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Database.SetUp(dbHelper.getReadableDatabase());
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