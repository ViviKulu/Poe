package com.example.vivianbabiryekulumba.poe;

import android.animation.Animator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vivianbabiryekulumba.poe.models.Poem;
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
    ImageView imageView;
    View fabLayout;
    TextView menu_tab, tab1, tab2, tab3;
    LinearLayout ll_base, ll1, ll2, ll3;
    boolean isMenuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_content);

        poem_title = findViewById(R.id.card_poem_title);
        poem_content = findViewById(R.id.card_poem_content);
        imageView = findViewById(R.id.raven);

        fabLayout = findViewById(R.id.menuBGLayout);
        ll_base = findViewById(R.id.menu_layout_base);
        ll1 = findViewById(R.id.tab_layout_1);
        ll2 = findViewById(R.id.tab_layout_2);
        ll3 = findViewById(R.id.tab_layout_3);
        menu_tab = findViewById(R.id.menu_tab);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);

        fabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeTabMenu();
            }
        });

        hideTabs(tab1, tab2, tab3);

        menu_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isMenuOpen) {
                    showTabMenu();
                    tab1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CardContentActivity.this, PoemActivity.class);
                            startActivity(intent);
                        }
                    });
                    tab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CardContentActivity.this, ThemePracticeActivity.class);
                            startActivity(intent);
                        }
                    });
                    tab3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(CardContentActivity.this, MyWorkActivity.class);
                            startActivity(intent);
                        }
                    });

                } else {
                    closeTabMenu();
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

    private void hideTabs(TextView tab1, TextView tab2, TextView tab3) {
        if (!isMenuOpen) {
            tab1.setVisibility(View.INVISIBLE);
            tab2.setVisibility(View.INVISIBLE);
            tab3.setVisibility(View.INVISIBLE);
        }else{
            showTabMenu();
        }
    }


    private void showTabMenu(){
        isMenuOpen = true;
        ll_base.setVisibility(View.VISIBLE);
        ll1.setVisibility(View.VISIBLE);
        ll2.setVisibility(View.VISIBLE);
        ll3.setVisibility(View.VISIBLE);
        fabLayout.setVisibility(View.VISIBLE);

        ll_base.animate().rotationBy(360);
        ll1.animate().translationY(-getResources().getDimension(R.dimen.standard_45));
        ll2.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        ll3.animate().translationY(-getResources().getDimension(R.dimen.standard_85));
    }

    private void closeTabMenu(){
        isMenuOpen =false;
        fabLayout.setVisibility(View.GONE);
        ll_base.animate().rotationBy(-360);
        ll1.animate().translationY(2);
        ll2.animate().translationY(2);
        ll3.animate().translationY(2).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                tab1.setVisibility(View.VISIBLE);
                tab2.setVisibility(View.VISIBLE);
                tab3.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isMenuOpen){
                    ll1.setVisibility(View.GONE);
                    ll2.setVisibility(View.GONE);
                    ll3.setVisibility(View.GONE);
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
        if(isMenuOpen){
            closeTabMenu();
        }else{
            super.onBackPressed();
        }
    }


}
