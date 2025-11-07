package com.example.mealmate.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.ui.unit.dp

@Composable
fun SolidTopBar(
    title: String,
    showBack: Boolean,
    onBack: () -> Unit,
    actions: @Composable () -> Unit = {}
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,   // 🔵 solid blue (from your theme)
        contentColor = Color.White,                        // white content/icons
        elevation = 4.dp,
        title = { Text(title, color = Color.White) },
        navigationIcon = if (showBack) {
            {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        } else null,
        actions = {
            // Ensure action icons are white too
            androidx.compose.runtime.CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.high
            ) { actions() }
        }
    )
}