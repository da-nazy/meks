package com.example.meks_enginering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sign_in extends AppCompatActivity {
TextView forgot;
Button sign_in;
EditText email,password;
    MeksApi meksApi;
    checkInternet isNetwork;
   // String response_code;
    get_user userCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bindView();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //Gson
        Gson gson =new GsonBuilder().serializeNulls().create();
           //For retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://www.meksnigerialtd.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        meksApi=retrofit.create(MeksApi.class);



        forgot=findViewById(R.id.forgot_pwd);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(sign_in.this,forgot_password.class);
                startActivity(intent);
            }
        });

        sign_in=findViewById(R.id.sign_btn);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        // check if its connected
        // check if the fields are filled

           try{
               if(isConnected()&&check()){
                       getUsers();
                       if(userCheck.getResponse_code().contains("404")){
                           // user not found
                           // check for user account status
                           //To include custom notification
                           Toast.makeText(getApplicationContext(),"Invalid credentials ",Toast.LENGTH_SHORT).show();

                       }else{
                           // user found either store user details in shared preference or bundle in intent extra
                           // To be used in profile menu
                           // Main get started is first followed by Main menu  then profile if user chooses
                           Toast.makeText(getApplicationContext(),"valid credentials ",Toast.LENGTH_SHORT).show();

                           // user details saved in sharepeference
                           sendUser(userCheck);
                           // load user activity and populate data from shareperence
                           // if user details is updated data from sharedpreference should also be updated too
                           Intent intent=new Intent(sign_in.this,profile.class);
                           startActivity(intent);
                       }
               }else{
                   Toast.makeText(getApplicationContext(),"Not connected",Toast.LENGTH_SHORT).show();
               }
           }catch (Exception e){

           }


                // should put a loader while the value isn't null
                // values should be stored in shared preference or bundle in the intent

                //Intent intent=new Intent(sign_in.this,profile.class);
                //startActivity(intent);
            }
        });
    }

    public void getUsers(){
        //Call<get_user> call=meksApi.getUser("frank@gmail.com","12345","MEKS_LIVE-iZ6QwzPFK62tx87CrQe4decmjzWk7TarMgLuIwov46ltaOE0CzzG");
        Call<get_user> call=meksApi.getUser(email.getText().toString(),password.getText().toString(),"MEKS_LIVE-iZ6QwzPFK62tx87CrQe4decmjzWk7TarMgLuIwov46ltaOE0CzzG");

       call.enqueue(new Callback<get_user>() {
           @Override
           public void onResponse(Call<get_user> call, Response<get_user> response) {
               Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
               if(!response.isSuccessful()){
                   Toast.makeText(getApplicationContext(),"Code: "+response.code(),Toast.LENGTH_LONG).show();
               }else{
                   Toast.makeText(getApplicationContext(),"Code: "+response.code(),Toast.LENGTH_LONG).show();

               }
               get_user user;
               if(response!=null){
                  user=response.body();

                  // for storing user values in a global variable
                  userCheck=new get_user();
                  userCheck=response.body();

                  String content="";
                   content+="User ID: "+ user.getUserid() + "\n";
                   content+="Email Address: " + user.getEmail_address()+ "\n";
                   content+="Surname: " + user.getSurname() + "\n";
                   content+="Firstname: " + user.getFirstname() + "\n";
                   content+="Othername: " + user.getOthername() + "\n";
                   content+="Contact: " + user.getContact() + "\n";
                   content+="Location: " + user.getLocation() + "\n";
                   content+="Gender: " + user.getGender() + "\n";
                   content+="Registeration Date: " + user.getReg_date()+ "\n";
                   content+="Account Type: " + user.getAccount_type() + "\n";
                   content+="Response Code: " + user.getResponse_code() + "\n";
                   content+=":Response Description " + user.getResponse_desc() + "\n";
                 //  response_code=user.getResponse_code();
               //  Toast.makeText(getApplicationContext(),content,Toast.LENGTH_LONG).show();
               }
           }

           @Override
           public void onFailure(Call<get_user> call, Throwable t) {
               Toast.makeText(getApplicationContext(),"Failure"+t.getMessage(),Toast.LENGTH_LONG).show();
           }
       });
    }
    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -i 5 -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }
    public void bindView(){
        email=findViewById(R.id.name_si);
        password=findViewById(R.id.password_si);
    }

    public boolean check(){
        boolean check;
        if(email.getText().toString().isEmpty()){
            email.setError("Empty field");
            check=false;
        }else if(password.getText().toString().isEmpty()){
            password.setError("Empty field");
            check=false;
        }else{
            check=true;
        }
        return check;
    }
         public void sendUser(get_user value){
             // get_user myObject = new MyObject;
             //set variables of 'myObject', etc.
            // SharedPreferences  mPrefs = getPreferences("pused",MODE_PRIVATE);
             SharedPreferences mPrefs=getApplicationContext().getSharedPreferences("up", MODE_PRIVATE);
             SharedPreferences.Editor prefsEditor = mPrefs.edit();
             Gson gson = new Gson();
             String json = gson.toJson(value);
             prefsEditor.putString("UserProfile", json);
             prefsEditor.commit();
         }
}