package com.example.vivianbabiryekulumba.poe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.vivianbabiryekulumba.poe.recyclerview.Poem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class CardContentActivity extends AppCompatActivity {

    private static final String TAG = "CardContentActivity";
    TextView poem_title;
    TextView poem_content;
    DatabaseReference reference;
    FloatingActionButton floatingActionButton;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_content);

        poem_title = findViewById(R.id.card_poem_title);
        poem_content = findViewById(R.id.card_poem_content);
        floatingActionButton = findViewById(R.id.floating_button);
        imageButton = findViewById(R.id.menu_image);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(getApplicationContext(), v);
                popupMenu.inflate(R.menu.menu_navigation);
                invalidateOptionsMenu();
                popupMenu.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.inspiration:
                                Intent intent = new Intent(getApplicationContext(), PoemActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.my_work:
                                Intent intent1 = new Intent(getApplicationContext(), MyWorkActivity.class);
                                startActivity(intent1);
                                return true;
                            default:
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardContentActivity.this, ThemePracticeActivity.class);
                startActivity(intent);
            }
        });

        reference = FirebaseDatabase.getInstance().getReference();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Poem poem = dataSnapshot.getValue(Poem.class);
                writeToPost();
                poem_title.setText(poem.getTitle());
                poem_content.setText(poem.getContent());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "onCancelled: ", databaseError.toException());
            }
        };
        reference.addValueEventListener(postListener);
        Log.d(TAG, "onCreate: success" + reference);
    }

    public void writeToPost() {
        String titleKey = reference.child("title").push().getKey();
        String contentKey = reference.child("content").push().getKey();
        Poem poem = new Poem();
        Map<String, Object> postValues = poem.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(titleKey, postValues);
        childUpdates.put(contentKey, postValues);

        reference.updateChildren(childUpdates);
    }

}
