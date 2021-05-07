package com.example.meks_enginering.requests.pendingRequests;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meks_enginering.R;
import com.example.meks_enginering.menu.menu;
import com.example.meks_enginering.requests.completedRequests.completedRequest;
import com.example.meks_enginering.user.profile;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class pendingRequests extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_pending_requests);
        bottomNav();
    }

    public void bottomNav(){
        bottomNavigationView=findViewById(R.id.activity_bottom_navigation_barp);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.History:
                      //  Toast.makeText(getApplicationContext(),"Pending",Toast.LENGTH_SHORT).show();
                        Intent intenth=new Intent(pendingRequests.this, completedRequest.class);
                        startActivity(intenth);
                        break;
                    case R.id.Profile:
                       // Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                        Intent intentp=new Intent(pendingRequests.this, profile.class);
                        startActivity(intentp);
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
