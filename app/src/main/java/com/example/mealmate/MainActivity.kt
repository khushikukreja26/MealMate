package com.example.mealmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mealmate.ui.nav.AppNav
import com.example.mealmate.ui.theme.AppTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 🔑 Provide Koin scope to Compose
            KoinAndroidContext {
                AppTheme { AppNav() }
            }
        }
    }
}