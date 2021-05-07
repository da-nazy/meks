package com.example.meks_enginering.requests.completedRequests;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meks_enginering.R;
import com.example.meks_enginering.menu.menu;
import com.example.meks_enginering.requests.pendingRequests.pendingRequests;
import com.example.meks_enginering.user.profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class completedRequest extends AppCompatActivity {
    /* Access modifiers changed, original: protected */
    BottomNavigationView bottomNavigationView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_completed_request);
        bottomNav();
    }
    public void bottomNav(){
        bottomNavigationView=findViewById(R.id.activity_bottom_navigation_barp);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.Pending:
                        //  Toast.makeText(getApplicationContext(),"Pending",Toast.LENGTH_SHORT).show();
                        Intent intentp=new Intent(completedRequest.this, pendingRequests.class);
                        startActivity(intentp);
                        break;
                    case R.id.Profile:
                        // Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                        Intent intenth=new Intent(completedRequest.this, profile.class);
                        startActivity(intenth);
                        break;
                    case R.id.Home:
                        Intent intentm=new Intent(getApplicationContext(), menu.class);
                        startActivity(intentm);
                        break;


                }

                return true;
            }
        });
    }
}
