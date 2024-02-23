package com.example.themoviedb.retrofit

import com.example.themoviedb.model.QueryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET ("3/search/movie")
    suspend fun getQueryResponse(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = RetrofitInstance.API_KEY
    ): Response<QueryResponse>

}


