package com.example.themoviedb.data

import com.google.gson.annotations.SerializedName

data class QueryResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)