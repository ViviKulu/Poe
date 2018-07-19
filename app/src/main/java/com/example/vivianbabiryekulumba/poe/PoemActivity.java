package com.example.vivianbabiryekulumba.poe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.example.vivianbabiryekulumba.poe.network.PoeNetworkService;
import com.example.vivianbabiryekulumba.poe.recyclerview.Poem;
import com.example.vivianbabiryekulumba.poe.recyclerview.PoetAdapter;
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
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        floatingActionButton = findViewById(R.id.floating_button);
        getRetrofit();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PoemActivity.this, ThemePracticeActivity.class);
                startActivity(intent);
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
                    PoetAdapter poetAdapter = new PoetAdapter(poemList);
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
}
