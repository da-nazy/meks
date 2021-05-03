package com.example.meks_enginering;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meks_enginering.user.sign_in;
import com.example.meks_enginering.user.sign_up;
import com.example.meks_enginering.user.verification;

public class MainActivity extends AppCompatActivity {
    RotateAnimation anim;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        splash();
    }

    public void splash() {
        ImageView splash = (ImageView) findViewById(R.id.mekslogo);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise);
        animation1.setDuration(5000);
        splash.startAnimation(animation1);
        animation1.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
               // MainActivity.this.startActivity(new Intent(MainActivity.this, sign_up.class));
                 checkPendingVerification();
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    public void checkPendingVerification(){
        // check the pending verification if exit else remove the the verification question
        // and  move to the verification page
        // if does not exit leave user might need verification
        //SharedPreferences.Editor localEditor =getApplicationContext().getSharedPreferences("verification",MODE_PRIVATE).edit();
        SharedPreferences prefs=this.getSharedPreferences("verification",MODE_PRIVATE);
        int verification=prefs.getInt("pendingVerification",0);
        String email=prefs.getString("EMAIL","NOMAIL");
        String userid=prefs.getString("userid","NOID");

        // for time exceeding 24 hrs
        boolean hasExpire=false;
        // for current time comparism
        long currentTime=System.currentTimeMillis();
        long time=prefs.getLong("time",0000);
        if(time>=currentTime+24*60*60*1000){
            hasExpire=true;
            // meaning the current time is more than 24 hours
        }else{
            hasExpire=false;
        }

        if(verification==0){
            // verification hasn't been done
            // move to signup/login
            startActivity(new Intent(MainActivity.this, sign_up.class));
        }
        if(verification ==1&& hasExpire){
            // verification has been done not completed and has expired
            // take to signup page to re-register;
            startActivity(new Intent(MainActivity.this, sign_up.class));
        }
        if((verification==1)&&(hasExpire==false)){

            startActivity(new Intent(MainActivity.this, verification.class));
        }
        if(verification==2){
            // verification has been completed take to login section
            startActivity(new Intent(MainActivity.this, sign_in.class));
        }

    }
}
