package com.example.themoviedb.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Movie(

    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    val vote_count: Int
): Serializable