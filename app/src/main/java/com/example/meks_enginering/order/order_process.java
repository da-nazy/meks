package com.example.meks_enginering.order;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meks_enginering.R;

public class order_process extends AppCompatActivity
{

    RadioButton cod;
    RadioGroup main;
    Button order;
    RadioGroup tp;


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_order_process);
        bindView();

        order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
           // get the required values for making an order
            }
        });


    }

    public void bindView()
    {
        order = findViewById(R.id.order_btn);

    }




}

