package com.m3.rajat.piyush.studymatealpha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.m3.rajat.piyush.studymatealpha.presentation.navigation.StudyMateApp
import com.m3.rajat.piyush.studymatealpha.ui.theme.StudyMateTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        enableEdgeToEdge()

        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            StudyMateTheme {
                StudyMateApp(windowSizeClass = windowSizeClass)
            }
        }
    }
}