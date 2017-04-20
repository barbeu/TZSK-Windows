package com.example.tzadmin.tzsk_windows;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tzadmin.tzsk_windows.AuthModule.Auth;
import com.example.tzadmin.tzsk_windows.CustomListView.BoxAdapter;
import com.example.tzadmin.tzsk_windows.DatabaseModule.Database;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseModels.Meas;
import com.example.tzadmin.tzsk_windows.HttpModule.HttpResp;
import com.example.tzadmin.tzsk_windows.JsonModule.JSON;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by tzadmin on 17.04.17.
 */

public class Tab1Meases extends Fragment implements AdapterView.OnItemClickListener {
    ArrayList<Meas> meases;
    BoxAdapter boxAdapter;
    ListView lvMain;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab1meases, container, false);
        lvMain = (ListView) rootView.findViewById(R.id.lvMain);
        lvMain.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onStart () {
        super.onStart();
        reloadMeases();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Meas meas = (Meas)parent.getItemAtPosition(position);
        int idMeas = meas.id;
        Intent intent = new Intent(getActivity(), MeasActivity.class);
        intent.putExtra("id", idMeas);
        startActivity(intent);
    }

    public void reloadMeases () {
        if(!helper.InetHasConnection(getActivity()))
            helper.message(getActivity(), helper.MSG.INTERNET_NOT_CONNECTING, Toast.LENGTH_LONG);
        new downloadMeases().execute(helper.HTTP_QUERY_GETORDERS, Auth.login, Auth.passwd);
    }

    private void refreshMeases (String jsonStringMeases) {
        meases = JSON.parse(jsonStringMeases);
        TextView tv = (TextView) rootView.findViewById(R.id.tvMain);

        if (meases == null) {
            meases = Database.selectMeases(Auth.id);
            if (meases == null) {
                tv.setText(Auth.login + " на данный момент у вас нет замеров.");
                return;
            }
        } else {
            Database.insertMeases(meases, Auth.id);
            meases = Database.selectMeases(Auth.id);
            tv.setText("Здравствуйте! " + Auth.login);
        }

        Collections.sort(meases, new FishNameComparator());
        boxAdapter = new BoxAdapter(rootView.getContext(), meases);
        lvMain.setAdapter(boxAdapter);
    }

    public class FishNameComparator implements Comparator<Meas> {
        public int compare(Meas left, Meas right) {
            return left.Date.compareTo(right.Date);
        }
    }

    class downloadMeases extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            publishProgress(new Void[]{});
            URL url;
            HttpURLConnection connection;
            String result = null;
            try {
                url = new URL(helper.httpServer + params[helper.HTTP_PARAM_QUERY]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Authorization", "Basic " + Base64.encodeToString((params[helper.HTTP_PARAM_LOGIN] + ":" + params[helper.HTTP_PARAM_PASSWORD]).getBytes(), Base64.NO_WRAP ));
                connection.connect();
                HttpResp resp = new HttpResp();
                resp.Code = connection.getResponseCode();
                if(resp.Code == helper.CODE_RESP_SERVER_OK)
                    result = helper.streamToString(connection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            refreshMeases(result);
        }
    }
}

