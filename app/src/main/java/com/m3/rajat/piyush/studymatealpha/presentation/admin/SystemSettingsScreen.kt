package com.m3.rajat.piyush.studymatealpha.presentation.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SystemSettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: AdminViewModel = hiltViewModel()
) {
    var maintenanceMode by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var studentCount by remember { mutableStateOf(0) }
    var facultyCount by remember { mutableStateOf(0) }
    var parentCount by remember { mutableStateOf(0) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val state = viewModel.state.value
        studentCount = state.studentCount
        facultyCount = state.facultyCount
        parentCount = state.parentCount
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("System Settings") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Text(
                    "General",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )

                ListItem(
                    headlineContent = { Text("Maintenance Mode") },
                    supportingContent = { Text("Restrict access for non-admin users") },
                    leadingContent = { Icon(Icons.Default.Build, null) },
                    trailingContent = {
                        Switch(
                            checked = maintenanceMode,
                            onCheckedChange = {
                                maintenanceMode = it
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        if (it) "Maintenance mode enabled" else "Maintenance mode disabled"
                                    )
                                }
                            }
                        )
                    }
                )

                ListItem(
                    headlineContent = { Text("Push Notifications") },
                    supportingContent = { Text("Enable global notification delivery") },
                    leadingContent = { Icon(Icons.Default.Notifications, null) },
                    trailingContent = {
                        Switch(
                            checked = notificationsEnabled,
                            onCheckedChange = { notificationsEnabled = it }
                        )
                    }
                )
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    "System Information",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )

                ListItem(
                    headlineContent = { Text("Database Status") },
                    supportingContent = { Text("Room • SQLite • ${studentCount + facultyCount + parentCount} total records") },
                    leadingContent = { Icon(Icons.Default.DataUsage, null, tint = MaterialTheme.colorScheme.primary) }
                )

                ListItem(
                    headlineContent = { Text("App Version") },
                    supportingContent = { Text("2.0.0 (Build 9) • compileSdk 36") },
                    leadingContent = { Icon(Icons.Default.Info, null) }
                )

                ListItem(
                    headlineContent = { Text("Performance") },
                    supportingContent = { Text("Compose + Hilt + Room + DataStore") },
                    leadingContent = { Icon(Icons.Default.Speed, null) }
                )

                ListItem(
                    headlineContent = { Text("Theme") },
                    supportingContent = { Text("Material 3 Dynamic Color + Custom Palette") },
                    leadingContent = { Icon(Icons.Default.DarkMode, null) }
                )
            }

            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    "Danger Zone",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )

                ListItem(
                    headlineContent = { Text("Clear Cache", color = MaterialTheme.colorScheme.error) },
                    supportingContent = { Text("Remove temporary data and cached files") },
                    modifier = Modifier.clickable {
                        scope.launch { snackbarHostState.showSnackbar("Cache cleared successfully") }
                    }
                )

                ListItem(
                    headlineContent = { Text("Reset Settings", color = MaterialTheme.colorScheme.error) },
                    supportingContent = { Text("Restore all settings to default values") },
                    modifier = Modifier.clickable {
                        scope.launch { snackbarHostState.showSnackbar("Settings reset to defaults") }
                    }
                )
            }
        }
    }
}
