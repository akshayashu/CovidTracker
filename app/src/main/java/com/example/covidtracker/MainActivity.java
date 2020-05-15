package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView activeCases, recoveredCases, deathCases, whoLink, globalCases, IndiaCases;
    ListView listView;
    ProgressBar progressBar;
    PieChart pieChart;
    ArrayList<stateData> stateList;

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
        pieChart = findViewById(R.id.pieChart);

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
            DecimalFormat formatter = new DecimalFormat("#,###,###,###");
            String yourFormattedString = formatter.format(IndCases);
            IndiaCases.setText(yourFormattedString);
            globalCases.setText(worldCases);

            ArrayList<PieEntry> pie = new ArrayList<>();
            pie.add(new PieEntry(Integer.parseInt(active),"Active"));
            pie.add(new PieEntry(Integer.parseInt(recov),"Recovered"));
            pie.add(new PieEntry(Integer.parseInt(death),"Deaths"));

            PieDataSet pieDataSet = new PieDataSet(pie,"         Rotatable Chart");
            pieDataSet.setColors(new int[] { R.color.blue, R.color.green, R.color.red }, getApplicationContext());
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setValueTextSize(16f);

            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieChart.setVisibility(View.VISIBLE);
            pieChart.getDescription().setEnabled(false);
            pieChart.setCenterText("Total Cases");
            pieChart.setEntryLabelColor(Color.BLACK);
            pieChart.animate();

            progressBar.setVisibility(View.GONE);
        }
    }
}
