package com.example.themoviedb.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.adapters.MoviesAdapter
import com.example.themoviedb.databinding.FragmentMoviesListBinding
import com.example.themoviedb.model.Movie
import com.example.themoviedb.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MoviesListFragment : Fragment() {
    private val TAG = "MoviesListFragment"
    private var _binding: FragmentMoviesListBinding? = null
    private lateinit var moviesAdapter: MoviesAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        binding.btnSearch.setOnClickListener { searchForMovies() }
    }

    private fun searchForMovies() {
        val keywords = binding.etMovieTitle.text.toString()
        Log.d(TAG,"New search: $keywords")
        if (keywords.isEmpty()) return

        lifecycleScope.launch {
            binding.progressBar.isVisible = true
            val queryResponse = try {
                RetrofitInstance.movieListApi
                    .getMoviesList(keywords, RetrofitInstance.API_KEY)
            } catch (e: IOException) {
                binding.tvMoviesFound.text = getString(R.string.no_internet)
                binding.progressBar.isVisible = false
                return@launch
            } catch (e: HttpException) {
                binding.tvMoviesFound.text = getString(R.string.bad_response_from_server)
                binding.progressBar.isVisible = false
                return@launch
            }
            binding.progressBar.isVisible = false

            // Если ответ от сервера получен - записываем результат в moviesList и открываем фрагмент со списком фильмов
            if (queryResponse.isSuccessful && queryResponse.body() != null) {
                val newMoviesList = queryResponse.body()!!.results
                newMoviesList.sortedByDescending { it.popularity }

                val moviesFound = queryResponse.body()!!.totalResults
                Log.d(TAG, "Request success. Total results: $moviesFound")
                Log.d(TAG, newMoviesList.toString())
                if (moviesFound == 0) {
                    moviesAdapter.moviesList = listOf<Movie>()
                    moviesAdapter.notifyDataSetChanged()
                    binding.tvMoviesFound.text =
                        getString(R.string.no_matches_found_check_for_typos)
                } else {
                    binding.tvMoviesFound.text = getString(R.string.matches_found, moviesFound)
                    moviesAdapter.moviesList = newMoviesList
                    moviesAdapter.notifyDataSetChanged()


                }
            }
        }


    }


    private fun setupRecyclerView() {
        moviesAdapter = MoviesAdapter(listOf<Movie>())
        binding.recyclerView.apply {
            adapter = moviesAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }


}
