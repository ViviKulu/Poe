package com.example.vivianbabiryekulumba.poe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CardContentActivity extends AppCompatActivity {

    private static final String TAG = "CardContentActivity";
    TextView poem_title;
    TextView poem_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_content);
        poem_title = findViewById(R.id.card_poem_title);
        poem_content = findViewById(R.id.card_poem_content);



    }
}
