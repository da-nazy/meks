package com.example.meks_enginering;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meks_enginering.user.sign_up;

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
                MainActivity.this.startActivity(new Intent(MainActivity.this, sign_up.class));
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
