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
    String query = null, data = null;
    HttpResp responce = null;

    public HttpResp GET (String inquiry) {
        query = inquiry;
        getTask task = new getTask();
        task.execute();
        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HttpResp POST (String inquiry, String postData) {
        query = inquiry;
        data = postData;
        postTask task = new postTask();
        task.execute();
        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    class getTask extends AsyncTask<Void, Void, HttpResp> {
        @Override
        protected HttpResp doInBackground(Void... params) {
            try {
                URL url = new URL(helper.httpServer + query);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", "Basic " + Base64.encodeToString((SaveAuth.login + ":" + SaveAuth.psswd).getBytes(), Base64.NO_WRAP ));
                connection.connect();
                responce = new HttpResp();
                responce.Code = connection.getResponseCode();
                responce.Message = connection.getResponseMessage();
                return responce;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute (HttpResp result) {
            responce = result;
        }
    }

    class postTask extends AsyncTask<Void, Void, HttpResp> {
        @Override
        protected HttpResp doInBackground(Void... inquiry) {
            try {
                URL url = new URL(helper.httpServer + query);
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
                responce = new HttpResp();
                responce.Code = connection.getResponseCode();
                responce.Message = connection.getResponseMessage();
                return responce;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute (HttpResp result) {
            responce = result;
        }
    }
}
