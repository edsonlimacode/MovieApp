package com.edsonlima.flixapp.presenter.main.movie.details.tabs.trailer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edsonlima.flixapp.domain.model.Trailer
import com.edsonlima.flixapp.domain.usecase.movie.GetTrailersByMovieIdUseCase
import com.edsonlima.flixapp.utils.StateView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrailerViewModel @Inject constructor(
    private val getTrailersByMovieIdUseCase: GetTrailersByMovieIdUseCase
) : ViewModel() {

    private val _trailers = MutableLiveData<StateView<List<Trailer>>>()
    val trailers: LiveData<StateView<List<Trailer>>> = _trailers

    fun getTrailersByMovieId(movieId: Int) = viewModelScope.launch {

        try {

            _trailers.value = StateView.Loading()

            val trailers = getTrailersByMovieIdUseCase(movieId)

            _trailers.value = StateView.Success(trailers)


        } catch (ex: Exception) {
            _trailers.value = StateView.Error(ex.message.toString())
        }
    }

}