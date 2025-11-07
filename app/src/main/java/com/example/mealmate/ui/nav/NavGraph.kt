package com.example.mealmate.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mealmate.data.remote.models.CategoryDto
import com.example.mealmate.ui.detail.DetailScreen
import com.example.mealmate.ui.home.HomeScreen
import com.example.mealmate.ui.recipe.RecipeScreen
import com.example.mealmate.ui.start.StartScreen

object Routes {
    const val START = "start"
    const val HOME = "home"
    const val DETAIL_MEAL = "detail/meal/{id}"
    const val DETAIL_CATEGORY = "detail/category"
    const val RECIPE = "detail/recipe"              // 👈 NEW
}

@Composable
fun AppNav(modifier: Modifier = Modifier) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = Routes.START,
        modifier = modifier
    ) {
        composable(Routes.START) {
            StartScreen(
                onStart = {
                    nav.navigate(Routes.HOME) {
                        popUpTo(Routes.START) { inclusive = true }
                    }
                },
                showBack = nav.previousBackStackEntry != null,
                onBack = { nav.navigateUp() }
            )
        }

        composable(Routes.HOME) {
            HomeScreen(
                openMeal = { id -> nav.navigate("detail/meal/$id") },
                openCategory = { cat ->
                    nav.currentBackStackEntry?.savedStateHandle?.set("category", cat)
                    nav.navigate(Routes.DETAIL_CATEGORY)
                },
                showBack = nav.previousBackStackEntry != null,
                onBack = { nav.navigateUp() }
            )
        }

        composable(
            Routes.DETAIL_MEAL,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { back ->
            val id = back.arguments?.getString("id") ?: ""
            DetailScreen(
                type = "meal",
                id = id,
                showBack = true,
                onBack = { nav.navigateUp() },
                onOpenRecipe = { mealName, instructions ->
                    // pass big text via SavedStateHandle (safe and simple)
                    nav.currentBackStackEntry?.savedStateHandle?.set("recipe_title", mealName)
                    nav.currentBackStackEntry?.savedStateHandle?.set("recipe_text", instructions)
                    nav.navigate(Routes.RECIPE)
                }
            )
        }

        composable(Routes.DETAIL_CATEGORY) {
            val cat = nav.previousBackStackEntry
                ?.savedStateHandle
                ?.get<CategoryDto>("category")
            DetailScreen(
                type = "category",
                id = null,
                category = cat,
                showBack = true,
                onBack = { nav.navigateUp() }
            )
        }

        // 👇 NEW Recipe screen
        composable(Routes.RECIPE) {
            val recipeTitle = nav.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("recipe_title") ?: "Recipe"
            val recipeText = nav.previousBackStackEntry
                ?.savedStateHandle
                ?.get<String>("recipe_text") ?: ""
            RecipeScreen(
                title = recipeTitle,
                instructions = recipeText,
                onBack = { nav.navigateUp() }
            )
        }
    }
}