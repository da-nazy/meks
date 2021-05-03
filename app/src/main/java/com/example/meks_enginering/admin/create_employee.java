package com.example.meks_enginering.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.meks_enginering.R;
import com.example.meks_enginering.api.APIResponse;
import com.example.meks_enginering.api.ApiListener;
import com.example.meks_enginering.api.MeksApi;
import com.example.meks_enginering.api.createEmployee;
import com.example.meks_enginering.api.getUserProfile;
import com.example.meks_enginering.utility.urls;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.ArrayList;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class create_employee extends AppCompatActivity implements ApiListener {
    ArrayAdapter a;
    String accountType;
    EditText account_status;
    EditText account_type;
    EditText cpwd;
    ArrayList<String> details;
    Button done;
    EditText email;
    EditText firstname;
    String[] gend = new String[]{"GENDER", "MALE", "FEMALE"};
    Spinner gender;
    EditText location;
    MeksApi meksApi;
    Intent op;
    int operation;
    EditText other_name;
    EditText phone;
    EditText pwd;
    Retrofit retrofit;
    EditText surName;
   // for storing user details being viewed
    getUserProfile userProfile;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_create_employee);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        gson();
        this.op = getIntent();
        String oper = "";
        this.details = new ArrayList();
        ArrayList stringArrayListExtra = this.op.getStringArrayListExtra("Operation");
        this.details = stringArrayListExtra;
        if (stringArrayListExtra != null) {
            for (int a = 0; a < this.details.size(); a++) {
                oper += details.get(a).toString();
            }
        }
        if (oper != null) {
            Toast.makeText(getApplicationContext(), oper, Toast.LENGTH_SHORT).show();
        }
        if (this.details.get(2) != null) {
            page_operation(Integer.parseInt((String) details.get(2)));
        } else {
            page_operation(1);
        }
        bindView();
        this.done.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                try {
                    if (isConnected() && check()) {
                       createEmployee();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    public void bindView() {
        done = (Button) findViewById(R.id.create_emp);
        surName = (EditText) findViewById(R.id.sur_name_emp);
        firstname = (EditText) findViewById(R.id.first_name_emp);
        email = (EditText) findViewById(R.id.email_emp);
        phone = (EditText) findViewById(R.id.phone_emp);
        other_name = (EditText) findViewById(R.id.other_name_emp);
        gender = (Spinner) findViewById(R.id.gender_emp);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, gend);
        a = arrayAdapter;
        gender.setAdapter(arrayAdapter);
        account_type = (EditText) findViewById(R.id.account_type_emp);
        pwd = (EditText) findViewById(R.id.password_emp);
        cpwd = (EditText) findViewById(R.id.confirm_pasword_emp);
        location = (EditText) findViewById(R.id.location_emp);
        account_status = (EditText) findViewById(R.id.account_status_emp);
    }

    public void page_operation(int operation) {
        if (operation == 2) {
            try {
                if (isConnected()) {
                    getUserDetails();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Please retry when the internet comes back on", Toast.LENGTH_SHORT).show();
        } else if (operation == 3) {
            setEditable(false);
            getUserDetails();
        }
    }

    private void getUserDetails() {
        if (details.get(0) == null || details.get(1) == null) {
            Toast.makeText(getApplicationContext(), "Null values found", Toast.LENGTH_SHORT).show();
            return;
        }else{
            APIResponse.callRetrofit(meksApi.getUserProfile(details.get(0), details.get(1), urls.securityKey()), "getUserProfile", this, this);

        }
    }

    public void setUserValues(getUserProfile userProfile){
        if(userProfile!=null){
            // for surname
            if(userProfile.getSurname()!=null){
                surName.setText(userProfile.getSurname().toString());
            }else{
                surName.setText("");
            }
            // for firstname
            if(userProfile.getFirstname()!=null){
                firstname.setText(userProfile.getFirstname());
            }else{
                firstname.setText("");
            }
            // email,phone ,other_name, gender, location
            // for email
            if(userProfile.getEmail_address()!=null){
                email.setText(userProfile.getEmail_address());
            }else{
                email.setText("");
            }
            // for phone
             if(userProfile.getContact()!=null){
                 phone.setText(userProfile.getContact());
                 phone.setVisibility(View.VISIBLE);
             }else{
                 phone.setText("");
             }
             // other_name
            if(userProfile.getOthername()!=null){
                other_name.setText(userProfile.getOthername());
            }else{
                other_name.setText("");
            }
            // gender
            if(userProfile.getGender()!=null ){
                for(int a=0; a<gend.length;a++){
                    // This loops through the spinner list and checks if it matches the said gender
                    // then it gets the position and sets it as selected.
                    if(gend[a].toLowerCase().contains(userProfile.getGender())){
                        gender.setSelection(a);
                    }
                }
            }
            // location
            if(userProfile.getLocation()!=null || !userProfile.getLocation().isEmpty()){
                location.setVisibility(View.VISIBLE);
                location.setText(userProfile.getLocation());
            }else{
                location.setText("");
            }
            //account_type
            if(userProfile.getAccount_type()!=null || !userProfile.getAccount_type().isEmpty()){
                account_type.setText(userProfile.getAccount_type());
            }else{
                account_type.setText("");
            }
        }
    }

    public void setEditable(boolean set) {
        surName.setEnabled(set);
        firstname.setEnabled(set);
        email.setEnabled(set);
        other_name.setEnabled(set);
        gender.setEnabled(set);
        account_type.setEnabled(set);
        pwd.setEnabled(set);
        cpwd.setVisibility(View.GONE);
    }

    public boolean check() {
        boolean check;
        String str = "Empty field";
        if (surName.getText().toString().isEmpty()) {
            surName.setError(str);
        }
        if (firstname.getText().toString().isEmpty()) {
            firstname.setError(str);
        }
        str = "empty field";
        if (email.getText().toString().isEmpty()) {
            email.setError(str);
        }
        if (other_name.getText().toString().isEmpty()) {
          other_name.setError(str);
        }
        if (gender.getSelectedItem() == null) {
        }
        if (account_type.getText().toString().isEmpty()) {
            account_type.setError("empty field)");
        } else {
            int parseInt = Integer.parseInt(account_type.getText().toString().trim());
            if (parseInt == 1) {
                accountType = "Employee";
            } else if (parseInt == 2) {
               accountType = "User";
            }
        }
        String str2 = "empty";
        if (pwd.getText().toString().trim().isEmpty()) {
            pwd.setError(str2);
        }
        if (cpwd.getText().toString().trim().isEmpty()) {
            cpwd.setError(str2);
        }
        if (!pwd.getText().toString().trim().equals(this.cpwd.getText().toString().trim())) {
            str2 = "Password doesn't match";
           pwd.setError(str2);
            cpwd.setError(str2);
        }
        if (gender.getSelectedItem().equals("GENDER")) {
            check = false;
            Toast.makeText(getApplicationContext(), "Empty gender", Toast.LENGTH_SHORT).show();
        } else {
            check = true;
        }
        if (operation != 2) {
            return check;
        }
        phone.setVisibility(View.VISIBLE);
       location.setVisibility(View.VISIBLE);

       account_status.setVisibility(View.VISIBLE);
        str = "Empty";
        if (phone.getText().toString().trim().isEmpty()) {
            phone.setError(str);
        }
        if (location.getText().toString().trim().isEmpty()) {
            location.setError(str);
        }
        if (!account_status.getText().toString().trim().isEmpty()) {
            return true;
        }
        this.account_status.setError(str);
        return false;
    }

    public void createEmployee() {
        MeksApi apiList = (MeksApi) retrofit.create(MeksApi.class);
        APIResponse.callRetrofit(meksApi.addUser(urls.securityKey(), surName.getText().toString().trim(), firstname.getText().toString().trim(), other_name.getText().toString().trim(), gender.getSelectedItem().toString(), email.getText().toString().trim(), pwd.getText().toString().trim(),accountType), "addEmployee", this, this);
    }

    public void gson() {
        Retrofit build = new Builder().baseUrl(urls.meks()).addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create())).build();
        retrofit = build;
        meksApi = (MeksApi) build.create(MeksApi.class);
    }

    public boolean isConnected() throws InterruptedException, IOException {
        return Runtime.getRuntime().exec("ping -i 5 -c 1 google.com").waitFor() == 0;
    }

    public void success(String strApiName, Object response) {
        if (strApiName.equals("addEmployee")) {
            Toast.makeText(getApplicationContext(), ((createEmployee) response).toString(), Toast.LENGTH_SHORT).show();
        }
        if (strApiName.equals("getUserProfile")) {
            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
            userProfile=(getUserProfile) response;
            setUserValues(userProfile);
        }
    }

    public void error(String strApiName, String error) {
        if (strApiName.equals("addEmployee")) {
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }
        if (strApiName.equals("getUserProfile")) {
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }
    }

    public void failure(String strApiName, String message) {
        if (strApiName.equals("addEmployee")) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
        if (strApiName.equals("getUserProfile")) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}
