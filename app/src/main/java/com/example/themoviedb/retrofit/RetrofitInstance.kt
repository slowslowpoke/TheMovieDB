package com.example.themoviedb.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    const val API_KEY="05892fc59640cfc16f1c89a66bc0e12c"
    const val IMAGE_URL="https://image.tmdb.org/t/p/original"
    private const val BASE_URL="https://api.themoviedb.org"

    val movieListApi: TmdbApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TmdbApi::class.java)

    }





}