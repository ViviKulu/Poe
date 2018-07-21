package com.example.vivianbabiryekulumba.poe;

import android.animation.Animator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vivianbabiryekulumba.poe.network.PoeNetworkService;
import com.example.vivianbabiryekulumba.poe.recyclerview.Poem;
import com.example.vivianbabiryekulumba.poe.recyclerview.PoemAdapter;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PoemActivity extends AppCompatActivity {

    private static final String TAG = "PoemActivity";
    Retrofit retrofit;
    private static List<Poem> poemList;
    RecyclerView recyclerView;
    View menuTabLayout;
    TextView menu_tab, tab1, tab2, tab3;
    LinearLayout ll_base, ll1, ll2, ll3;
    boolean isMenuOpen = false;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        menuTabLayout = findViewById(R.id.menuBGLayout);
        ll_base = findViewById(R.id.menu_layout_base);
        ll1 = findViewById(R.id.tab_layout_1);
        ll2 = findViewById(R.id.tab_layout_2);
        ll3 = findViewById(R.id.tab_layout_3);
        menu_tab = findViewById(R.id.menu_tab);
        tab1 = findViewById(R.id.tab1);
        tab2 = findViewById(R.id.tab2);
        tab3 = findViewById(R.id.tab3);
        imageButton = findViewById(R.id.raven);

        getRetrofit();

        menuTabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeTabMenu();
            }
        });

        hideTabs(tab1, tab2, tab3);

        menu_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isMenuOpen){
                    showTabMenu();
                    tab1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PoemActivity.this, ThemePracticeActivity.class);
                            startActivity(intent);
                        }
                    });
                    tab2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(PoemActivity.this, MyWorkActivity.class);
                            startActivity(intent);
                        }
                    });
                    tab3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Make retrofit to google translate api and translate current content from poet
                            //then with an intent take translated content to Card content activity.
                        }
                    });
                }else{
                    closeTabMenu();
                }
            }
        });

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
                    Log.d(TAG, "onResponse: " + poemList);
                    PoemAdapter poetAdapter = new PoemAdapter(poemList);
                    poetAdapter.notifyDataSetChanged();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setAdapter(poetAdapter);
                    recyclerView.setLayoutManager(linearLayoutManager);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Poem>> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void onClick(View v){
        Intent intent = new Intent(PoemActivity.this, CardContentActivity.class);
        startActivity(intent);
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
        ll_base.setVisibility(View.VISIBLE);
        menuTabLayout.setVisibility(View.VISIBLE);

        ll_base.animate().rotationBy(360);
        ll1.animate().translationY(-getResources().getDimension(R.dimen.standard_45));
        ll2.animate().translationY(-getResources().getDimension(R.dimen.standard_65));
        ll3.animate().translationY(-getResources().getDimension(R.dimen.standard_85));
    }

    private void closeTabMenu(){
        isMenuOpen = false;
        menuTabLayout.setVisibility(View.GONE);
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
