package com.example.themoviedb.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.themoviedb.R
import com.example.themoviedb.databinding.FragmentMoviesListBinding
import com.example.themoviedb.model.Movie

class MoviesListFragment : Fragment() {
    private val TAG = "MoviesListFragment"
    private var _binding: FragmentMoviesListBinding? = null
    private lateinit var moviesAdapter: MoviesListAdapter
    private val binding get() = _binding!!
    private lateinit var viewModel: MoviesViewModel

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
        val factory = MoviesViewModelFactory(requireContext())
        viewModel = ViewModelProvider(this, factory)[MoviesViewModel::class.java]
        binding.btnSearch.setOnClickListener { searchForMovies() }

        //подписываемся на статус запроса чтобы отображать/прятать ProgressBar
        viewModel.apiRequestStatus.observe(viewLifecycleOwner) { newStatus ->
            when (newStatus) {
                ApiRequestStatus.DONE_SUCCESS -> {
                    binding.progressBar.isVisible = false
                }

                ApiRequestStatus.LOADING -> {
                    binding.progressBar.isVisible = true
                }

                ApiRequestStatus.DONE_ERROR -> {
                    binding.progressBar.isVisible = false
                    binding.tvMatchesFound.text =
                        getString(R.string.network_error_check_your_connection)
                }
            }
        }

        //подписываемся на количество фильмов
        viewModel.matchesFound.observe(viewLifecycleOwner) { newMatchesFound ->
            binding.tvMatchesFound.text = newMatchesFound
        }

        //подписываемся на список фильмишек
        viewModel.moviesList.observe(viewLifecycleOwner) { newMoviesList ->
            moviesAdapter.apply {
                moviesList = newMoviesList
                notifyDataSetChanged()
            }

        }


    }

    private fun searchForMovies() {
        val keywords = binding.etMovieTitle.text.toString()
        if (keywords.isEmpty()) return
        Log.d(TAG, "New search: $keywords")
        viewModel.getMoviesList(keywords)

        /*
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

             lifecycleScope.launch {


                 // Если ответ от сервера получен - записываем результат в moviesList и открываем фрагмент со списком фильмов
                 if (queryResponse.isSuccessful && queryResponse.body() != null) {
                     val newMoviesList = queryResponse.body()!!.results
                     newMoviesList.sortedByDescending { it.popularity }

                     val moviesFound = queryResponse.body()!!.totalResults
                     Log.d(TAG, "Request success. Total results: $moviesFound")

                 }
             }*/


    }


    private fun setupRecyclerView() {
        moviesAdapter = MoviesListAdapter(listOf<Movie>())
        binding.recyclerView.apply {
            adapter = moviesAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }


}
