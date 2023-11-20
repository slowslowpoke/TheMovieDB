package com.example.themoviedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.themoviedb.databinding.ActivityMainBinding
import com.example.themoviedb.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

const val TAG = "The Movie DB"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
                Log.d(TAG, "Check your internet connection")
                binding.progressBar.isVisible = false
                return@launch
            } catch (e: HttpException) {
                Log.d(TAG, "Server response unsuccessful")
                binding.progressBar.isVisible = false
                return@launch
            }

            binding.progressBar.isVisible = false
            if (queryResponse.isSuccessful && queryResponse.body() != null) {
                val moviesFound = queryResponse.body()!!.totalResults
                Log.d(TAG, moviesFound.toString())
                if (moviesFound == 0) {
                    binding.tvSearchMovie.text = "Movie not found"
                    return@launch
                }
                binding.tvSearchMovie.text = moviesFound.toString()

                //тут выделить потом в отдельную функцию для загрузки картинки
                val moviesList = queryResponse.body()!!.results.sortedByDescending { it.vote_average }
                binding.tvMovieDesc.text = moviesList[0].overview
                val imageUrl = RetrofitInstance.IMAGE_URL + moviesList[0].poster_path
                binding.ivPoster.load(imageUrl)


            }

        }
    }




}