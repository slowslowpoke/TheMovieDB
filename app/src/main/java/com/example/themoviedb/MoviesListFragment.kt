package com.example.themoviedb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviedb.data.Movie
import com.example.themoviedb.databinding.FragmentMoviesListBinding

class MoviesListFragment : Fragment() {
    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var sortedMoviesList: List<Movie>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleAdapter()
    }

    private fun setupRecycleAdapter() {
        binding.recyclerView.apply {
            adapter = MovieRVAdapter(sortedMoviesList)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    fun setMoviesList(moviesList: List<Movie>) {
        sortedMoviesList = moviesList.sortedByDescending { it.popularity }

    }

}
