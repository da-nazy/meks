package com.example.meks_enginering.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.example.meks_enginering.R;
import com.example.meks_enginering.api.APIResponse;
import com.example.meks_enginering.api.ApiListener;
import com.example.meks_enginering.api.MeksApi;
import com.example.meks_enginering.api.SubCatRequest;
import com.example.meks_enginering.order.order_details;
import com.example.meks_enginering.requests.completedRequests.completedRequest;
import com.example.meks_enginering.requests.pendingRequests.pendingRequests;
import com.example.meks_enginering.user.profile;
import com.example.meks_enginering.utility.urls;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class menu extends AppCompatActivity implements ApiListener {
    private BottomNavigationView bottomNavigationView;
    private menu_cat_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    private ArrayList<menu_cat_model> mMenuModel;
    private RecyclerView mRecyclerView;
    MeksApi meksApi;
    Retrofit retrofit;

    public void bindView() {
    }

    public void buildRecyclerView() {

        mRecyclerView = findViewById(R.id.main_recycle);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManger = new GridLayoutManager(this, 2);
        mAdapter = new menu_cat_adapter(mMenuModel);
        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setAdapter(mAdapter);
    }


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_menu);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        bottomNav();
        retro();
        populateList();
        buildRecyclerView();
        mAdapter.setOnItemClickListener(new menu_cat_adapter.OnItemClickListener() {
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        catHead(mMenuModel.get(0).getCat_text());
                        getCat(mMenuModel.get(0).getCat_req());
                        break;
                    case 1:
                        catHead(mMenuModel.get(1).getCat_text());
                        getCat(mMenuModel.get(1).getCat_req());
                        break;
                    case 2:
                        catHead(mMenuModel.get(2).getCat_text());
                        getCat(mMenuModel.get(2).getCat_req());
                        break;
                    case 3:
                        catHead(mMenuModel.get(3).getCat_text());
                        getCat(mMenuModel.get(3).getCat_req());
                        break;
                    case 4:
                        catHead(mMenuModel.get(4).getCat_text());
                        getCat(mMenuModel.get(4).getCat_req());
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void populateList() {
        mMenuModel = new ArrayList<>();
        mMenuModel.add(new menu_cat_model("Electrical Repair Service", R.mipmap.electric_repair, "electronics-repairs"));
        mMenuModel.add(new menu_cat_model("Electrical Installation Service ", R.mipmap.electric_installation, "automobile-repairs"));
        mMenuModel.add(new menu_cat_model("Automobile Repair Service", R.mipmap.at_repair, "electrical-repairs"));
        mMenuModel.add(new menu_cat_model("Generator Repair Service", R.mipmap.gen_repair, "generator repair"));

    }

    public void getCat(String paramString) {
        if (paramString.equals("empty")) {
            Toast.makeText(getApplicationContext(), "Work in progresss", Toast.LENGTH_SHORT).show();
        } else {
            //    MeksApi meksApi = retrofit.create(MeksApi.class);
            APIResponse.callRetrofit(meksApi.getSubCategory(urls.securityKey(), paramString), "DetailApi", this, this);

        }
    }

    public void catHead(String paramString) {
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        localEditor.putString("CatName", paramString);
        localEditor.apply();
    }

    public void catRequest(SubCatRequest paramSubCatRequest) {
        SharedPreferences.Editor localEditor = getApplicationContext().getSharedPreferences("up", MODE_PRIVATE).edit();
        localEditor.putString("subCategory", new Gson().toJson(paramSubCatRequest));
        localEditor.commit();
    }

    public void retro() {
        Gson gson = new GsonBuilder().serializeNulls().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(urls.meks())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        meksApi = retrofit.create(MeksApi.class);
    }

    public void success(String paramString, Object paramObject) {
        if (paramString.equals("DetailApi")) {
            catRequest((SubCatRequest) paramObject);
            startActivity(new Intent(this, order_details.class));
        }
    }

    public void error(String paramString1, String paramString2) {
        if (paramString1.equals("DetailApi"))
            Toast.makeText(getApplicationContext(), paramString2, Toast.LENGTH_SHORT).show();
    }

    public void failure(String paramString1, String paramString2) {
        if (paramString1.equals("DetailApi"))
            Toast.makeText(getApplicationContext(), paramString2, Toast.LENGTH_SHORT).show();
    }
    public void bottomNav(){
        bottomNavigationView=findViewById(R.id.activity_bottom_navigation_barm);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.History:
                        //  Toast.makeText(getApplicationContext(),"Pending",Toast.LENGTH_SHORT).show();
                        Intent intenth=new Intent(menu.this, completedRequest.class);
                        startActivity(intenth);
                        break;
                    case R.id.Profile:
                        // Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                        Intent intentp=new Intent(menu.this, profile.class);
                        startActivity(intentp);
                        break;
                    case R.id.Pending:
                        Intent intentm=new Intent(getApplicationContext(), pendingRequests.class);
                        startActivity(intentm);
                        break;

                }

                return true;
            }
        });

    }
}

/* Location:           C:\Users\danazy\Desktop\xs\classes-dex2jar.jar
 * Qualified Name:     com.example.meks_enginering.menu.menu
 * JD-Core Version:    0.6.0
 */