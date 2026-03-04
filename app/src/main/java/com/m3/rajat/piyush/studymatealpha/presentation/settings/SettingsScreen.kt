package com.m3.rajat.piyush.studymatealpha.presentation.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    onNavigateToComponents: () -> Unit
) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkThemeEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                SettingsCategoryHeader("Preferences")
                
                ListItem(
                    headlineContent = { Text("Push Notifications") },
                    supportingContent = { Text("Receive alerts for classes and notices") },
                    leadingContent = { Icon(Icons.Default.Notifications, null) },
                    trailingContent = {
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it }
                        )
                    }
                )

                ListItem(
                    headlineContent = { Text("Force Dark Mode") },
                    supportingContent = { Text("Override system theme") },
                    leadingContent = { Icon(Icons.Default.DarkMode, null) },
                    trailingContent = {
                        Switch(
                            checked = darkThemeEnabled,
                            onCheckedChange = { darkThemeEnabled = it }
                        )
                    }
                )
                
                ListItem(
                    headlineContent = { Text("Language") },
                    supportingContent = { Text("English (US)") },
                    leadingContent = { Icon(Icons.Default.Language, null) },
                    modifier = Modifier.clickable { }
                )
            }
            
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                SettingsCategoryHeader("Developer & Design")
                
                ListItem(
                    headlineContent = { Text("M3 Component Showcase") },
                    supportingContent = { Text("Explore all interactive widgets and themes") },
                    leadingContent = { Icon(Icons.Default.Widgets, null, tint = MaterialTheme.colorScheme.primary) },
                    modifier = Modifier.clickable { onNavigateToComponents() }
                )
                
                ListItem(
                    headlineContent = { Text("Dynamic Color Palette") },
                    supportingContent = { Text("View system generated primary/secondary mapping") },
                    leadingContent = { Icon(Icons.Default.ColorLens, null) },
                    modifier = Modifier.clickable { }
                )
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                SettingsCategoryHeader("Account")
                
                ListItem(
                    headlineContent = { Text("Security") },
                    supportingContent = { Text("Password & Authentication") },
                    leadingContent = { Icon(Icons.Default.Security, null) },
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
}

@Composable
private fun SettingsCategoryHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}
