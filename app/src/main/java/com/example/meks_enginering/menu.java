package com.example.meks_enginering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class menu extends AppCompatActivity {

    private ArrayList<menu_cat_model> mMenuModel;
    private RecyclerView mRecyclerView;

    // The adapter is the bridge between our data and the recyclerview
    private menu_cat_adapter mAdapter;
    // responsible for arranging item in ur list
    private RecyclerView.LayoutManager mLayoutManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        populateList();
        buildRecyclerView();

        mAdapter.setOnItemClickListener(new menu_cat_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getApplicationContext(),"position"+position,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(menu.this,order_details.class);
                startActivity(intent);
            }
        });
    }

    public void buildRecyclerView(){
        mRecyclerView=findViewById(R.id.main_recycle);
        // if recycler view won't change in size
        mRecyclerView.setHasFixedSize(true);
        mLayoutManger=new GridLayoutManager(this,2);
        mAdapter=new menu_cat_adapter(mMenuModel);
        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.setAdapter(mAdapter);

    }
    public void bindView(){

    }
    public void populateList(){
        mMenuModel=new ArrayList<>();
        mMenuModel.add(new menu_cat_model("Electrical Repair Service",R.mipmap.electric_repair));
        mMenuModel.add(new menu_cat_model("Electrical Installation Service ",R.mipmap.ei_service));
        mMenuModel.add(new menu_cat_model("Automobile Repair Service",R.mipmap.at_repair));
        mMenuModel.add(new menu_cat_model("More",R.mipmap.more));
    }

    // to implement a searchable list
    // to remove the Main Category and search from sub category
    // to add more recycleview for the submenu search

}