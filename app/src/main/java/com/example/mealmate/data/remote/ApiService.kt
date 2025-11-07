package com.example.mealmate.data.remote

import com.example.mealmate.data.remote.models.CategoriesResponse
import com.example.mealmate.data.remote.models.MealsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // List meals by first letter (we’ll use 'a' as a “popular” demo feed)
    @GET("search.php")
    fun searchMealsByFirstLetter(@Query("f") first: String): Single<MealsResponse>

    // All categories
    @GET("categories.php")
    fun getCategories(): Single<CategoriesResponse>

    // Lookup a meal by ID for detail
    @GET("lookup.php")
    fun lookupMeal(@Query("i") id: String): Single<MealsResponse>
}
