package com.example.themoviedb.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.themoviedb.R
import com.example.themoviedb.databinding.MovieListItemBinding
import com.example.themoviedb.model.Movie
import com.example.themoviedb.retrofit.RetrofitInstance

class MoviesListAdapter(var moviesList: List<Movie>) :
    RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder>() {

    private val TAG = "MoviesAdapter"

    inner class MovieViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                tvMovieTitle.text = movie.originalTitle
                tvReleaseDate.text = movie.releaseDate
                //load poster using Coil lib and apply rounded corner transformation

                val posterUrl = RetrofitInstance.IMAGE_BASE_URL + movie.posterPath
                ivPoster.load(posterUrl) {
                    placeholder(R.drawable.loading_animation)
                    transformations(RoundedCornersTransformation(20.0f))
                }
                clMovieListRow.setOnClickListener {
                    val action = MoviesListFragmentDirections.actionMoviesListFragmentToMovieInfoFragment(movie)
                    root.findNavController().navigate(action)

                }
            }

        }

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
        val movie = moviesList[position]
        holder.bind(movie)
    }
}