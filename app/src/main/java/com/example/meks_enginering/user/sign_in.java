package com.example.meks_enginering.user;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meks_enginering.R;
import com.example.meks_enginering.admin.admin;
import com.example.meks_enginering.api.APIResponse;
import com.example.meks_enginering.api.ApiListener;
import com.example.meks_enginering.api.MeksApi;
import com.example.meks_enginering.api.get_user;
import com.example.meks_enginering.utility.checkInternet;
import com.example.meks_enginering.utility.urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class sign_in extends AppCompatActivity implements ApiListener {
    EditText email;
    TextView forgot;
    checkInternet isNetwork;
    MeksApi meksApi;
    EditText password;
    Retrofit retrofit;
    Button sign_in;
    get_user userCheck;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bindView();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Retrofit build = new Builder().baseUrl(urls.meks())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .serializeNulls().create())).build();
        retrofit = build;
        meksApi = (MeksApi) build.create(MeksApi.class);

        TextView textView = (TextView) findViewById(R.id.forgot_pwd);

        textView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
      startActivity(new Intent(sign_in.this, forgot_password.class));
            }
        });
       sign_in =  findViewById(R.id.sign_btn);
        sign_in.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                try {
                    if (isConnected() && check()) {
                       testUser();
                    } else {
                        Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    public void testUser() {
        MeksApi apiList = (MeksApi) this.retrofit.create(MeksApi.class);
        APIResponse.callRetrofit(this.meksApi.getUser(this.email.getText().toString(), this.password.getText().toString(), urls.securityKey()), "LoginApi", this, this);
    }

    public void getUsers() {
        this.meksApi.getUser(this.email.getText().toString(), this.password.getText().toString(), urls.securityKey()).enqueue(new Callback<get_user>() {
            public void onResponse(Call<get_user> call, Response<get_user> response) {
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                String str = "Code: ";
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), str + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), str + response.code(), Toast.LENGTH_SHORT).show();
                }
                if (response != null) {
                    get_user user = (get_user) response.body();
                    userCheck = new get_user();
                   userCheck = (get_user) response.body();
                    String userDetails="";
                    userDetails+= "User ID: " + user.getUserid() + "\n";
                    userDetails+= "Email Address: " + user.getEmail_address() + "\n";
                    userDetails+= "Surname: " + user.getSurname() + "\n";
                    userDetails+= "Firstname: " + user.getFirstname() + "\n";
                    userDetails+= "Othername: " + user.getOthername() +  "\n";
                    userDetails+= "Contact: " + user.getContact() + "\n";
                    userDetails+= "Location: " + user.getLocation() + "\n";
                    userDetails+= "Gender: " + user.getGender() + "\n";
                    userDetails+= "Registeration Date: " + user.getReg_date() + "\n";
                    userDetails+= "Account Type: " + user.getAccount_type() + "\n";
                    userDetails+= "Response Code: " + user.getResponse_code() + "\n";
                    userDetails+= ":Response Description " +user.getResponse_desc() + "\n";




                }
            }

            public void onFailure(Call<get_user> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isConnected() throws InterruptedException, IOException {
        return Runtime.getRuntime().exec("ping -i 5 -c 1 google.com").waitFor() == 0;
    }

    public void bindView() {
        email = (EditText) findViewById(R.id.email_phone);
        password = (EditText) findViewById(R.id.password_si);
    }

    public boolean check() {
        String str = "Empty field";
        if (email.getText().toString().isEmpty()) {
            email.setError(str);
            return false;
        } else if (!password.getText().toString().isEmpty()) {
            return true;
        } else {
            password.setError(str);
            return false;
        }
    }

    public void sendUser(get_user value) {
        Editor prefsEditor = getApplicationContext().getSharedPreferences("up", 0).edit();
        prefsEditor.putString("UserProfile", new Gson().toJson((Object) value));
        prefsEditor.commit();
    }

    public void success(String strApiName, Object response) {
        if (strApiName.equals("LoginApi")) {
            get_user user = (get_user) response;
            sendUser(user);
            Toast.makeText(getApplicationContext(), user.getFirstname(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, admin.class));
        }
    }

    public void error(String strApiName, String error) {
        if (strApiName.equals("LoginApi")) {
            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void failure(String strApiName, String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
