package com.example.vivianbabiryekulumba.poe;

import android.animation.Animator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

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
    View fabLayout;
    FloatingActionButton fab_base, fab1, fab2, fab3;
    LinearLayout ll_base, ll1, ll2, ll3;
    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        fabLayout = findViewById(R.id.fabBGLayout);
        ll_base = findViewById(R.id.fab_layout_base);
        ll1 = findViewById(R.id.fab_layout_1);
        ll2 = findViewById(R.id.fab_layout_2);
        ll3 = findViewById(R.id.fab_layout_3);
        fab_base = findViewById(R.id.floating_button_base);
        fab1 = findViewById(R.id.floating_button_1);
        fab2 = findViewById(R.id.floating_button_2);
        fab3 = findViewById(R.id.floating_button_3);

        getRetrofit();

        fab_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFABMenu();
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

    private void showFABMenu(){
        isFABOpen=true;
        ll1.setVisibility(View.VISIBLE);
        ll2.setVisibility(View.VISIBLE);
        ll3.setVisibility(View.VISIBLE);
        fabLayout.setVisibility(View.VISIBLE);

        ll_base.animate().rotationBy(180);
        ll1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        ll2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
        ll3.animate().translationY(-getResources().getDimension(R.dimen.standard_145));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabLayout.setVisibility(View.GONE);
        ll_base.animate().rotationBy(-180);
        ll1.animate().translationY(0);
        ll2.animate().translationY(0);
        ll3.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
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
        if(isFABOpen){
            closeFABMenu();
        }else{
            super.onBackPressed();
        }
    }
}
