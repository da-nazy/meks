package com.example.meks_enginering.order;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.example.meks_enginering.R;
import com.example.meks_enginering.api.APIResponse;
import com.example.meks_enginering.api.ApiListener;
import com.example.meks_enginering.api.MeksApi;
import com.example.meks_enginering.api.SubCatRequest;
import com.example.meks_enginering.api.data;
import com.example.meks_enginering.utility.urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class order_details extends AppCompatActivity
        implements ApiListener
{
    private order_detail_adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManger;
    private ArrayList<order_detail_model> mOrderDetail;
    private RecyclerView mRecyclview;
    MeksApi meksApi;
    TextView meksHead;
    Retrofit retrofit;
    SubCatRequest subList;

    public void bindView()
    {
        meksHead = findViewById(R.id.meksHead);
    }

    public void buildRecyclerView()
    {
        RecyclerView localRecyclerView = findViewById(R.id.sub_menu_recycle);
        mRecyclview = localRecyclerView;
        localRecyclerView.setHasFixedSize(true);
         mLayoutManger = new LinearLayoutManager(this);
         mAdapter = new order_detail_adapter(this.mOrderDetail);
         mRecyclview.setLayoutManager(this.mLayoutManger);
         mRecyclview.setAdapter(this.mAdapter);
    }



    public String getHeading()
    {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("CatName", "No Heading");
    }

    public void getSubCat(String paramString)
    {
        if (paramString.equals("empty"))
        {
            Toast.makeText(getApplicationContext(), "Yet to be populated", Toast.LENGTH_SHORT).show();
            return;
        }
        APIResponse.callRetrofit(this.meksApi.getSubCategory(urls.securityKey(), paramString), "subCat", this, this);
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_order_details);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        retrieveCatList();
        bindView();

        meksHead.setText(getHeading());
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urls.meks())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
         meksApi = retrofit.create(MeksApi.class);


        if (subList!= null){
            populateList(subList);
        }

    }

    public void populateList(SubCatRequest subCatRequest)
    {
        mOrderDetail = new ArrayList();
        int i = 0;
        while (i < subCatRequest.getData().size())
        {
            mOrderDetail.add(new order_detail_model(R.mipmap.at_repair,subCatRequest.getData().get(i).getSub_category_name(),"Changing of the operating system and driving"));
            i += 1;
        }
        buildRecyclerView();
    }

    public void retrieveCatList()
    {
        SharedPreferences localSharedPreferences = getSharedPreferences("up", 0);
        subList = ((SubCatRequest)new Gson().fromJson(localSharedPreferences.getString("subCategory", ""), SubCatRequest.class));
    }

    public void success(String paramString, Object paramObject)
    {
        paramString.equals("subCat");
    }
    public void error(String paramString1, String paramString2)
    {
        if (paramString1.equals("subCat"))
            Toast.makeText(getApplicationContext(), paramString2, Toast.LENGTH_SHORT).show();
    }

    public void failure(String paramString1, String paramString2)
    {
        if (paramString1.equals("subCat"))
            Toast.makeText(getApplicationContext(), paramString2, Toast.LENGTH_SHORT).show();
    }
}

/* Location:           C:\Users\danazy\Desktop\xs\classes-dex2jar.jar
 * Qualified Name:     com.example.meks_enginering.order.order_details
 * JD-Core Version:    0.6.0
 */