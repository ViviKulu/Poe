package com.example.vivianbabiryekulumba.poe;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView splashImage;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImage = findViewById(R.id.splash_poe);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, PoemActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
