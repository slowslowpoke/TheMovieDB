package com.example.themoviedb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.themoviedb.R
import com.example.themoviedb.databinding.FragmentMovieDetailsBinding
import com.example.themoviedb.retrofit.RetrofitInstance

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {
    private val TAG = "MovieDB - MovieInfoFragment"
    private var _binding: FragmentMovieDetailsBinding? = null
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movie = args.movie
        binding.apply {
            val imageFilePath = movie.backdropPath ?: movie.posterPath
            val imageFullUrl = RetrofitInstance.IMAGE_BASE_URL + imageFilePath
            ivBackdrop.load(imageFullUrl) {
                placeholder(R.drawable.loading_animation)
                transformations(RoundedCornersTransformation(40.0f))
            }
            tvMovieTitle.text = movie.originalTitle
            tvMovieOverview.text = movie.overview
            tvReleaseDate.text = resources.getString(R.string.release_date, movie.releaseDate)
            tvVoteAverage.text = resources.getString(R.string.vote_average, movie.voteAverage)


        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}