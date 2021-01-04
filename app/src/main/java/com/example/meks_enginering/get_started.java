package com.example.meks_enginering;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class get_started extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}