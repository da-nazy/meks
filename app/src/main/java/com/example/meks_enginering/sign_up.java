package com.example.meks_enginering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class sign_up extends AppCompatActivity {
Button logIn,signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
            logIn=findViewById(R.id.log_in_btn);
            logIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent =new Intent(sign_up.this,sign_in.class);
                    startActivity(intent);
                }
            });



    }
    public void signUp(View v){
        Intent intent=new Intent(sign_up.this,menu.class);
        startActivity(intent);
    }
}