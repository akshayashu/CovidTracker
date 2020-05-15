package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class startActivity extends AppCompatActivity {

    ArrayList<stateData> stateList = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        progressBar = findViewById(R.id.pBar);
        new Content().execute();
    }

    private class Content extends AsyncTask<Void, Void, Void> {

        String[] sNo = new String[33];
        String[] sName = new String[33];
        String[] sAct = new String[33];
        String[] sRec = new String[33];
        String[] sDeath = new String[33];

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect("https://www.mohfw.gov.in/").get();

                for(int i=0;i<33;i++){
                    Elements stateInfoNo = doc.select("div.col-xs-12").select("tbody").select("tr").eq(i).select("td").eq(0);
                    Elements stateInfoName = doc.select("div.col-xs-12").select("tbody").select("tr").eq(i).select("td").eq(1);
                    Elements stateInfoAct = doc.select("div.col-xs-12").select("tbody").select("tr").eq(i).select("td").eq(2);
                    Elements stateInfoRec = doc.select("div.col-xs-12").select("tbody").select("tr").eq(i).select("td").eq(3);
                    Elements stateInfoDeath = doc.select("div.col-xs-12").select("tbody").select("tr").eq(i).select("td").eq(4);
                    sNo[i] = stateInfoNo.text();
                    sName[i] = stateInfoName.text();
                    sAct[i] = stateInfoAct.text();
                    sRec[i] = stateInfoRec.text();
                    sDeath[i] = stateInfoDeath.text();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            for(int i=0;i<33;i++) {
                String a, b, c, d, e;
                a=sNo[i];
                b=sName[i];
                c=sAct[i];
                d=sRec[i];
                e=sDeath[i];
                stateList.add(new stateData(a, b, c, d, e));
            }
            progressBar.setVisibility(View.GONE);
            Intent i = new Intent(startActivity.this,MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("stateList",stateList);
            i.putExtras(bundle);
            startActivity(i);
            finish();
        }
    }
}
