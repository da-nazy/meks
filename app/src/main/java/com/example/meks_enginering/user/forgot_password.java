package com.example.meks_enginering.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meks_enginering.R;

public class forgot_password extends AppCompatActivity {
    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_forgot_password);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}
