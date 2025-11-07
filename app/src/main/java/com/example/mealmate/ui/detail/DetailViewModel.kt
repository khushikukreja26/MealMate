package com.example.mealmate.ui.detail

import androidx.lifecycle.ViewModel
import com.example.mealmate.data.Repository
import com.example.mealmate.data.remote.models.CategoryDto
import com.example.mealmate.data.remote.models.MealDto
import com.example.mealmate.util.RxSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class DetailUiState {
    data object Loading : DetailUiState()
    data class Meal(val data: MealDto) : DetailUiState()
    data class Category(val data: CategoryDto) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

class DetailViewModel(
    private val repo: Repository,
    private val sched: RxSchedulers
) : ViewModel() {

    private val _state = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val state: StateFlow<DetailUiState> = _state

    private val bag = CompositeDisposable()

    fun loadMeal(id: String) {
        _state.value = DetailUiState.Loading
        repo.getMealDetail(id)
            .subscribeOn(sched.io)
            .observeOn(sched.main)
            .subscribe({ m -> _state.value = DetailUiState.Meal(m) },
                { e -> _state.value = DetailUiState.Error(e.localizedMessage ?: "Error") })
            .addTo(bag)
    }

    fun showCategory(cat: CategoryDto) {
        _state.value = DetailUiState.Category(cat)
    }

    override fun onCleared() {
        bag.clear()
        super.onCleared()
    }
}