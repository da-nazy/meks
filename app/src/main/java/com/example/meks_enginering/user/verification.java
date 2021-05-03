package com.example.meks_enginering.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import com.example.meks_enginering.R;
import com.example.meks_enginering.api.APIResponse;
import com.example.meks_enginering.api.ApiListener;
import com.example.meks_enginering.api.MeksApi;
import com.example.meks_enginering.utility.urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class verification extends AppCompatActivity implements ApiListener {
   TextView yourmail;
   EditText fourdigitcode;
   Button verify,v_main_menu;
   String userEmail,userId;
   MeksApi meksApi;
   Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        bindView();
        getValue();
        retro();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        if(userEmail.equals("NOMAIL")){
            // No user email
            System.out.println("No Email found");
        }else{
            yourmail.setText("Please enter the 4 digit pin sent\n"+userEmail);
        }

        verify.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String code;
                if(!fourdigitcode.getText().toString().trim().isEmpty()){
                     code=fourdigitcode.getText().toString().trim();
                    try {
                        verification();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    fourdigitcode.setError("Empty verification code");
                }
                // since we have the code ,userid,seckey and email run the verification api

            }
        });
    }

    public void getValue(){
        SharedPreferences prefs=this.getSharedPreferences("verification",MODE_PRIVATE);
        userEmail=prefs.getString("EMAIL","NOMAIL");
        userId=prefs.getString("userid","NOID");

    }

    public void bindView(){
        yourmail=findViewById(R.id.yourEmail);
        fourdigitcode=findViewById(R.id.fourdigitcode);
        verify=findViewById(R.id.verify);
    }
    public void verification() throws IOException, InterruptedException {
        if(urls.isConnected()){
            MeksApi meksApi =retrofit.create(MeksApi.class);
            APIResponse.callRetrofit(meksApi.verifyAccount(userEmail,fourdigitcode.getText().toString().trim(),userId,urls.securityKey()),"AccountVerification",this,this);
        }

    }

    public void retro(){
        Gson gson = new GsonBuilder().serializeNulls().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(urls.meks())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        meksApi = retrofit.create(MeksApi.class);
    }

    @Override
    public void error(String strApiName, String error) {
        if(strApiName.equals("AccountVerification")){
            Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failure(String strApiName, String message) {
        if(strApiName.equals("AccountVerification")){
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void success(String strApiName, Object obj) {
        if(strApiName.equals("AccountVerification")){
           // to call a dialog box
            verifiedDialog();
        }
    }

    public void verifiedDialog(){
       View verifiedDialog= LayoutInflater.from(this).inflate(R.layout.verified,(ViewGroup) findViewById(R.id.content),false);
       Builder builder =new Builder(this);
       builder.setView(verifiedDialog);
       final AlertDialog alertDialog=builder.create();
     alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
     alertDialog.show();
        v_main_menu=verifiedDialog.findViewById(R.id.v_main_menu);
        v_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adding 2 to the sharepreference to indicate verified
                SharedPreferences.Editor localEditor = getApplicationContext().getSharedPreferences("verification",MODE_PRIVATE).edit();
                localEditor.putInt("pendingVerification",2);
                localEditor.apply();

                Intent intent=new Intent(verification.this,sign_in.class);
                startActivity(intent);
            }
        });
    }
}