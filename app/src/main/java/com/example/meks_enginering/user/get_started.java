package com.example.meks_enginering.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meks_enginering.R;

public class get_started extends AppCompatActivity {
    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_get_started);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}
