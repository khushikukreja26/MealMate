package com.example.mealmate.ui.home

import androidx.lifecycle.ViewModel
import com.example.mealmate.data.Repository
import com.example.mealmate.data.remote.models.CategoryDto
import com.example.mealmate.data.remote.models.MealDto
import com.example.mealmate.util.RxSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(
        val meals: List<MealDto>,
        val categories: List<CategoryDto>
    ) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel(
    private val repo: Repository,
    private val sched: RxSchedulers
) : ViewModel() {

    private val _state = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val state: StateFlow<HomeUiState> = _state

    private val bag = CompositeDisposable()

    fun refresh() {
        _state.value = HomeUiState.Loading
        repo.fetchHomeData()
            .subscribeOn(sched.io)
            .observeOn(sched.main)
            .subscribe({ (meals, cats) ->
                _state.value = HomeUiState.Success(meals, cats)
            }, { e ->
                _state.value = HomeUiState.Error(e.localizedMessage ?: "Something went wrong")
            })
            .addTo(bag)
    }

    override fun onCleared() {
        bag.clear()
        super.onCleared()
    }
}