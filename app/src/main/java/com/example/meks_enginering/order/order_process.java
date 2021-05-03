package com.example.meks_enginering.order;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
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
import com.example.meks_enginering.api.APIResponse;
import com.example.meks_enginering.api.ApiListener;
import com.example.meks_enginering.api.MeksApi;
import com.example.meks_enginering.api.ProductRequest;
import com.example.meks_enginering.api.get_user;
import com.example.meks_enginering.menu.menu;
import com.example.meks_enginering.utility.urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class order_process extends AppCompatActivity implements ApiListener {
      // for the product request
    String sub_category_id,category_id,sub_category_name,sub_category_desc;
    TextView productDesc;
    RadioButton cod;
    RadioGroup main;
    Button order;
    RadioGroup tp;
     get_user user;
   ProductRequest userProductRequest;
   // for Gson and Retrofit global varaiable
    Gson gson;
    Retrofit retrofit;
    // the MeksApi that contains our retrofit call
    MeksApi meksApi;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);

        // for product details
        bindProductDetails(getIntent().getExtras().getString("sub_category_id"),getIntent().getExtras().getString("category_id"), getIntent().getExtras().getString("sub_category_name"),getIntent().getExtras().getString("sub_category_desc"));
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_order_process);
        bindView();
        retrofit();

        order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
           // get the required values for making an order
               Toast.makeText(getApplicationContext(),sub_category_id+"\n"+category_id+"\n"+sub_category_name+"\n"+sub_category_desc+"\n"+userId(),Toast.LENGTH_SHORT).show();
              if(sub_category_id!=null&&userId()+""!=null){
                  // All the value is okay
                  // now to run a network check
                  try {
                      // if the network is working
                      if(urls.isConnected()){
                         // disableing the button from further request
                          order.setEnabled(false);
                          productRequest(userId()+"",category_id,urls.securityKey());
                      }
                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }

            }
        });


    }

    public void bindView()
    {
        order = findViewById(R.id.order_btn);
        productDesc=findViewById(R.id.productDesc);
        // settting the product description name
       if(sub_category_name.isEmpty()){
           // empty value
       }else{
           productDesc.setText(sub_category_name);
       }

    }

   public void bindProductDetails(String sci, String ci,String scn,String scd){
        sub_category_id=sci;
        category_id=ci;
        sub_category_name=scn;
        sub_category_desc=scd;
   }

   public long userId(){
       user = (get_user) new Gson().fromJson(getSharedPreferences("up", 0).getString("UserProfile", ""), get_user.class);
        return  user.getUserid();
   }

   public void retrofit(){
        gson =new GsonBuilder().serializeNulls().create();
        retrofit=new Retrofit.Builder()
                .baseUrl(urls.meks())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        meksApi=retrofit.create(MeksApi.class);
   }

   public void productRequest(String customerId,String serviceId,String seckey){
       APIResponse.callRetrofit(meksApi.produdctRequest(customerId,serviceId,seckey),"productRequest",this ,this);
   }

    @Override
    public void error(String strApiName, String error) {
        order.setEnabled(true);
    if(strApiName.equals("productRequest")){
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
        failedRequest();
    }
    }

    @Override
    public void failure(String strApiName, String message) {
        order.setEnabled(true);
        if(strApiName.equals("productRequest")){
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
            failedRequest();
        }
    }

    @Override
    public void success(String strApiName, Object obj) {
        order.setEnabled(true);
        if(strApiName.equals("productRequest")){
            userProductRequest=(ProductRequest) obj;
            Toast.makeText(getApplicationContext(),userProductRequest.getResponse_desc(),Toast.LENGTH_SHORT).show();
            sentRequest();
        }
    }

    public void sentRequest(){
        Button sentReq;
           View requestSent= LayoutInflater.from(this).inflate(R.layout.product_request_sent,(ViewGroup) findViewById(R.id.content),false);
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setView(requestSent);
            final AlertDialog alertDialog=builder.create();
            alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            alertDialog.show();
            // binding button
            sentReq=requestSent.findViewById(R.id.sent_request_main_menu);
            sentReq.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // close the dialog and send to main menu
                    alertDialog.dismiss();
                    Intent intent=new Intent(order_process.this, menu.class);
                    startActivity(intent);
                    // so that it won't go back to order_process from main menu
                    finish();
                }
            });
    }

    public void failedRequest(){
        Button failedReq;
        View requestSent= LayoutInflater.from(this).inflate(R.layout.product_request_faild,(ViewGroup) findViewById(R.id.content),false);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(requestSent);
        final AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
        // binding button
        failedReq=requestSent.findViewById(R.id.failed_request_main_menu);
        failedReq.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // close the dialog and send to main menu
                alertDialog.dismiss();

            }
        });
    }
}

