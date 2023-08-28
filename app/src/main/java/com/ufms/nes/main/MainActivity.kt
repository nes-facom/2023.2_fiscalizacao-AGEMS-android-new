package com.ufms.nes.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.ufms.nes.core.ui.AgemsApplicationTheme
import com.ufms.nes.main.ui.AgemsApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AgemsApplicationTheme {
                AgemsApp()
            }
        }
    }
}
