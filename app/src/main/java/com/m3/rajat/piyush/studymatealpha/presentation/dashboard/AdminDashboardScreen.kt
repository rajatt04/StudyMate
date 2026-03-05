package com.m3.rajat.piyush.studymatealpha.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m3.rajat.piyush.studymatealpha.presentation.admin.AdminViewModel
import com.m3.rajat.piyush.studymatealpha.presentation.common.ErrorScreen
import com.m3.rajat.piyush.studymatealpha.presentation.common.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onNavigateToAddStudent: () -> Unit = {},
    onNavigateToAddFaculty: () -> Unit = {},
    onNavigateToAddParent: () -> Unit = {},
    onNavigateToClassManagement: () -> Unit = {},
    onNavigateToNotices: () -> Unit = {},
    onNavigateToLibrary: () -> Unit = {},
    viewModel: AdminViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    var isRefreshing by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            kotlinx.coroutines.delay(1000L)
            while (viewModel.state.value.isLoading) {
                kotlinx.coroutines.delay(15L)
            }
            isRefreshing = false
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text("Admin Dashboard") },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = {
                isRefreshing = true
                viewModel.loadDashboard()
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading && !isRefreshing -> {
                    LoadingScreen()
                }
                uiState.errorMessage != null -> {
                    ErrorScreen(
                        message = uiState.errorMessage!!,
                        onRetry = { viewModel.loadDashboard() }
                    )
                }
                else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item(span = { GridItemSpan(2) }) {
                        Text(
                            text = "Overview",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    item {
                        MetricCard(
                            title = "Total Students",
                            value = uiState.studentCount.toString(),
                            icon = Icons.Default.School,
                            trendStr = "Live from database"
                        )
                    }
                    item {
                        MetricCard(
                            title = "Total Faculty",
                            value = uiState.facultyCount.toString(),
                            icon = Icons.Default.CoPresent,
                            trendStr = "Live from database"
                        )
                    }
                    item {
                        MetricCard(
                            title = "Total Parents",
                            value = uiState.parentCount.toString(),
                            icon = Icons.Default.FamilyRestroom,
                            trendStr = "Live from database"
                        )
                    }
                    item {
                        MetricCard(
                            title = "Notices Posted",
                            value = uiState.noticeCount.toString(),
                            icon = Icons.Default.Campaign,
                            trendStr = "Active notices"
                        )
                    }

                    // Fee Summary Card
                    item(span = { GridItemSpan(2) }) {
                        StaffAttendanceCard(
                            presentCount = uiState.assignmentCount,
                            totalCount = uiState.studentCount.coerceAtLeast(1)
                        )
                    }

                    // Financial Overview
                    item(span = { GridItemSpan(2) }) {
                        OutlinedCard(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    "Financial Summary",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(bottom = 12.dp)
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            "â‚¹${uiState.totalFeesCollected.toInt()}",
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text("Collected", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text(
                                            "â‚¹${uiState.totalFeesPending.toInt()}",
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = if (uiState.totalFeesPending > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                                        )
                                        Text("Pending", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                                if (uiState.overdueCount > 0) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "âš ï¸ ${uiState.overdueCount} overdue payment(s)",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }

                    item(span = { GridItemSpan(2) }) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Quick Actions",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    item { ActionCard(title = "Add Student", icon = Icons.Default.PersonAdd, onClick = onNavigateToAddStudent) }
                    item { ActionCard(title = "Add Faculty", icon = Icons.Default.CoPresent, onClick = onNavigateToAddFaculty) }
                    item { ActionCard(title = "Add Parent", icon = Icons.Default.FamilyRestroom, onClick = onNavigateToAddParent) }
                    item { ActionCard(title = "Manage Classes", icon = Icons.Default.Class, onClick = onNavigateToClassManagement) }
                    item { ActionCard(title = "Issue Notice", icon = Icons.Default.Campaign, onClick = onNavigateToNotices) }
                    item { ActionCard(title = "Library System", icon = Icons.AutoMirrored.Filled.MenuBook, onClick = onNavigateToLibrary) }
                }
            }
        }
        }
    }
}

@Composable
private fun MetricCard(title: String, value: String, icon: ImageVector, trendStr: String) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(shape = CircleShape, color = MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.size(40.dp)) {
                    Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.padding(8.dp).size(24.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = value, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold), color = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = trendStr, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun StaffAttendanceCard(presentCount: Int, totalCount: Int) {
    val progress = presentCount.toFloat() / totalCount.toFloat()
    val pct = (progress * 100).toInt()
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Staff Registered", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                Text(text = "$presentCount Faculty Members", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(progress = { progress }, modifier = Modifier.size(48.dp), strokeWidth = 4.dp, color = MaterialTheme.colorScheme.primary, trackColor = MaterialTheme.colorScheme.surfaceVariant)
                Text(text = "$pct%", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActionCard(title: String, icon: ImageVector, onClick: () -> Unit = {}) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Icon(imageVector = icon, contentDescription = title, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}
