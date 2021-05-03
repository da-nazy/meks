package com.example.meks_enginering.admin;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.meks_enginering.R;
import com.example.meks_enginering.admin.fragment.employees_fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;

public class admin extends AppCompatActivity implements OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    NavigationView navigationView;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_app_bar_open_drawer_description, R.string.nav_app_close_drawer_description);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new admin_dashboard_fragment()).commit();
          navigationView.setCheckedItem( R.id.nav_menu);
        }
    }

    public void onBackPressed() {
        if ( drawer.isDrawerOpen(GravityCompat.START)) {
             drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_employee /*2131231053*/:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new employees_fragment()).commit();
                navigationView.setCheckedItem( R.id.nav_employee);
                break;
            case R.id.nav_menu /*2131231054*/:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new admin_dashboard_fragment()).commit();
                navigationView.setCheckedItem( R.id.nav_menu);
                break;
        }
        return false;
    }
}
