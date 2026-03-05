package com.m3.rajat.piyush.studymatealpha.presentation.admin

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.m3.rajat.piyush.studymatealpha.presentation.common.EmptyStateScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatabaseBackupScreen(onNavigateBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Database Backup") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        EmptyStateScreen(
            modifier = Modifier.padding(padding),
            title = "Database Backup",
            subtitle = "Features to export/import database records."
        )
    }
}
