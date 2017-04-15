package com.example.tzadmin.tzsk_windows;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.tzadmin.tzsk_windows.DatabaseModule.Database;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseHelper;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseModels.User;
import com.example.tzadmin.tzsk_windows.HttpModule.Http;
import com.example.tzadmin.tzsk_windows.HttpModule.HttpResp;
import static com.example.tzadmin.tzsk_windows.AuthModule.Auth.setAuth;

public class LoginActivity extends AppCompatActivity {

    EditText tb_login, tb_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tb_login = (EditText)findViewById(R.id.tb_login);
        tb_password = (EditText)findViewById(R.id.tb_password);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Database.SetUp(dbHelper.getReadableDatabase());
        User user = Database.lastLoginUser();

        if(user != null) {
            Integer dateLogin = helper.getTimeMili();
            Database.updateUser(user.id, dateLogin);
            startMainActivity(user.id, user.login, user.password, dateLogin);
        }
    }

    public void onClick (View view) {
        if(!tb_login.getText().toString().equals("") && !tb_password.getText().toString().equals("")) {
            String _login = tb_login.getText().toString(), _password = tb_password.getText().toString();
            Integer dateLogin = helper.getTimeMili();
            int id = Database.isUserExist(_login, _password);

            if(id != -1) {
                Database.updateUser(id, dateLogin);
                startMainActivity(id, _login, _password, dateLogin);
            }
            else {
                if (!helper.InetHasConnection(this)) {
                    helper.message(this, helper.MSG.INTERNET_NOT_CONNECTING, Toast.LENGTH_SHORT);
                    return;
                }
                Http http = new Http();
                HttpResp resp = http.GET(helper.HTTP_QUERY_AUTH, _login, _password);
                switch (resp.Code) {
                    case 200:
                        id = Database.insertUser(_login, _password);
                        startMainActivity(id, _login, _password, dateLogin);
                        break;
                    case 401:
                        helper.message(this, helper.MSG.INCORRECT_AUTH_DATA, Toast.LENGTH_SHORT);
                        break;
                }
            }
        }
        else {
            helper.message(this, helper.MSG.EMPTY_AUTH_DATA, Toast.LENGTH_SHORT);
        }
    }

    private void startMainActivity (int id, String login, String password, Integer dateLogin) {
        setAuth(id, login, password, dateLogin);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}