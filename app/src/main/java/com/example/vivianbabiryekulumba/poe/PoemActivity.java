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
import android.widget.ImageButton;
import com.example.vivianbabiryekulumba.poe.network.PoeNetworkService;
import com.example.vivianbabiryekulumba.poe.models.Poem;
import com.example.vivianbabiryekulumba.poe.controllers.PoemAdapter;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PoemActivity extends AppCompatActivity {

    private static final String TAG = "PoemActivity";
    Retrofit networking;
    private static List<Poem> poemList;
    RecyclerView recyclerView;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        imageButton = findViewById(R.id.raven);

        getRetrofit();

    }

    public void getRetrofit() {
        networking = new Retrofit.Builder()
                .baseUrl("https://www.poemist.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final PoeNetworkService networkService = networking.create(PoeNetworkService.class);

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
                    recyclerView.setAdapter(poetAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
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

    public void textClick(View v){
        //Make get request to the search api.
    }

}
