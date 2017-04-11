package com.example.tzadmin.tzsk_windows.HttpModule;

import android.os.AsyncTask;
import android.util.Base64;

import com.example.tzadmin.tzsk_windows.SaveAuthModule.SaveAuth;
import com.example.tzadmin.tzsk_windows.helper;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by tzadmin on 07.04.17.
 */

public class Http {
    String responce = null, data = null;

    public String GET (String inquiry) throws ExecutionException, InterruptedException {
        getTask task = new getTask();
        task.execute(inquiry);
        return task.get();
    }

    public String POST (String inquiry, String postData) throws ExecutionException, InterruptedException {
        data = postData;
        postTask task = new postTask();
        task.execute(inquiry);
        return task.get();
    }

    class getTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... inquiry) {
            try {
                URL url = new URL(helper.httpServer + inquiry);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", "Basic " + Base64.encodeToString((SaveAuth.login + ":" + SaveAuth.psswd).getBytes(), Base64.NO_WRAP ));
                connection.connect();
                int code = connection.getResponseCode();
                return connection.getResponseMessage();

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute (String result) {
            responce = result;
        }
    }

    class postTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... inquiry) {
            try {
                URL url = new URL(helper.httpServer + inquiry);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", "Basic " + Base64.encodeToString((SaveAuth.login + ":" + SaveAuth.psswd).getBytes(), Base64.NO_WRAP ));
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(data);
                writer.flush();
                writer.close();
                os.close();

                connection.connect();
                int code = connection.getResponseCode();
                return connection.getResponseMessage();

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
