package com.example.tzadmin.tzsk_windows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import com.example.tzadmin.tzsk_windows.AuthModule.Auth;
import com.example.tzadmin.tzsk_windows.CustomListView.BoxAdapter;
import com.example.tzadmin.tzsk_windows.DatabaseModule.Database;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseHelper;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseModels.Meas;
import com.example.tzadmin.tzsk_windows.HttpModule.Http;
import com.example.tzadmin.tzsk_windows.HttpModule.HttpResp;
import com.example.tzadmin.tzsk_windows.JsonModule.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by tzadmin on 17.04.17.
 */

public class Tab1Meases extends Fragment {
    ArrayList<Meas> meases = new ArrayList<Meas>();
    BoxAdapter boxAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1meases, container, false);

        DatabaseHelper dbHelper = new DatabaseHelper(rootView.getContext());
        Database.SetUp(dbHelper.getReadableDatabase());

        Http http = new Http();
        HttpResp resp = http.GET(helper.HTTP_QUERY_GETORDERS, Auth.login, Auth.passwd);
        meases = JSON.parse(resp.body);
        Database.insertMeases(meases, Auth.id);
        Collections.sort(meases, new FishNameComparator());
        boxAdapter = new BoxAdapter(rootView.getContext(), meases);

        ListView lvMain = (ListView) rootView.findViewById(R.id.lvMain);
        lvMain.setAdapter(boxAdapter);

        return rootView;
    }

    public class FishNameComparator implements Comparator<Meas>
    {
        public int compare(Meas left, Meas right) {
            return left.Date.compareTo(right.Date);
        }
    }
}

