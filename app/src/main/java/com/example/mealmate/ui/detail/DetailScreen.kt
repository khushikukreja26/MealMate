package com.example.mealmate.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mealmate.data.remote.models.CategoryDto
import com.example.mealmate.data.remote.models.MealDto
import com.example.mealmate.ui.common.FrostedCard
import com.example.mealmate.ui.common.GradientBackground
import com.example.mealmate.ui.common.PrimaryButton
import org.koin.androidx.compose.koinViewModel
import com.example.mealmate.ui.common.SolidTopBar
import androidx.compose.ui.graphics.Color


@Composable
fun DetailScreen(
    type: String,
    id: String?,
    category: CategoryDto? = null,
    showBack: Boolean = true,
    onBack: () -> Unit = {},
    onOpenRecipe: (mealName: String, instructions: String) -> Unit = { _, _ -> },
    vm: DetailViewModel = koinViewModel()
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(type, id, category) {
        when (type) {
            "meal" -> if (!id.isNullOrBlank()) vm.loadMeal(id)
            "category" -> vm.showCategory(category ?: CategoryDto("0","Unknown","","No description"))
        }
    }

    Scaffold(
        topBar = {
            SolidTopBar(
                title = type.replaceFirstChar { it.uppercase() },
                showBack = showBack,
                onBack = onBack
            )
        },
        backgroundColor = Color.Transparent
    ) { pad ->
        GradientBackground(Modifier.fillMaxSize().padding(pad)) {
            Box(Modifier.fillMaxSize()) {
                FrostedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .align(Alignment.Center)
                ) {
                    when (val s = state) {
                        is DetailUiState.Loading -> {
                            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                        is DetailUiState.Error -> Text("Error: ${s.message}")
                        is DetailUiState.Meal -> MealDetailContent(s.data) {
                            onOpenRecipe(s.data.strMeal.orEmpty(), s.data.strInstructions.orEmpty())
                        }
                        is DetailUiState.Category -> CategoryDetailContent(s.data)
                    }
                }
            }
        }
    }
}

@Composable
private fun MealDetailContent(
    m: MealDto,
    onOpenRecipe: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = m.strMealThumb, contentDescription = m.strMeal, modifier = Modifier.size(220.dp))
        Spacer(Modifier.height(16.dp))
        Text(m.strMeal.orEmpty(), style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)
        Spacer(Modifier.height(8.dp))
        Text(listOfNotNull(m.strCategory, m.strArea).joinToString(" • "))

        Spacer(Modifier.height(18.dp))
        Text("Ingredients", style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(8.dp))
        IngredientsTable(
            rows = m.ingredientPairs(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))
        PrimaryButton(text = "View Recipe", modifier = Modifier.fillMaxWidth(), onClick = onOpenRecipe)

        Spacer(Modifier.height(16.dp))
//        Text("Instructions (preview)", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
//        Spacer(Modifier.height(6.dp))
//        Text(m.strInstructions.orEmpty(), maxLines = 4, modifier = Modifier.alpha(0.9f))
    }
}

@Composable
private fun CategoryDetailContent(c: CategoryDto) {
    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = c.strCategoryThumb, contentDescription = c.strCategory, modifier = Modifier.size(220.dp))
        Spacer(Modifier.height(16.dp))
        Text(c.strCategory, style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        Text(c.strCategoryDescription)
    }
}

/** Same table you had, kept intact — fits inside frosted card */
@Composable
private fun IngredientsTable(
    rows: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier, elevation = 2.dp) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Text("Ingredient", style = MaterialTheme.typography.subtitle1, modifier = Modifier.weight(0.6f), fontWeight = FontWeight.Bold)
                Text("Measure", style = MaterialTheme.typography.subtitle1, modifier = Modifier.weight(0.4f), textAlign = TextAlign.End, fontWeight = FontWeight.Bold)
            }
            Divider()
            rows.forEachIndexed { idx, (ingredient, measure) ->
                Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp)) {
                    Text(ingredient, style = MaterialTheme.typography.body1, modifier = Modifier.weight(0.6f))
                    Text(if (measure.isBlank()) "-" else measure, style = MaterialTheme.typography.body1, modifier = Modifier.weight(0.4f), textAlign = TextAlign.End)
                }
                if (idx != rows.lastIndex) Divider()
            }
        }
    }
}