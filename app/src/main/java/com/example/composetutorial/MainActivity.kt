package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.composetutorial.counter.CounterScreen
import com.example.composetutorial.ui.theme.CounterTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterTutorialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MyAppNavHost()
                }
            }
        }
    }
}

