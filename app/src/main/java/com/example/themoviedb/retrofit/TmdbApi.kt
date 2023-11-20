package com.example.themoviedb.retrofit

import com.example.themoviedb.data.QueryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET ("3/search/movie")
    suspend fun getMoviesList(@Query("query") query: String,
                              @Query("api_key") apiKey: String ): Response<QueryResponse>


    @GET ("t/p/original")
    suspend fun getMoviePoster()
}


