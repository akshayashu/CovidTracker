package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    TextView activeCases, recoveredCases, deathCases, whoLink, globalCases, IndiaCases;
    ListView listView;
    ProgressBar progressBar;

    ArrayList<stateData> stateList;

    AnyChartView anyChartView;
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
        IndiaCases = findViewById(R.id.IndiaCases);
        globalCases = findViewById(R.id.globalCases);
        anyChartView = findViewById(R.id.anyChart);

        whoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.mohfw.gov.in/"));
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        stateList = (ArrayList<stateData>) bundle.getSerializable("stateList");
        new Content().execute();
    }

    private class Content extends AsyncTask<Void, Void, Void> {
        String active, recov, death, worldCases;
        String[] graphName = {"Active", "Recovered", "Deaths"};
        int[] graphNumber = new int[3];
        String[] sNo = new String[33];
        String[] sName = new String[33];
        String[] sAct = new String[33];
        String[] sRec = new String[33];
        String[] sDeath = new String[33];
        long IndCases;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document doc = Jsoup.connect("https://www.mohfw.gov.in/").get();
                Document wor = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();
                Elements act = doc.select("div.site-stats-count").select("strong").eq(0);
                Elements rec = doc.select("div.site-stats-count").select("strong").eq(1);
                Elements dead = doc.select("div.site-stats-count").select("strong").eq(2);
                Elements worCases = wor.select("div.maincounter-number").select("span").eq(0);

                active = act.text();
                recov = rec.text();
                death = dead.text();
                worldCases = worCases.text();
                IndCases = Integer.parseInt(active) + Integer.parseInt(recov) + Integer.parseInt(death);

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
            if (stateList != null && stateList.size() > 32) {
                stateListAdapter adapter = new stateListAdapter(getApplicationContext(), R.layout.table_elements, stateList);
                listView.setAdapter(adapter);
                Helper.getListViewSize(listView);
            }
            graphNumber[0]=Integer.parseInt(active);
            graphNumber[1]=Integer.parseInt(recov);
            graphNumber[2]=Integer.parseInt(death);
            DecimalFormat formatter = new DecimalFormat("#,###,###,###");
            String yourFormattedString = formatter.format(IndCases);
            IndiaCases.setText(yourFormattedString);
            globalCases.setText(worldCases);

            Pie pie = AnyChart.pie();
            List<DataEntry> dataEntries = new ArrayList<>();

            for(int i=0;i<3;i++){
                dataEntries.add(new ValueDataEntry(graphName[i],graphNumber[i]));
            }

            pie.data(dataEntries);
            anyChartView.setChart(pie);
            anyChartView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
