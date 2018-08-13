package com.example.vivianbabiryekulumba.poe;

import android.animation.Animator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.vivianbabiryekulumba.poe.models.Exercise;
import com.example.vivianbabiryekulumba.poe.models.Theme;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ThemePracticeActivity extends AppCompatActivity {

    private static final String TAG = "ThemePracticeActivity";
    TextView theme_tv;
    EditText theme_practice_et;
    private DatabaseReference databaseReference;
    Exercise exercise;
    View menuBGLayout;
    TextView menu_tab, tab1, tab2, tab3;
    LinearLayout ll_base, ll1, ll2, ll3;
    boolean isMenuOpen = false;

    //Use bundle or intent extra to carry theme title to theme practice activity.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_practice);
        theme_tv = findViewById(R.id.theme_tv);
        theme_practice_et = findViewById(R.id.theme_practice_et);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        menuBGLayout = findViewById(R.id.menuBGLayout);
        ll_base = findViewById(R.id.menu_layout_base);
        ll1 = findViewById(R.id.tab_layout_1);
        ll2 = findViewById(R.id.tab_layout_2);
        ll3 = findViewById(R.id.tab_layout_3);
        menu_tab = findViewById(R.id.menu_tab);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);


        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Theme theme = dataSnapshot.getValue(Theme.class);
                theme_tv.setText(theme.getTheme());
                Log.d(TAG, "onDataChange: " + theme);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(postListener);

        menuBGLayout.setOnClickListener(new View.OnClickListener() {
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
                            Intent intent = new Intent(ThemePracticeActivity.this, PoemActivity.class);
                            startActivity(intent);
                        }
                    });
                    tab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            writeNewExercise(theme_practice_et.getText().toString());
                            Log.d(TAG, "onClick: " + exercise);
                            Toast.makeText(getApplicationContext(), "Saved draft to My Work!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    tab3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ThemePracticeActivity.this, MyWorkActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    closeTabMenu();
                }
            }
        });

        Toast.makeText(getApplicationContext(), "Complete the thought", Toast.LENGTH_SHORT).show();
    }

    private void hideTabs(TextView tab1, TextView tab2, TextView tab3) {
        if (!isMenuOpen) {
            tab1.setVisibility(View.INVISIBLE);
            tab2.setVisibility(View.INVISIBLE);
            tab3.setVisibility(View.INVISIBLE);
        } else {
            showTabMenu();
        }
    }

    private void showTabMenu() {
        isMenuOpen = true;
        ll_base.setVisibility(View.VISIBLE);
        ll1.setVisibility(View.VISIBLE);
        ll2.setVisibility(View.VISIBLE);
        ll3.setVisibility(View.VISIBLE);
        ll_base.setVisibility(View.VISIBLE);
        menuBGLayout.setVisibility(View.VISIBLE);

        ll_base.animate().rotationBy(360);
        ll1.animate().translationY(-getResources().getDimension(R.dimen.standard_45));
        ll2.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        ll3.animate().translationY(-getResources().getDimension(R.dimen.standard_85));
    }

    private void closeTabMenu() {
        isMenuOpen = false;
        menuBGLayout.setVisibility(View.GONE);
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
                if (!isMenuOpen) {
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
        if (isMenuOpen) {
            closeTabMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void writeNewExercise(String exercise_content) {
        Exercise exercise = new Exercise(exercise_content);
        databaseReference.child("exercise").child(exercise_content).setValue(exercise);
        Log.d(TAG, "writeNewExercise: " + exercise);
    }
}
