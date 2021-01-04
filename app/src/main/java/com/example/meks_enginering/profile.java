package com.example.meks_enginering;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class profile extends AppCompatActivity {
ImageButton edit_image;
AppCompatImageView profile_img;
// for EditText Editting text
    TextView name,email,contact,location,gender;
    TextView name_edit,email_edit,contact_edit,location_edit,gender_edit;
    Button e_exit,e_edit;
    private static final int IMAGE_PICK_CODE=1000;
    private static final int PERMISSION_CODE=1001;
    // from sharedpreference stored in login
    get_user user=new get_user();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
           retrieveUserProfile();
        // displaying the user details to know that it's working(its working)
       // Toast.makeText(getApplicationContext(),user.toString(),Toast.LENGTH_SHORT).show();
        bindView();
        // Setting user details
        name.setText(user.getSurname()+" "+user.getFirstname()+" "+user.getOthername());
        email.setText(user.getEmail_address());
    try{
        contact.setText(user.getContact()+"");
    }catch(Exception e){
        e.printStackTrace();
    }

        location.setText(user.getLocation());
        gender.setText(user.getGender());

        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check runime permission
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        // permision not granted , request it.
                        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
                        // Show popup for runtime permission
                        requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else{
                        //permission already granted
                        pickImageFromGallery();
                    }
                }else{
                    // System os is less than marshmallow
                    pickImageFromGallery();
                }
            }
        });

    }

    private  void  pickImageFromGallery() {
        // intent to pick image
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    // handle result of runtime permission


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    // PERMISSION WAS GRANTED
                    pickImageFromGallery();
                }else{
                    // permission was denied
                    Toast.makeText(this,"Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // handle result of picked image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK && requestCode==IMAGE_PICK_CODE){
            // set image to image view
            //  mImageView.setImageURI(data.getData());
            inputImageCompressor.compressInputImage(data,profile.this,profile_img);
            edit_image.setVisibility(View.GONE);
        }
    }
    public boolean profileEdit(View anchor){
        PopupMenu  popup =new PopupMenu(getApplicationContext(),anchor);
        popup.getMenuInflater().inflate(R.menu.settings,popup.getMenu());
        popup.show();
       popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem menuItem) {
               switch (menuItem.getItemId()){
                   case R.id.chng_img_p:
                       Toast.makeText(getApplicationContext(),"edit image clicked",Toast.LENGTH_SHORT).show();
                       // Should make the image edit icon visible.
                       // The data should be made invisible after data has been loaded
                       edit_image.setVisibility(View.VISIBLE);
                       break;
                   case R.id.edit_profile:
                       Toast.makeText(getApplicationContext(),"edit profile clicked",Toast.LENGTH_SHORT).show();
                       // Should make all the edit profile text visbile
                       // Should be hidden when the edit has been completed

                       name_edit.setVisibility(View.VISIBLE);
                       email_edit.setVisibility(View.VISIBLE);
                       contact_edit.setVisibility(View.VISIBLE);
                       location_edit.setVisibility(View.VISIBLE);
                       gender_edit.setVisibility(View.VISIBLE);
                       // To set the onclick listener of the above views
                       // A dialog box will be used to update the views
                       // Individual api is needed for updating and editing the user
                       break;
               }
               return false;
           }
       });
        return true;
    }

    public void editImageDialog(View view){
        final View v=view;
        ViewGroup viewGroup=findViewById(R.id.content);
        final View pauseDialog= LayoutInflater.from(this).inflate(R.layout.profile_edit_dialog,viewGroup,false);
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setView(pauseDialog);
        final AlertDialog alertDialog=builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
        e_exit=pauseDialog.findViewById(R.id.e_exit);
        e_edit=pauseDialog.findViewById(R.id.e_edit);
        e_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            alertDialog.dismiss();
                v.setVisibility(View.GONE);
            }
        });
//08080128748
        e_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Should return a code if the edit is successful
                //also make the edit button view gone
                // use the values gottne and update the textView
                alertDialog.dismiss();
            }
        });
    }
    public void retrieveUserProfile(){
        SharedPreferences mPrefs = getSharedPreferences("up", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("UserProfile", "");
           user =  gson.fromJson(json, get_user.class);
    }

    public void bindView(){

        edit_image=findViewById(R.id.edit_image);
        profile_img=findViewById(R.id.profile_img);
        name_edit=findViewById(R.id.name_edit_p);
        email_edit=findViewById(R.id.email_edit_p);
        contact_edit=findViewById(R.id.number_edit_p);
        location_edit=findViewById(R.id.address_edit_p);
        gender_edit=findViewById(R.id.gender_edit_p);


        // for user profile
        name=findViewById(R.id.name_si);
        email=findViewById(R.id.email_si);
        contact=findViewById(R.id.contact_si);
        location=findViewById(R.id.location_si);
        gender=findViewById(R.id.gender_si);
    }

}
