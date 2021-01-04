package com.example.meks_enginering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class order_details extends AppCompatActivity {

    private ArrayList<order_detail_model> mOrderDetail;
    private RecyclerView mRecyclview;

    // The adape ris the bridge between our data and the recyclerview
    private order_detail_adapter mAdapter;
    // responsible for arranging item in our list
    private RecyclerView.LayoutManager mLayoutManger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        populateList();
        buildRecyclerView();

    }


    public void buildRecyclerView(){
        mRecyclview=findViewById(R.id.sub_menu_recycle);
        // if recycler view won't change in size
        mRecyclview.setHasFixedSize(true);
        mLayoutManger=new LinearLayoutManager(this);
        mAdapter=new order_detail_adapter(mOrderDetail);
        mRecyclview.setLayoutManager(mLayoutManger);
        mRecyclview.setAdapter(mAdapter);
    }

    public void bindView(){

    }

    public void populateList(){
        mOrderDetail =new ArrayList<>();
        mOrderDetail.add(new order_detail_model(R.mipmap.at_repair,"Os Install","Changing of the operating system and driving."));
        mOrderDetail.add(new order_detail_model(R.mipmap.at_repair,"Os Install","Changing of the operating system and driving."));
        mOrderDetail.add(new order_detail_model(R.mipmap.at_repair,"Os Install","Changing of the operating system and driving."));
        mOrderDetail.add(new order_detail_model(R.mipmap.at_repair,"Os Install","Changing of the operating system and driving."));

    }
}