package com.example.themoviedb

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.themoviedb.data.Movie
import com.example.themoviedb.databinding.ActivityMainBinding
import com.example.themoviedb.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

const val TAG = "The Movie DB"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var moviesListFragment = MoviesListFragment()
    private lateinit var moviesList: List<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSearch.setOnClickListener {
            val keywords = binding.etMovieTitle.text.toString()
            searchForMovie(keywords)
        }

    }


    private fun searchForMovie(keywords: String) {
        lifecycleScope.launch {
            binding.progressBar.isVisible = true
            val queryResponse = try {
                RetrofitInstance.movieListApi
                    .getMoviesList(keywords, RetrofitInstance.API_KEY)
            } catch (e: IOException) {
                binding.tvMoviesFound.text = "No internet"
                binding.progressBar.isVisible = false
                return@launch
            } catch (e: HttpException) {
                binding.tvMoviesFound.text = "Bad response from server"
                binding.progressBar.isVisible = false
                return@launch
            }
            binding.progressBar.isVisible = false

            // Если ответ от сервера получен - записываем результат в moviesList и открываем фрагмент со списком фильмов
            if (queryResponse.isSuccessful && queryResponse.body() != null) {
                moviesList = queryResponse.body()!!.results
                val moviesFound = moviesList.size
                Log.d(TAG, "Total matches: $moviesFound")
                if (moviesFound == 0) {
                    binding.tvMoviesFound.text = "No matches found. Check for typos."
                } else {
                    binding.tvMoviesFound.text = "Matches found: $moviesFound"
                    showResultsFragment()
                }


            }
        }
    }


    // Display the movie list in a separate fragment
    // Called from searchForMovie function if results retrieved from server successfully
    private fun showResultsFragment() {
        moviesListFragment.setMoviesList(moviesList)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, moviesListFragment)
            //addToBackStack(null)
            commit()
        }

    }

}