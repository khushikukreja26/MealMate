package com.example.mealmate.ui.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.ui.common.SolidTopBar



@Composable
fun StartScreen(
    onStart: () -> Unit,
    showBack: Boolean,
    onBack: () -> Unit
) {
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
        // Gradient background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.primary.copy(alpha = 0.95f),
                            MaterialTheme.colors.primary.copy(alpha = 0.75f),
                            MaterialTheme.colors.secondary.copy(alpha = 0.65f)
                        )
                    )
                )
                .padding(pad)
        ) {
            // Foreground card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .align(Alignment.Center),
                color = Color.White.copy(alpha = 0.08f),
                contentColor = Color.White,
                shape = RoundedCornerShape(24.dp),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LogoBadge()

                    Spacer(Modifier.height(14.dp))

                    TitleBlock()

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Discover tasty meals, explore categories, and dive into step-by-step recipes.",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.alpha(0.95f)
                    )

                    Spacer(Modifier.height(16.dp))
                    Divider(
                        color = Color.White.copy(alpha = 0.15f),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Spacer(Modifier.height(16.dp))

                    FeatureChips()

                    Spacer(Modifier.height(22.dp))

                    Button(
                        onClick = onStart,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp)),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = MaterialTheme.colors.primary
                        ),
                        elevation = ButtonDefaults.elevation(defaultElevation = 2.dp, pressedElevation = 6.dp)
                    ) {
                        Text(
                            text = "Start",
                            style = MaterialTheme.typography.button.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }
                }
            }

            // Subtle footer credit
            Text(
                text = "MealMate",
                color = Color.White.copy(alpha = 0.35f),
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 14.dp)
            )
        }
    }
}

@Composable
private fun LogoBadge() {
    // Circular badge with a food icon
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.15f))
            .padding(18.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Fastfood,
            contentDescription = "Logo",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .height(32.dp)
        )
    }
}

@Composable
private fun TitleBlock() {
    // "Meal" normal + "Mate" accented
    val title = buildAnnotatedString {
        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) { append("Meal") }
        append(" ")
        withStyle(SpanStyle(fontWeight = FontWeight.ExtraBold)) { append("Mate") }
    }
    Text(
        text = title,
        style = MaterialTheme.typography.h3.copy(
            color = Color.White,
            letterSpacing = 0.5.sp
        ),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun FeatureChips() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Chip(icon = Icons.Filled.List, label = "Categories")
//        Chip(icon = Icons.Filled.Star, label = "Popular")
        Chip(icon = Icons.Filled.Fastfood, label = "Recipes")
    }
}

@Composable
private fun Chip(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Surface(
        color = Color.White.copy(alpha = 0.12f),
        contentColor = Color.White,
        shape = RoundedCornerShape(50),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, tint = Color.White.copy(alpha = 0.95f))
            Spacer(Modifier.height(0.dp).then(Modifier.padding(horizontal = 4.dp)))
            Text(label, style = MaterialTheme.typography.body2)
        }
    }
}