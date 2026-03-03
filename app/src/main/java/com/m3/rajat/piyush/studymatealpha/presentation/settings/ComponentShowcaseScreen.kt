package com.m3.rajat.piyush.studymatealpha.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ComponentShowcaseScreen(onNavigateBack: () -> Unit) {
    var sliderVal by remember { mutableFloatStateOf(0.5f) }
    var checkboxState by remember { mutableStateOf(false) }
    var radioState by remember { mutableStateOf(1) }
    val chips = listOf("Chip 1", "Chip 2", "Chip 3")
    var selectedChips by remember { mutableStateOf(setOf("Chip 1")) }
    
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            icon = { Icon(Icons.Default.Info, contentDescription = null) },
            title = { Text("Action Confirmation") },
            text = { Text("Are you sure you want to proceed with this highly important mock action styled with Material 3 tokens?") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Dismiss")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("M3 Showcase") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Buttons
            ShowcaseSection("Buttons") {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = { }) { Text("Filled") }
                    ElevatedButton(onClick = { }) { Text("Elevated") }
                    OutlinedButton(onClick = { }) { Text("Outlined") }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { }) { Text("Text Button") }
                }
            }

            // Selection Controls
            ShowcaseSection("Selection Controls (Radio/Checkbox)") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = checkboxState, onCheckedChange = { checkboxState = it })
                    Text("Checkbox Option")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = radioState == 1, onClick = { radioState = 1 })
                    Text("Radio 1")
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(selected = radioState == 2, onClick = { radioState = 2 })
                    Text("Radio 2")
                }
            }

            // Chips
            ShowcaseSection("Filter Chips") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    chips.forEach { chip ->
                        val isSelected = selectedChips.contains(chip)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                selectedChips = if (isSelected) selectedChips - chip else selectedChips + chip
                            },
                            label = { Text(chip) },
                            leadingIcon = if (isSelected) {
                                {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                                    )
                                }
                            } else null
                        )
                    }
                }
            }

            // Slider
            ShowcaseSection("Slider") {
                Slider(
                    value = sliderVal,
                    onValueChange = { sliderVal = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Dialogs
            ShowcaseSection("Dialogs") {
                Button(onClick = { showDialog = true }) {
                    Text("Show Alert Dialog")
                }
            }
        }
    }
}

@Composable
private fun ShowcaseSection(title: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
}
