package com.example.mealmate.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mealmate.data.remote.models.CategoryDto
import com.example.mealmate.data.remote.models.MealDto
import com.example.mealmate.ui.common.FrostedCard
import com.example.mealmate.ui.common.GradientBackground
import com.example.mealmate.ui.common.PrimaryButton
import com.example.mealmate.ui.common.SolidTopBar
import com.example.mealmate.ui.common.shimmerEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    openMeal: (String) -> Unit,
    openCategory: (CategoryDto) -> Unit,
    showBack: Boolean,
    onBack: () -> Unit,
    vm: HomeViewModel = koinViewModel()
) {
    val state by vm.state.collectAsState()
    LaunchedEffect(Unit) { vm.refresh() }

    Scaffold(
        topBar = {
            SolidTopBar(
                title = "MealMate",
                showBack = showBack,
                onBack = onBack
            )
        },
        backgroundColor = Color.Transparent
    ){ pad ->
        GradientBackground(Modifier.fillMaxSize().padding(pad)) {
            Box(Modifier.fillMaxSize()) {
                FrostedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .align(Alignment.Center)
                ) {
                    when (val s = state) {
                        is HomeUiState.Loading -> ShimmerList()
                        is HomeUiState.Error -> ErrorView(s.message) { vm.refresh() }
                        is HomeUiState.Success -> {
                            var tab by remember { mutableStateOf(0) }
                            Column(Modifier.fillMaxWidth()) {
                                TabRowStyled(tab) { tab = it }
                                Spacer(Modifier.height(8.dp))
                                if (tab == 0) MealsList(s.meals, openMeal)
                                else CategoriesList(s.categories, openCategory)

                                Spacer(Modifier.height(8.dp))
                                Divider(Modifier.alpha(0.15f))
                                Spacer(Modifier.height(8.dp))

                                // A soft CTA at bottom
                                PrimaryButton(
                                    text = if (tab == 0) "Explore Categories" else "See Meals",
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    tab = 1 - tab
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TabRowStyled(selected: Int, onSelect: (Int) -> Unit) {
    TabRow(
        selectedTabIndex = selected,
        backgroundColor = androidx.compose.ui.graphics.Color.Transparent,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        Tab(selected = selected == 0, onClick = { onSelect(0) }, text = { Text("Meals (A)") })
        Tab(selected = selected == 1, onClick = { onSelect(1) }, text = { Text("Categories") })
    }
}

@Composable
private fun ShimmerList() {
    Column(Modifier.fillMaxWidth()) {
        TabRowStyled(selected = 0, onSelect = {})
        Spacer(Modifier.height(8.dp))
        repeat(6) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .padding(vertical = 8.dp)
                    .shimmerEffect(),
                verticalAlignment = Alignment.CenterVertically
            ) { }
        }
    }
}

@Composable
private fun ErrorView(msg: String, onRetry: () -> Unit) {
    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Oops: $msg")
        Spacer(Modifier.height(12.dp))
        PrimaryButton(text = "Retry", onClick = onRetry, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun MealsList(items: List<MealDto>, onClick: (String) -> Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(items, key = { it.idMeal }) { m ->
            MealRow(m) { onClick(m.idMeal) }
            Divider(Modifier.alpha(0.12f))
        }
    }
}

@Composable
private fun MealRow(m: MealDto, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(model = m.strMealThumb, contentDescription = m.strMeal, modifier = Modifier.size(64.dp))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                m.strMeal.orEmpty(),
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(m.strCategory.orEmpty(), style = MaterialTheme.typography.body2)
        }
        IconButton(onClick = onClick) { Icon(Icons.Filled.ArrowForward, contentDescription = "Open") }
    }
}

@Composable
private fun CategoriesList(items: List<CategoryDto>, onClick: (CategoryDto) -> Unit) {
    LazyColumn(Modifier.fillMaxWidth()) {
        items(items, key = { it.idCategory }) { c ->
            CategoryRow(c) { onClick(c) }
            Divider(Modifier.alpha(0.12f))
        }
    }
}

@Composable
private fun CategoryRow(c: CategoryDto, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(model = c.strCategoryThumb, contentDescription = c.strCategory, modifier = Modifier.size(64.dp))
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(c.strCategory, style = MaterialTheme.typography.h6)
            Text(c.strCategoryDescription, style = MaterialTheme.typography.body2, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
        IconButton(onClick = onClick) { Icon(Icons.Filled.ArrowForward, contentDescription = "Open") }
    }
}