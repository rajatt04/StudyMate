package com.m3.rajat.piyush.studymatealpha.presentation.dashboard

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.m3.rajat.piyush.studymatealpha.presentation.admin.AdminDashState
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
    val extendedColors = com.m3.rajat.piyush.studymatealpha.ui.theme.LocalExtendedColors.current

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
        containerColor = extendedColors.surfaceContainerLowest,
        topBar = {
            LargeTopAppBar(
                title = { 
                    Text(
                        "Overview",
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
                    ) 
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = extendedColors.surfaceContainerLow.copy(alpha = 0.9f)
                )
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
            AnimatedContent(
                targetState = Triple(uiState.isLoading, uiState.errorMessage != null, uiState),
                transitionSpec = {
                    fadeIn(com.m3.rajat.piyush.studymatealpha.ui.theme.StudyMateMotion.tweenEnter()) togetherWith
                            fadeOut(com.m3.rajat.piyush.studymatealpha.ui.theme.StudyMateMotion.tweenExit())
                },
                label = "dashboardContent"
            ) { (loading, hasError, state) ->
                when {
                    loading && !isRefreshing -> {
                        LoadingScreen()
                    }
                    hasError -> {
                        ErrorScreen(
                            message = state.errorMessage!!,
                            onRetry = { viewModel.loadDashboard() }
                        )
                    }
                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 120.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item(span = { GridItemSpan(2) }) {
                                DashboardHeaderCard(state)
                            }
                            
                            item(span = { GridItemSpan(2) }) {
                                SectionHeader("Key Metrics")
                            }

                            item {
                                MetricCard(
                                    title = "Students",
                                    value = state.studentCount.toString(),
                                    icon = Icons.Default.School,
                                    color = com.m3.rajat.piyush.studymatealpha.ui.theme.RoleColors.studentPrimary
                                )
                            }
                            item {
                                MetricCard(
                                    title = "Faculty",
                                    value = state.facultyCount.toString(),
                                    icon = Icons.Default.CoPresent,
                                    color = com.m3.rajat.piyush.studymatealpha.ui.theme.RoleColors.facultyPrimary
                                )
                            }
                            item {
                                MetricCard(
                                    title = "Parents",
                                    value = state.parentCount.toString(),
                                    icon = Icons.Default.FamilyRestroom,
                                    color = com.m3.rajat.piyush.studymatealpha.ui.theme.RoleColors.parentPrimary
                                )
                            }
                            item {
                                MetricCard(
                                    title = "Notices",
                                    value = state.noticeCount.toString(),
                                    icon = Icons.Default.Campaign,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }

                            item(span = { GridItemSpan(2) }) {
                                SectionHeader("Finances & Stats")
                            }

                            item(span = { GridItemSpan(2) }) {
                                FinancialSummaryCard(state)
                            }

                            item(span = { GridItemSpan(2) }) {
                                StaffAttendanceCard(
                                    presentCount = state.assignmentCount,
                                    totalCount = state.studentCount.coerceAtLeast(1)
                                )
                            }

                            item(span = { GridItemSpan(2) }) {
                                SectionHeader("Quick Actions")
                            }

                            item { ActionCard(title = "Add Student", icon = Icons.Default.PersonAdd, color = com.m3.rajat.piyush.studymatealpha.ui.theme.RoleColors.studentPrimary, onClick = onNavigateToAddStudent) }
                            item { ActionCard(title = "Add Faculty", icon = Icons.Default.CoPresent, color = com.m3.rajat.piyush.studymatealpha.ui.theme.RoleColors.facultyPrimary, onClick = onNavigateToAddFaculty) }
                            item { ActionCard(title = "Add Parent", icon = Icons.Default.FamilyRestroom, color = com.m3.rajat.piyush.studymatealpha.ui.theme.RoleColors.parentPrimary, onClick = onNavigateToAddParent) }
                            item { ActionCard(title = "Classes", icon = Icons.Default.Class, color = MaterialTheme.colorScheme.primary, onClick = onNavigateToClassManagement) }
                            item { ActionCard(title = "Broadcast", icon = Icons.Default.Campaign, color = MaterialTheme.colorScheme.secondary, onClick = onNavigateToNotices) }
                            item { ActionCard(title = "Library", icon = Icons.AutoMirrored.Filled.MenuBook, color = MaterialTheme.colorScheme.tertiary, onClick = onNavigateToLibrary) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

@Composable
private fun DashboardHeaderCard(state: AdminDashState) {
    val totalUsers = state.studentCount + state.facultyCount + state.parentCount
    Card(
        modifier = Modifier.fillMaxWidth().height(160.dp),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, pressedElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                )
        ) {
            // Background patterns
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.1f),
                modifier = Modifier.size(200.dp).align(Alignment.BottomEnd).offset(x = 40.dp, y = 40.dp)
            )
            
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Total Community",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = totalUsers.toString(),
                        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Black),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Active Users",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricCard(title: String, value: String, icon: ImageVector, color: Color) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth().height(140.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = com.m3.rajat.piyush.studymatealpha.ui.theme.LocalExtendedColors.current.surfaceContainerLow
        ),
        border = BorderStroke(1.dp, color.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Surface(
                    shape = CircleShape,
                    color = color.copy(alpha = 0.15f),
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun FinancialSummaryCard(state: AdminDashState) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.Class, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "Total Fees Collected",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        "₹${state.totalFeesCollected.toInt()}",
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text("Received this term", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "₹${state.totalFeesPending.toInt()}",
                        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                        color = if (state.totalFeesPending > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                    Text("Pending Balance", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            if (state.overdueCount > 0) {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "⚠️ ${state.overdueCount} payments are overdue",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun StaffAttendanceCard(presentCount: Int, totalCount: Int) {
    val progress = presentCount.toFloat() / totalCount.toFloat()
    val pct = (progress * 100).toInt()
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Engagement Rate", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold), color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "$presentCount tasks assigned by faculties", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { progress }, 
                    modifier = Modifier.size(64.dp), 
                    strokeWidth = 6.dp, 
                    color = MaterialTheme.colorScheme.primary, 
                    trackColor = MaterialTheme.colorScheme.primaryContainer,
                    strokeCap = StrokeCap.Round
                )
                Text(
                    text = "$pct%", 
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold), 
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActionCard(title: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    var pressed by rememberSaveable { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "scale"
    )

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().height(110.dp).scale(scale),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = com.m3.rajat.piyush.studymatealpha.ui.theme.LocalExtendedColors.current.surfaceContainerLow
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = color.copy(alpha = 0.1f),
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = title, tint = color, modifier = Modifier.size(24.dp))
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title, 
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold), 
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
