package com.m3.rajat.piyush.studymatealpha.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m3.rajat.piyush.studymatealpha.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleSelectionScreen(
    onRoleSelected: (String) -> Unit,
    viewModel: RoleSelectionViewModel = hiltViewModel()
) {
    val savedRole by viewModel.savedRole.collectAsState()
    var expandedRole by rememberSaveable { mutableStateOf<String?>(null) }

    // Debounce navigation — prevent multi-click
    var navigationInProgress by remember { mutableStateOf(false) }

    LaunchedEffect(navigationInProgress) {
        if (navigationInProgress) {
            kotlinx.coroutines.delay(500)
            navigationInProgress = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.select_your_role)) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.welcome_to_studymate),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(R.string.choose_account_type),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            RoleCard(
                roleKey = "STUDENT",
                title = stringResource(R.string.role_student),
                description = stringResource(R.string.role_student_desc),
                detail = stringResource(R.string.role_student_detail),
                icon = Icons.Default.Face,
                accentColor = MaterialTheme.colorScheme.primary,
                isExpanded = expandedRole == "STUDENT",
                isSelected = savedRole == "STUDENT",
                onExpandToggle = { expandedRole = if (expandedRole == "STUDENT") null else "STUDENT" },
                onClick = {
                    if (!navigationInProgress) {
                        navigationInProgress = true
                        viewModel.saveRole("STUDENT")
                        onRoleSelected("STUDENT")
                    }
                }
            )

            RoleCard(
                roleKey = "FACULTY",
                title = stringResource(R.string.role_faculty),
                description = stringResource(R.string.role_faculty_desc),
                detail = stringResource(R.string.role_faculty_detail),
                icon = Icons.Default.Person,
                accentColor = MaterialTheme.colorScheme.secondary,
                isExpanded = expandedRole == "FACULTY",
                isSelected = savedRole == "FACULTY",
                onExpandToggle = { expandedRole = if (expandedRole == "FACULTY") null else "FACULTY" },
                onClick = {
                    if (!navigationInProgress) {
                        navigationInProgress = true
                        viewModel.saveRole("FACULTY")
                        onRoleSelected("FACULTY")
                    }
                }
            )

            RoleCard(
                roleKey = "ADMIN",
                title = stringResource(R.string.role_admin),
                description = stringResource(R.string.role_admin_desc),
                detail = stringResource(R.string.role_admin_detail),
                icon = Icons.Default.AdminPanelSettings,
                accentColor = MaterialTheme.colorScheme.tertiary,
                isExpanded = expandedRole == "ADMIN",
                isSelected = savedRole == "ADMIN",
                onExpandToggle = { expandedRole = if (expandedRole == "ADMIN") null else "ADMIN" },
                onClick = {
                    if (!navigationInProgress) {
                        navigationInProgress = true
                        viewModel.saveRole("ADMIN")
                        onRoleSelected("ADMIN")
                    }
                }
            )

            RoleCard(
                roleKey = "PARENT",
                title = stringResource(R.string.role_parent),
                description = stringResource(R.string.role_parent_desc),
                detail = stringResource(R.string.role_parent_detail),
                icon = Icons.Default.FamilyRestroom,
                accentColor = MaterialTheme.colorScheme.error,
                isExpanded = expandedRole == "PARENT",
                isSelected = savedRole == "PARENT",
                onExpandToggle = { expandedRole = if (expandedRole == "PARENT") null else "PARENT" },
                onClick = {
                    if (!navigationInProgress) {
                        navigationInProgress = true
                        viewModel.saveRole("PARENT")
                        onRoleSelected("PARENT")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoleCard(
    roleKey: String,
    title: String,
    description: String,
    detail: String,
    icon: ImageVector,
    accentColor: Color,
    isExpanded: Boolean,
    isSelected: Boolean,
    onExpandToggle: () -> Unit,
    onClick: () -> Unit
) {
    val elevation by animateDpAsState(
        targetValue = if (isSelected) 8.dp else 2.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "cardElevation"
    )
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "cardScale"
    )
    val iconTint by animateColorAsState(
        targetValue = if (isSelected) accentColor else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "iconTint"
    )

    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .semantics {
                contentDescription = "$title role card. $description"
                role = Role.Button
            },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = iconTint
                )
                Column(
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                // Expand/collapse toggle
                androidx.compose.material3.IconButton(
                    onClick = onExpandToggle,
                    modifier = Modifier.semantics {
                        contentDescription = if (isExpanded) "Collapse $title details" else "Expand $title details"
                    }
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Expandable detail section
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Text(
                    text = detail,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 12.dp, start = 68.dp)
                )
            }
        }
    }
}
