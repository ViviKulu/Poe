package com.example.vivianbabiryekulumba.poe;

import android.animation.Animator;
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
import android.widget.LinearLayout;
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
    ImageButton imageButton;
    View fabLayout;
    FloatingActionButton fab_base, fab1, fab2, fab3, fab4;
    LinearLayout ll_base, ll1, ll2, ll3, ll4;
    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_content);

        poem_title = findViewById(R.id.card_poem_title);
        poem_content = findViewById(R.id.card_poem_content);
        imageButton = findViewById(R.id.menu_image);

        fabLayout = findViewById(R.id.fabBGLayout);
        ll_base = findViewById(R.id.fab_layout_base);
        ll1 = findViewById(R.id.fab_layout_home);
        ll2 = findViewById(R.id.fab_layout_explore);
        ll3 = findViewById(R.id.fab_layout_my_work);
        ll4 = findViewById(R.id.fab_layout_translate);
        fab_base = findViewById(R.id.floating_button_base);
        fab1 = findViewById(R.id.floating_button_home);
        fab2 = findViewById(R.id.floating_button_explore);
        fab3 = findViewById(R.id.floating_button_my_work);
        fab4 = findViewById(R.id.floating_button_translate);

        fabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFABMenu();
            }
        });

        fab_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                    fab1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CardContentActivity.this, ThemePracticeActivity.class);
                            startActivity(intent);
                        }
                    });
                    fab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CardContentActivity.this, MyWorkActivity.class);
                            startActivity(intent);
                        }
                    });
                    fab3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Make retrofit to google translate api and translate current content from poet
                            //then with an intent take translated content to Card content activity.
                        }
                    });
                    fab4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CardContentActivity.this, PoemActivity.class);
                            startActivity(intent);
                        }
                    });
                }else{
                    closeFABMenu();
                }
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


    private void showFABMenu(){
        isFABOpen= true;
        ll_base.setVisibility(View.VISIBLE);
        ll1.setVisibility(View.VISIBLE);
        ll2.setVisibility(View.VISIBLE);
        ll3.setVisibility(View.VISIBLE);
        ll4.setVisibility(View.VISIBLE);
        fabLayout.setVisibility(View.VISIBLE);

        ll_base.animate().rotationBy(360);
        ll1.animate().translationY(-getResources().getDimension(R.dimen.standard_75));
        ll2.animate().translationY(-getResources().getDimension(R.dimen.standard_150));
        ll3.animate().translationY(-getResources().getDimension(R.dimen.standard_225));
        ll3.animate().translationY(-getResources().getDimension(R.dimen.standard_300));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabLayout.setVisibility(View.GONE);
        ll_base.animate().rotationBy(-360);
        ll1.animate().translationY(2);
        ll2.animate().translationY(2);
        ll3.animate().translationY(2);
        ll4.animate().translationY(2).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    ll1.setVisibility(View.GONE);
                    ll2.setVisibility(View.GONE);
                    ll3.setVisibility(View.GONE);
                    ll4.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isFABOpen){
            closeFABMenu();
        }else{
            super.onBackPressed();
        }
    }


}
