package com.example.vivianbabiryekulumba.poe;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vivianbabiryekulumba.poe.network.PoeNetworkService;
import com.example.vivianbabiryekulumba.poe.recyclerview.Poem;
import com.example.vivianbabiryekulumba.poe.recyclerview.PoetAdapter;
import com.example.vivianbabiryekulumba.poe.recyclerview.ThemeAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemePracticeActivity extends AppCompatActivity {

    private static final String TAG = "ThemePracticeActivity";
    TextView complete_theme_tv;
    TextView theme_tv;
    EditText theme_practice_et;
    Retrofit retrofit;
    private static List<Poem> poemList;
    RecyclerView recyclerView;
    ThemeAdapter themeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_practice);
        recyclerView = findViewById(R.id.theme_recycler_practice);
        getRetrofit();

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
}
