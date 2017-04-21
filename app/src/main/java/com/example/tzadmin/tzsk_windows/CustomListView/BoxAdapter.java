package com.example.tzadmin.tzsk_windows.CustomListView;

/**
 * Created by tzadmin on 18.04.17.
 */

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.tzadmin.tzsk_windows.DatabaseModule.DatabaseModels.Meas;
import com.example.tzadmin.tzsk_windows.R;

public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Meas> objects;

    public BoxAdapter(Context context, ArrayList<Meas> meases) {
        ctx = context;
        objects = meases;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Meas p = getProduct(position);

        ((TextView) view.findViewById(R.id.meas_addres)).setText(p.Address);
        ((TextView) view.findViewById(R.id.meas_date)).setText(p.Date);
        ((TextView) view.findViewById(R.id.meas_timeStart)).setText(p.TimeStart + "/" + p.EndTime);
        return view;
    }

    Meas getProduct(int position) {
        return ((Meas) getItem(position));
    }
}
