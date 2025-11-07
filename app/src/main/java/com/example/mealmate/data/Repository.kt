package com.example.mealmate.data

import com.example.mealmate.data.remote.ApiService
import com.example.mealmate.data.remote.models.CategoryDto
import com.example.mealmate.data.remote.models.MealDto
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.Singles

class Repository(private val api: ApiService) {

    fun fetchHomeData(): Single<Pair<List<MealDto>, List<CategoryDto>>> {
        val mealsS = api.searchMealsByFirstLetter("a").map { it.meals ?: emptyList() }
        val catsS  = api.getCategories().map { it.categories ?: emptyList() }
        return Singles.zip(mealsS, catsS) { meals, cats -> meals to cats }
    }

    fun getMealDetail(id: String): Single<MealDto> =
        api.lookupMeal(id).map { resp ->
            resp.meals?.firstOrNull() ?: throw IllegalStateException("Meal not found")
        }
}