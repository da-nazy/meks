package com.example.meks_enginering.requests.pendingRequests;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meks_enginering.R;
import com.example.meks_enginering.api.APIResponse;
import com.example.meks_enginering.api.ApiListener;
import com.example.meks_enginering.api.MeksApi;
import com.example.meks_enginering.api.get_user;
import com.example.meks_enginering.api.pendingCustomerRequest;
import com.example.meks_enginering.menu.menu;
import com.example.meks_enginering.requests.completedRequests.completedRequest;
import com.example.meks_enginering.user.profile;
import com.example.meks_enginering.utility.urls;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class pendingRequests extends AppCompatActivity implements ApiListener {
    private BottomNavigationView bottomNavigationView;
   private  RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private pendRequestAdapter requestAdapter;
    private ArrayList<pendRequestModel> pendModel;

    pendingCustomerRequest pendCustomerRequest;
    // For retrofit calls
    Gson gson;
    Retrofit retrofit;
    MeksApi meksApi;

    // for user id
    get_user user;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_pending_requests);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        // include floating action bar to sort by date
        // to put swipe referesh


        retro();
        pendingRequest(userId()+"");
        bottomNav();
        populateRecyclerView();
        buildRecyclerView();
    }

    public void bottomNav(){
        bottomNavigationView=findViewById(R.id.pending_bottom_navigation_bar);

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
    public void populateRecyclerView(){
        pendModel=new ArrayList<>();


       // pendModel.add(new pendRequestModel("20","Electricals","pending",null,"10-10-2021","Os installation","Electricals","20201"));
    }

    public void buildRecyclerView(){
        mRecycler=findViewById(R.id.user_pending_request_recycle);
        mRecycler.setHasFixedSize(true);
        mLayoutManager=new LinearLayoutManager(this);
        requestAdapter=new pendRequestAdapter(pendModel);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(requestAdapter);

    }

    public void retro(){
        gson=new GsonBuilder().serializeNulls().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(urls.meks())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        meksApi=retrofit.create(MeksApi.class);
    }

    public void pendingRequest(String customer_id){
        APIResponse.callRetrofit(meksApi.pendingCustomerRequest(customer_id,true,urls.securityKey()),"customerPendingRequest",this,this);
    }
    public long userId(){
        user = (get_user) new Gson().fromJson(getSharedPreferences("up", 0).getString("UserProfile", ""), get_user.class);
        return  user.getUserid();
    }
    @Override
    public void error(String strApiName, String error) {

        if(strApiName.equals("customerPendingRequest")){
            Toast.makeText(this,error,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failure(String strApiName, String message) {
        if(strApiName.equals("customerPendingRequest")){
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void success(String strApiName, Object obj) {

        if(strApiName.equals("customerPendingRequest")){
            pendCustomerRequest=(pendingCustomerRequest) obj;
            ArrayList<pendRequestModel> model=new ArrayList<>();
            for(int a=0; a<pendCustomerRequest.getData().size(); a++){
                model.add(new pendRequestModel(pendCustomerRequest.getData().get(a).getRequest_id(),pendCustomerRequest.getData().get(a).getService_id(),pendCustomerRequest.getData().get(a).getStatus(),pendCustomerRequest.getData().get(a).getSent_date(),pendCustomerRequest.getData().get(a).getLast_updated(),"Os Installation","Electricals",null));
            }
            upadateList(model);
         //   Toast.makeText(this,obj.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void upadateList(ArrayList<pendRequestModel> customer){
        pendModel.clear();
        pendModel.addAll(customer);
        requestAdapter.notifyDataSetChanged();
    }
    /**
     *   public void updateList(ArrayList<employee_list_model> model) {
     *         mModel.clear();
     *         mModel.addAll(model);
     *         empAdapter.notifyDataSetChanged();
     *     }
     */
}
