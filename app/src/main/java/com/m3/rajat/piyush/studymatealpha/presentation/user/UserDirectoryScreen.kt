package com.m3.rajat.piyush.studymatealpha.presentation.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m3.rajat.piyush.studymatealpha.presentation.common.LoadingScreen

data class DirectoryUser(val name: String, val role: String, val detail: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDirectoryScreen(
    viewModel: UserViewModel = hiltViewModel()
) {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    val uiState by viewModel.directoryState.collectAsState()

    // Map to unified list
    val allUsers = uiState.students.map {
        DirectoryUser(it.studentName, "Student", it.studentClass)
    } + uiState.faculty.map {
        DirectoryUser(it.facultyName, "Faculty", it.facultySub)
    }

    // Filter
    val filteredUsers = if (text.isBlank()) allUsers else allUsers.filter {
        it.name.contains(text, ignoreCase = true) || it.detail.contains(text, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                LoadingScreen()
            }
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp , end = 16.dp)
                ) {
                    SearchBar(
                        query = text,
                        onQueryChange = { text = it },
                        onSearch = { active = false },
                        active = active,
                        onActiveChange = { active = it },
                        placeholder = { Text("Search users...") },
                        leadingIcon = { Icon(Icons.Default.Search, null) },
                        trailingIcon = {
                            if (active) {
                                IconButton(onClick = { if (text.isNotEmpty()) text = "" else active = false }) {
                                    Icon(Icons.Default.Close, "Clear")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Suggestions while active
                        LazyColumn(modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(16.dp)) {
                            items(filteredUsers.take(5)) { user ->
                                ListItem(
                                    headlineContent = { Text(user.name) },
                                    supportingContent = { Text("${user.role} • ${user.detail}") },
                                    leadingContent = { Icon(Icons.Default.Search, null) }
                                )
                            }
                        }
                    }
                }

                if (filteredUsers.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (allUsers.isEmpty()) "No users registered yet." else "No results found.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(filteredUsers) { user ->
                            ListItem(
                                headlineContent = { Text(user.name, fontWeight = FontWeight.SemiBold) },
                                supportingContent = { Text("${user.role} • ${user.detail}") },
                                leadingContent = {
                                    Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.size(40.dp)) {
                                        Icon(Icons.Default.Person, null, modifier = Modifier.padding(8.dp), tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                    }
                                },
                                trailingContent = {
                                    IconButton(onClick = { }) { Icon(Icons.Default.MoreVert, "Options") }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
