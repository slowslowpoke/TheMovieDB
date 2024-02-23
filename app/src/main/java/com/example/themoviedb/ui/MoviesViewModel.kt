package com.example.themoviedb.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.R
import com.example.themoviedb.model.Movie
import com.example.themoviedb.retrofit.RetrofitInstance
import kotlinx.coroutines.launch

enum class ApiRequestStatus {
    LOADING,
    DONE_SUCCESS,
    DONE_ERROR
    //может еще какие-то варианты пригодятся?
}

class MoviesViewModel(private val context: Context) : ViewModel() {
    private var _matchesFound = MutableLiveData("")
    val matchesFound: LiveData<String>
        get() = _matchesFound

    private var _moviesList: MutableLiveData<List<Movie>> = MutableLiveData()
    val moviesList: LiveData<List<Movie>>
        get() = _moviesList

    private var _apiRequestStatus = MutableLiveData<ApiRequestStatus>(ApiRequestStatus.DONE_SUCCESS)
    val apiRequestStatus: LiveData<ApiRequestStatus>
        get() = _apiRequestStatus

    fun getMoviesList(keywords: String) = viewModelScope.launch {
        _apiRequestStatus.postValue(ApiRequestStatus.LOADING)
        val queryResponse = RetrofitInstance.movieListApi.getQueryResponse(keywords)
        if (!queryResponse.isSuccessful) {
            _apiRequestStatus.postValue(ApiRequestStatus.DONE_ERROR)
            return@launch
        }

        _apiRequestStatus.postValue(ApiRequestStatus.DONE_SUCCESS)
        val totalResults = queryResponse.body()?.totalResults
        if (totalResults == 0) {
            _matchesFound.postValue(context.getString(R.string.no_matches_found_check_for_typos))
            _apiRequestStatus.postValue(ApiRequestStatus.DONE_SUCCESS)
            _moviesList.postValue(emptyList())
        } else {
            _matchesFound.postValue(
                context.getString(
                    R.string.matches_found,
                    totalResults.toString()
                )
            )
            queryResponse.body()?.results.let { _moviesList.postValue(it) }
        }

        //добавить изменение статуса запроса!
    }
}

