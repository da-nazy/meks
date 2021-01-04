package com.example.meks_enginering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    RotateAnimation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splash();
    }
    public void splash(){

// Start animating the image
        ImageView splash = (ImageView) findViewById(R.id.mekslogo);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.clockwise);
        animation1.setDuration(5000);
        splash.startAnimation(animation1);
        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                  //  Toast.makeText(getApplicationContext(),"Animation ended",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this, sign_up.class);
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}