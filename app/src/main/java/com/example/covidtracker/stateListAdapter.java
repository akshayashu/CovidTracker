package com.example.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class stateListAdapter extends ArrayAdapter<stateData> {
    private static final String TAG = "stateListAdapter";
    Context mContext;
    int mResource;

    public stateListAdapter(Context context, int resource, @NonNull ArrayList<stateData> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String sn = getItem(position).getsNo();
        String stName = getItem(position).getStateName();
        String totalAct = getItem(position).getActCase();
        String totalRec = getItem(position).getRecCase();
        String totalDeath = getItem(position).getDeathCase();

        stateData stateData = new stateData(sn,stName,totalAct,totalRec,totalDeath);

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
