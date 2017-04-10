package com.example.tzadmin.tzsk_windows.AuthModule;

import android.os.AsyncTask;
import android.util.Base64;
import com.example.tzadmin.tzsk_windows.helper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;


/**
 * Created by tzadmin on 07.04.17.
 */

public class Auth {
    MyTask task;
    String responce;

    public String getResponce (String inquiry) throws ExecutionException, InterruptedException {
        task = new MyTask();
        task.execute(inquiry);
        responce = task.get();
        return responce;
    }

    class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... inquiry) {
            try {
                URL url = new URL(helper.httpServer + inquiry);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", "Basic " + Base64.encodeToString((helper.login + ":" + helper.psswd).getBytes(), Base64.NO_WRAP ));
                connection.connect();
                int code = connection.getResponseCode();
                String resp = connection.getResponseMessage();
                connection.disconnect();
                return resp;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute (String result) {
            responce = result;
        }
    }
}
