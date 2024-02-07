package com.example.themoviedb.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.themoviedb.R
import com.example.themoviedb.databinding.MovieListItemBinding
import com.example.themoviedb.model.Movie
import com.example.themoviedb.retrofit.RetrofitInstance

class MoviesAdapter(var moviesList: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private val TAG = "MoviesAdapter"

    inner class MovieViewHolder(val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = MovieListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(view)
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.apply {
            val movie = moviesList[position]
            tvMovieTitle.text = movie.original_title
            tvReleaseDate.text = movie.release_date
            //load poster using Coil lib
            Log.d(TAG, "started loading poster")

            val posterUrl = RetrofitInstance.IMAGE_URL + movie.poster_path
            ivPoster.load(posterUrl) {
                placeholder(R.drawable.poster_placeholder)
                transformations(RoundedCornersTransformation())
            }
        }
    }
}