package com.example.pubapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import static java.lang.Thread.sleep;

public class SplashActivity extends Activity {
    ImageView splahImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        splahImage = (ImageView) findViewById(R.id.img);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        splahImage.startAnimation(animation);

        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        myThread.start();

    }


}
