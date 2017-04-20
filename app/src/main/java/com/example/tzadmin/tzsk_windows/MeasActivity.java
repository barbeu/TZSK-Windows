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
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseModels.User;

public class MeasActivity extends AppCompatActivity {

    Meas meas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meas);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Database.SetUp(dbHelper.getReadableDatabase());
        User user = Database.lastUserLogin();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        meas = Database.selectMeas(id, Auth.id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_meas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.btn_exit:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
