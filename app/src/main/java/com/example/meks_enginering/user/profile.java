package com.example.meks_enginering.user;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.meks_enginering.R;
import com.example.meks_enginering.api.get_user;
import com.example.meks_enginering.menu.menu;
import com.example.meks_enginering.requests.completedRequests.completedRequest;
import com.example.meks_enginering.requests.pendingRequests.pendingRequests;
import com.example.meks_enginering.utility.inputImageCompressor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    TextView contact;
    TextView contact_edit;
    Button e_edit;
    Button e_exit;
    ImageButton edit_image;
    TextView email;
    TextView email_edit;
    TextView gender;
    TextView gender_edit;
    TextView location;
    TextView location_edit;
    TextView name;
    TextView name_edit;
    AppCompatImageView profile_img;
    get_user user = new get_user();

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        retrieveUserProfile();
        bindView();
        bottomNav();
        String str = " ";
        name.setText(user.getSurname() + str + user.getFirstname() + str + user.getOthername());
      email.setText(user.getEmail_address());
        try {
            contact.setText(user.getContact() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
       location.setText(user.getLocation());
        gender.setText(user.getGender());
        edit_image.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_DENIED) {
                        requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 1001);
                        return;
                    }
                 pickImageFromGallery();
                    return;
                }
            pickImageFromGallery();
            }
        });


    }

    private void pickImageFromGallery() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        startActivityForResult(intent, 1000);
    }
public void bottomNav(){
           bottomNavigationView=findViewById(R.id.activity_bottom_navigation_barp);

               bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
               @Override
               public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                   switch (item.getItemId()){
                       case R.id.Pending:
                           Toast.makeText(getApplicationContext(),"Pending",Toast.LENGTH_SHORT).show();
                           Intent intentp=new Intent(profile.this, pendingRequests.class);
                           startActivity(intentp);
                           break;
                       case R.id.History:
                           Toast.makeText(getApplicationContext(),"History",Toast.LENGTH_SHORT).show();
                           Intent intenth=new Intent(profile.this, completedRequest.class);
                           startActivity(intenth);
                           break;
                       case R.id.Home:
                           Intent intentm=new Intent(getApplicationContext(),menu.class);
                           startActivity(intentm);
                           break;

                   }

                   return true;
               }
           });
}
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
            } else {
                pickImageFromGallery();
            }
        }
    }

    /* Access modifiers changed, original: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 1000) {
            inputImageCompressor.compressInputImage(data, this, this.profile_img);
            edit_image.setVisibility(View.GONE);
        }
    }

    public boolean profileEdit(View anchor) {
        PopupMenu popup = new PopupMenu(getApplicationContext(), anchor);
        popup.getMenuInflater().inflate(R.menu.settings, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.chng_img_p) {
                    Toast.makeText(getApplicationContext(), "edit image clicked", Toast.LENGTH_SHORT).show();
                   edit_image.setVisibility(View.VISIBLE);
                } else if (itemId == R.id.edit_profile) {
                    Toast.makeText(getApplicationContext(), "edit profile clicked", Toast.LENGTH_SHORT).show();
                      name_edit.setVisibility(View.VISIBLE);
                      email_edit.setVisibility(View.VISIBLE);
                      contact_edit.setVisibility(View.VISIBLE);
                      location_edit.setVisibility(View.VISIBLE);
                       gender_edit.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        return true;
    }

    public void editImageDialog(View view) {
        final View v = view;
        View pauseDialog = LayoutInflater.from(this).inflate(R.layout.profile_edit_dialog, (ViewGroup) findViewById(R.id.content), false);
        Builder builder = new Builder(this);
        builder.setView(pauseDialog);
        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
         e_exit = (Button) pauseDialog.findViewById(R.id.e_exit);
         e_edit = (Button) pauseDialog.findViewById(R.id.e_edit);
         e_exit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                alertDialog.dismiss();
                v.setVisibility(View.GONE);
            }
        });
        e_edit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void retrieveUserProfile() {
     user = (get_user) new Gson().fromJson(getSharedPreferences("up", 0).getString("UserProfile", ""), get_user.class);
    }

    public void bindView() {
         edit_image = (ImageButton) findViewById(R.id.edit_image);
       profile_img = (AppCompatImageView) findViewById(R.id.profile_img);
        name_edit = (TextView) findViewById(R.id.name_edit_p);
       email_edit = (TextView) findViewById(R.id.email_edit_p);
        contact_edit = (TextView) findViewById(R.id.number_edit_p);
        location_edit = (TextView) findViewById(R.id.address_edit_p);
        gender_edit = (TextView) findViewById(R.id.gender_edit_p);
       name = (TextView) findViewById(R.id.email_phone);
        email = (TextView) findViewById(R.id.email_si);
        contact = (TextView) findViewById(R.id.contact_si);
        location = (TextView) findViewById(R.id.location_si);
       gender = (TextView) findViewById(R.id.gender_si);
    }
}
