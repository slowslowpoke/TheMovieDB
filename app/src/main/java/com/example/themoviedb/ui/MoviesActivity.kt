package com.example.themoviedb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.themoviedb.R
import com.example.themoviedb.databinding.ActivityMoviesBinding


class MoviesActivity : AppCompatActivity() {
    private  val TAG = "The Movie DB"
    private lateinit var binding: ActivityMoviesBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.moviesNavHostFragment)
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}