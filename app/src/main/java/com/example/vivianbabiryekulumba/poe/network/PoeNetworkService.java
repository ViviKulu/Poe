package com.example.vivianbabiryekulumba.poe.network;

import com.example.vivianbabiryekulumba.poe.models.Poem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PoeNetworkService {

    @GET("api/v1/randompoems")
    Call<List<Poem>> getPoemData();
}
