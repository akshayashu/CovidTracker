package com.example.covidtracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidtracker.models.RootData;
import com.example.covidtracker.models.Statewise;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView activeCases, recoveredCases, deathCases, whoLink, globalCases, IndiaCases, title;
    ListView listView;
    ProgressBar progressBar;
    PieChart pieChart;
    static ArrayList<Statewise> stateList = new ArrayList<>();
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

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
        title = findViewById(R.id.title);

        loadIntAd();

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
        });

        whoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.mohfw.gov.in/"));
                startActivity(intent);
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                super.onAdLoaded();
                Toast.makeText(MainActivity.this, "Loaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                super.onAdFailedToLoad(adError);
                Log.d("TAG ADERROR", adError.toString());
//                mAdView.loadAd(adRequest);
//                Toast.makeText(MainActivity.this, "Load Failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                super.onAdOpened();
                Toast.makeText(MainActivity.this, "Opened", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                super.onAdClicked();
                Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
                super.onAdClosed();
                Toast.makeText(MainActivity.this, "Closed", Toast.LENGTH_LONG).show();
            }
        });

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
                Call<RootData> list = RetrofitClient.getInstance().getMyApi().getTeams();
                list.enqueue(new Callback<RootData>() {
                    @Override
                    public void onResponse(Call<RootData> call, Response<RootData> response) {
                        if (response.isSuccessful()) {
                            Log.d("NETCALL", "success");
                            ArrayList<Statewise> ll = new ArrayList<>(response.body().statewise);
                            Log.d("NETCALL size", String.valueOf(ll.size()));
                            setList(ll);
                        }
                    }

                    @Override
                    public void onFailure(Call<RootData> call, Throwable t) {
                        Log.d("NetCall", t.getLocalizedMessage());
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private void setList(ArrayList<Statewise> ll) {
        for (int i = 0; i < ll.size(); i++) {
            stateList.add(ll.get(i));
            Log.d("state " + (i + 1), stateList.get(i).state);
        }
        stateListAdapter adapter = new stateListAdapter(getApplicationContext(), R.layout.table_elements, stateList);
        listView.setAdapter(adapter);
        Helper.getListViewSize(listView);

        DecimalFormat formatter = new DecimalFormat("#,###,###,###");

        String a = stateList.get(0).active;
        String b = stateList.get(0).recovered;
        String c = stateList.get(0).deaths;
        String d = stateList.get(0).confirmed;

        String act = formatter.format(Integer.parseInt(a));
        String rec = formatter.format(Integer.parseInt(b));
        String deaths = formatter.format(Integer.parseInt(c));
        String total = formatter.format(Integer.parseInt(d));
        String world = formatter.format(Integer.parseInt(d)*3);
        stateList.remove(0);

        activeCases.setText(act);
        recoveredCases.setText(rec);
        deathCases.setText(deaths);
        IndiaCases.setText(total);
        globalCases.setText(world);

        progressBar.setVisibility(View.GONE);


        ArrayList<PieEntry> pie = new ArrayList<>();
        pie.add(new PieEntry(Integer.parseInt(a),"Active"));
        pie.add(new PieEntry(Integer.parseInt(b),"Recovered"));
        pie.add(new PieEntry(Integer.parseInt(c),"Deaths"));

        PieDataSet pieDataSet = new PieDataSet(pie,"         Rotatable Chart");
        pieDataSet.setColors(new int[] { R.color.orange, R.color.green, R.color.red }, getApplicationContext());
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setVisibility(View.VISIBLE);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Total Cases");
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.animate();
    }

    private void loadIntAd(){

        AdRequest adRequest1 = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-4970459579462716/4999405309", adRequest1,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
//                        loadIntAd();
                    }
                });
    }
}
