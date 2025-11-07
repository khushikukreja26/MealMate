package com.example.mealmate.ui.recipe


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.selection.SelectionContainer
import com.example.mealmate.ui.common.FrostedCard
import com.example.mealmate.ui.common.GradientBackground
import com.example.mealmate.ui.common.SolidTopBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text


@Composable
fun RecipeScreen(
    title: String,
    instructions: String,
    onBack: () -> Unit
) {
    val steps = remember(instructions) { normalizeToSteps(instructions) }
    val ctx = LocalContext.current

    val shareText = remember(title, steps) {
        buildString {
            appendLine(title.ifBlank { "Recipe" })
            appendLine()
            steps.forEachIndexed { i, s -> appendLine("${i + 1}. $s") }
        }
    }

    Scaffold(
        topBar = {
            SolidTopBar(
                title = title.ifBlank { "Recipe" },
                showBack = true,
                onBack = onBack,
                actions = {
                    IconButton(onClick = {
                        val intent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(android.content.Intent.EXTRA_SUBJECT, title.ifBlank { "Recipe" })
                            putExtra(android.content.Intent.EXTRA_TEXT, shareText)
                        }
                        ctx.startActivity(android.content.Intent.createChooser(intent, "Share Recipe"))
                    }) {
                        Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.White)
                    }
                }
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
                    if (steps.isEmpty()) {
                        Column(
                            Modifier.fillMaxWidth().padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("No instructions available.", style = MaterialTheme.typography.body1)
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            item {
                                Column(Modifier.fillMaxWidth()) {
                                    Text("Instructions", style = MaterialTheme.typography.h6)
                                    Spacer(Modifier.height(6.dp))
                                    Divider(Modifier.alpha(0.35f))
                                }
                            }
                            itemsIndexed(steps) { index, step ->
                                StepCard(number = index + 1, text = step)
                            }
                            item { Spacer(Modifier.height(8.dp)) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StepCard(number: Int, text: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .alpha(0.9f)
                    .background(MaterialTheme.colors.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.primary
                )
            }
            Spacer(Modifier.width(12.dp))
            SelectionContainer {
                Text(
                    text = prettyParagraph(text),
                    style = MaterialTheme.typography.body1.copy(lineHeight = 22.sp),
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

/* -------- helpers (same as before) -------- */

private fun prettyParagraph(text: String) = buildAnnotatedString {
    val clean = text.replace("\r\n", "\n").replace("\r", "\n").trim()
    append(clean)
}

private fun normalizeToSteps(raw: String): List<String> {
    if (raw.isBlank()) return emptyList()
    val normalized = raw.replace("\r\n", "\n").replace("\r", "\n").trim()

    val lineBased = normalized
        .lines()
        .map { it.trim() }
        .map { it.removePrefix("- ").removePrefix("* ") }
        .map { it.replace(Regex("""^\d+[\.\)]\s*"""), "") }
        .filter { it.isNotBlank() }

    val meaningful = lineBased.filter { it.length > 2 }
    if (meaningful.size >= 3) return meaningful

    return normalized
        .split(Regex("""(?<=[.!?])\s+"""))
        .map { it.trim() }
        .filter { it.isNotBlank() }
}