package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    TextView activeCases, recoveredCases, deathCases, whoLink;
    ListView listView;
    ProgressBar progressBar;
    ArrayList<stateData> stateList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activeCases = findViewById(R.id.activeCases);
        recoveredCases = findViewById(R.id.totalRecovered);
        deathCases = findViewById(R.id.totalDeath);
        whoLink = findViewById(R.id.whoLink);
        listView = findViewById(R.id.listView);
        progressBar =findViewById(R.id.progressBar);

        whoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.mohfw.gov.in/"));
                startActivity(intent);
            }
        });

        new Content().execute();
    }

    private class Content extends AsyncTask<Void, Void, Void> {
        String active, recov, death;
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
                Elements act = doc.select("div.site-stats-count").select("strong").eq(0);
                Elements rec = doc.select("div.site-stats-count").select("strong").eq(1);
                Elements dead = doc.select("div.site-stats-count").select("strong").eq(2);
                active = act.text();
                recov = rec.text();
                death = dead.text();
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
            activeCases.setText(active);
            recoveredCases.setText(recov);
            deathCases.setText(death);
            for(int i=0;i<33;i++) {
                String a, b, c, d, e;
                a=sNo[i];
                b=sName[i];
                c=sAct[i];
                d=sRec[i];
                e=sDeath[i];
                stateList.add(new stateData(a, b, c, d, e));

            }
            if (stateList != null && stateList.size() > 32) {
                stateListAdapter adapter = new stateListAdapter(getApplicationContext(), R.layout.table_elements, stateList);
                listView.setAdapter(adapter);
                Helper.getListViewSize(listView);
            }
            progressBar.setVisibility(View.GONE);
        }
    }
}
