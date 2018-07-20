package com.example.vivianbabiryekulumba.poe;

import android.animation.Animator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.vivianbabiryekulumba.poe.network.PoeNetworkService;
import com.example.vivianbabiryekulumba.poe.recyclerview.Poem;
import com.example.vivianbabiryekulumba.poe.recyclerview.ThemeAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemePracticeActivity extends AppCompatActivity {

    private static final String TAG = "ThemePracticeActivity";
    TextView theme_tv;
    EditText theme_practice_et;
    Retrofit retrofit;
    private static List<Poem> poemList;
    RecyclerView recyclerView;
    ThemeAdapter themeAdapter;
    View fabLayout;
    FloatingActionButton fab_base, fab1, fab2, fab3, fab4;
    LinearLayout ll_base, ll1, ll2, ll3, ll4;
    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_practice);
        recyclerView = findViewById(R.id.theme_recycler_practice);

        fabLayout = findViewById(R.id.fabBGLayout);
        ll_base = findViewById(R.id.fab_layout_base);
        ll1 = findViewById(R.id.fab_layout_home);
        ll2 = findViewById(R.id.fab_layout_my_work);
        ll3 = findViewById(R.id.fab_layout_translate);
        ll4 = findViewById(R.id.fab_layout_save_draft);
        fab_base = findViewById(R.id.floating_button_base);
        fab1 = findViewById(R.id.floating_button_home);
        fab2 = findViewById(R.id.floating_button_my_work);
        fab3 = findViewById(R.id.floating_button_translate);
        fab4 = findViewById(R.id.floating_button_save_draft);

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
                            Intent intent = new Intent(ThemePracticeActivity.this, PoemActivity.class);
                            startActivity(intent);
                        }
                    });
                    fab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ThemePracticeActivity.this, MyWorkActivity.class);
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
                            //Write to the fb-db. index : my_work. Should save into database.
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("my_work");
                            reference.setValue(theme_practice_et.getText().toString());
                            Log.d(TAG, "onClick: " + reference);
                            Toast.makeText(getApplicationContext(), "Saved draft to My Work!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    closeFABMenu();
                }
            }
        });

        getRetrofit();
        Toast.makeText(getApplicationContext(), "Complete the thought", Toast.LENGTH_SHORT).show();
    }

    public void getRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.poemist.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final PoeNetworkService networkService = retrofit.create(PoeNetworkService.class);

        final Call<List<Poem>> poemCall = networkService.getPoemData();
        poemCall.enqueue(new Callback<List<Poem>>() {
            @Override
            public void onResponse(@NonNull Call<List<Poem>> call, @NonNull Response<List<Poem>> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: success");
                    poemList = response.body();
                    themeAdapter = new ThemeAdapter(poemList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setAdapter(themeAdapter);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Poem>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void showFABMenu(){
        isFABOpen= true;
        ll_base.setVisibility(View.VISIBLE);
        ll1.setVisibility(View.VISIBLE);
        ll2.setVisibility(View.VISIBLE);
        ll3.setVisibility(View.VISIBLE);
        ll4.setVisibility(View.VISIBLE);
        ll_base.setVisibility(View.VISIBLE);
        fabLayout.setVisibility(View.VISIBLE);

        ll_base.animate().rotationBy(360);
        ll1.animate().translationY(-getResources().getDimension(R.dimen.standard_75));
        ll2.animate().translationY(-getResources().getDimension(R.dimen.standard_150));
        ll3.animate().translationY(-getResources().getDimension(R.dimen.standard_225));
        ll4.animate().translationY(-getResources().getDimension(R.dimen.standard_300));
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
