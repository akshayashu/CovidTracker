package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class startActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        progressBar = findViewById(R.id.pBar);
        Handler handler = new Handler();

        final Runnable r = () -> {
            progressBar.setVisibility(View.GONE);
            Intent i = new Intent(startActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        };

        handler.postDelayed(r, 2000);


    }

}
