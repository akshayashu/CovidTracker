package com.example.covidtracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.covidtracker.models.Statewise;

import java.util.ArrayList;

public class stateListAdapter extends ArrayAdapter<Statewise> {
    Context mContext;
    int mResource;

    public stateListAdapter(Context context, int resource, @NonNull ArrayList<Statewise> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sn = String.valueOf(position + 1);
        String stName = getItem(position).getState();
        String totalAct = getItem(position).getActive();
        String totalRec = getItem(position).getRecovered();
        String totalDeath = getItem(position).getDeaths();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView tvSNo = convertView.findViewById(R.id.sNo);
        TextView tvStateName = convertView.findViewById(R.id.stateName);
        TextView tvAct = convertView.findViewById(R.id.active);
        TextView tvRec = convertView.findViewById(R.id.recovered);
        TextView tvDeath = convertView.findViewById(R.id.deaths);

        tvSNo.setText(sn);
        tvStateName.setText(stName);
        tvAct.setText(totalAct);
        tvRec.setText(totalRec);
        tvDeath.setText(totalDeath);

        return convertView;
    }
}
