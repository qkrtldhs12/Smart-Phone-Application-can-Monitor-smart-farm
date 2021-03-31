package com.example.morta.mytestapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api_Client {
    //@GET 어노테이션으로 GET Request를 사용
    //@GET("/users/{user}/repos")
    @GET("df/servers/all/characters")
    //@Path()어노테이션으로 url에 들어갈 {user}를 동적으로 만들 수 있다.
    fun query(
            @Query("characterName") characterName: String,
            @Query("apikey") apikey: String
    ): Call<NeopleRepo>
    //Call<>object를 사용해서 반환값을 비동기식으로 구현
}