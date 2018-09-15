package com.example.vivianbabiryekulumba.poe.network;

import com.example.vivianbabiryekulumba.poe.models.Poem;
import com.example.vivianbabiryekulumba.poe.models.PoetSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PoeNetworkService {

    //API Key for custom search api : AIzaSyADTe6ouTgVo0R0rO3OEi4OCFi8KBC_on4

    @GET("api/v1/randompoems")
    Call<List<Poem>> getPoemData();

    @GET("https://www.googleapis.com/customsearch/v1?key=AIzaSyADTe6ouTgVo0R0rO3OEi4OCFi8KBC_on4017060971245161540337:gqen4ubbaoiq=poet")
    Call<List<PoetSearch>> getPoetData();

}
