package com.example.themoviedb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.themoviedb.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private  val TAG = "The Movie DB"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}