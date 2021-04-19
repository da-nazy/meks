package com.example.meks_enginering.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meks_enginering.R;
import com.example.meks_enginering.api.APIResponse;
import com.example.meks_enginering.api.ApiListener;
import com.example.meks_enginering.api.MeksApi;
import com.example.meks_enginering.api.customerAccountVerification;
import com.example.meks_enginering.order.order_process;
import com.example.meks_enginering.utility.urls;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sign_up extends AppCompatActivity implements ApiListener {
    Button logIn;
    Button signUp;
    EditText emailPhone,password,passwordcheck,surname;
    boolean emailcheck, phonecheck;
    MeksApi meksApi;
    Retrofit retrofit;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_sign_up);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        Button button = (Button) findViewById(R.id.log_in_btn);
        bindView();
        retro();


        this.logIn = button;
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(sign_up.this, sign_in.class));
            }
        });
    }

    public void signUp(View v) throws IOException, InterruptedException {
      //  startActivity(new Intent(this, order_process.class));
       if(inputCheck()){
           check();
           if(phonecheck){
               Toast.makeText(getApplicationContext(),"Phone can't be used currently for verification",Toast.LENGTH_SHORT).show();
           }else if(emailcheck){
           if(urls.isConnected()){
               customerAccountRegistration();
           }else{
               Toast.makeText(getApplicationContext(),"No Network Connection",Toast.LENGTH_SHORT).show();
           }
           }
       }

    }
      public void customerAccountRegistration(){
          MeksApi meksApi = retrofit.create(MeksApi.class);
          APIResponse.callRetrofit(meksApi.customerRegistration(emailPhone.getText().toString().trim(),password.getText().toString().trim(),surname.getText().toString().trim(),urls.securityKey()), "accountRegistration", this, this);

      }
    public void bindView() {
        emailPhone = findViewById(R.id.email_phone);
        password=findViewById(R.id.reg_pass);
        passwordcheck=findViewById(R.id.reg_pass_check);
        surname=findViewById(R.id.surname);
    }

    public void retro()
    {
        Gson gson = new GsonBuilder().serializeNulls().create();
            retrofit = new Retrofit.Builder()
                .baseUrl(urls.meks())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        meksApi = retrofit.create(MeksApi.class);
    }
    public boolean inputCheck(){
        boolean check=false;
        // for email and phone input
        if(emailPhone.getText().toString().trim().isEmpty()){
            check=false;
            emailPhone.setError("Empty fields");
        }else{
            check=true;
        }

        // for surname
        if(surname.getText().toString().trim().isEmpty()){
            check=false;
            surname.setError("Empty fields");
        }else{
            check=true;
        }
         // for password check

        if(password.getText().toString().trim().isEmpty()){
            check=false;
            password.setError("Empty fields");
        }else{
            check=true;
        }
          // for password check input
        if(passwordcheck.getText().toString().trim().isEmpty()){
            check=false;
            passwordcheck.setError("Empty fields");
        }else{
            check=true;
        }

        if(password.getText().toString().trim().equals(passwordcheck.getText().toString().trim())){
            check=true;

        }else{
            password.setError("Doesn't match");
            passwordcheck.setError("Doesn't match ");
        }

        return check;
    }
    public boolean check() {
        boolean valid = false;

        if (emailPhone.getText().toString().trim().isEmpty()) {
            // empty input
            emailPhone.setError("Empty value");
            valid = false;
        } else {
            // non empty input
            // check for email
            if (Patterns.EMAIL_ADDRESS.matcher(emailPhone.getText().toString().trim()).matches()) {
                // Matches an email address
                emailcheck = true;
                valid = true;
            } else {
                // doesn't match an email address check for number
                // trim() to remove space
                // first convert string to character array
                // check if character array is upt to eleven
                // check if character is an integer
                char[] phone = emailPhone.getText().toString().trim().toCharArray();
                int phoneNumber[] = new int[phone.length];
                if (phone.length == 11) {
                    // phone is upto eleven digits
                    for (int a = 0; a < phone.length; a++) {
                        // convert from char to int
                        // check if not int and trow an error
                        // then convert from charArray to int Array
                        try {
                            phoneNumber[a] = Integer.parseInt(phone[a] + "");
                            phonecheck = true;
                            valid = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                            // customToast("Not a valid Phone Number");
                            emailPhone.setError("Not a valid Phone Number");
                            valid = false;
                        }
                    }
                } else {
                    // phone not up to eleven digits
                    //customToast("Not up to eleven");
                    emailPhone.setError("Eleven digits required");
                    valid = false;
                }


            }
        }
        return valid;
    }

    @Override
    public void error(String strApiName, String error) {
        if(strApiName.equals("accountRegistration")){
            Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failure(String strApiName, String message) {
        if(strApiName.equals("accountRegistration")){
            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void success(String strApiName, Object obj) {
        customerAccountVerification customer=(customerAccountVerification) obj;
        if(strApiName.equals("accountRegistration")){
          Toast.makeText(getApplicationContext(),obj.toString(),Toast.LENGTH_SHORT).show();
          // To get userid here
            // To save and move to verifcation section
            // also saved the current time in milles to determine how long the verification has lasted
            saveSignUpDetails(emailPhone.getText().toString().trim(),customer.getUserid(),System.currentTimeMillis());
            Intent intent=new Intent(sign_up.this,verification.class);
            startActivity(intent);
        }
    }

    public void verify(View view) {
        // This should call the verify dialog for the user and should give room for the needed params
        Toast.makeText(getApplicationContext(),"Verify clicked",Toast.LENGTH_SHORT).show();
    }
    public void checkPendingVerification(){
        // check the pending verification if exit else remove the the verification question
        // and  move to the verification page
        // if does not exit leave user might need verification

    }
    public void emailSentDialog(){

    }
    // need to save users details
    public void saveSignUpDetails(String email,String userid, long time){
        // Things needed for verification
        // Email
        // USERID
        // 1 pending verification means the user has registered but yet to be verified
        // and should be sent to the verification page
        SharedPreferences.Editor localEditor = getApplicationContext().getSharedPreferences("name",MODE_PRIVATE).edit();
        localEditor.putString("EMAIL",email);
        localEditor.putString("userid",userid);
        localEditor.putInt("pendingVerification",1);
        localEditor.putLong("time",time);
        localEditor.apply();
    }

}


