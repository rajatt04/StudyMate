package com.m3.rajat.piyush.studymatealpha.presentation.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.ui.theme.LocalExtendedColors
import com.m3.rajat.piyush.studymatealpha.ui.theme.RoleColors
import com.m3.rajat.piyush.studymatealpha.ui.theme.StudyMateMotion
import kotlinx.coroutines.delay

data class RoleInfo(
    val key: String,
    val titleRes: Int,
    val descRes: Int,
    val detailRes: Int,
    val icon: ImageVector,
    val accentColor: Color,
    val containerColor: Color
)

@Composable
fun RoleSelectionScreen(
    onRoleSelected: (String) -> Unit,
    viewModel: RoleSelectionViewModel = hiltViewModel()
) {
    val savedRole by viewModel.savedRole.collectAsState()
    var expandedRole by rememberSaveable { mutableStateOf<String?>(null) }
    var navigationInProgress by remember { mutableStateOf(false) }
    val extendedColors = LocalExtendedColors.current

    LaunchedEffect(navigationInProgress) {
        if (navigationInProgress) {
            delay(500)
            navigationInProgress = false
        }
    }

    val roles = remember {
        listOf(
            RoleInfo("STUDENT", R.string.role_student, R.string.role_student_desc, R.string.role_student_detail, Icons.Default.Face, RoleColors.studentPrimary, RoleColors.studentContainer),
            RoleInfo("FACULTY", R.string.role_faculty, R.string.role_faculty_desc, R.string.role_faculty_detail, Icons.Default.Person, RoleColors.facultyPrimary, RoleColors.facultyContainer),
            RoleInfo("ADMIN", R.string.role_admin, R.string.role_admin_desc, R.string.role_admin_detail, Icons.Default.AdminPanelSettings, RoleColors.adminPrimary, RoleColors.adminContainer),
            RoleInfo("PARENT", R.string.role_parent, R.string.role_parent_desc, R.string.role_parent_detail, Icons.Default.FamilyRestroom, RoleColors.parentPrimary, RoleColors.parentContainer)
        )
    }

    // ─── Staggered entrance ──────────────────────────────────────────
    val visibilityStates = remember { roles.map { mutableStateOf(false) } }
    LaunchedEffect(Unit) {
        roles.forEachIndexed { index, _ ->
            delay(StudyMateMotion.STAGGER_DELAY.toLong() * (index + 1))
            visibilityStates[index].value = true
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = extendedColors.surfaceContainerLowest
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // ─── Header ──────────────────────────────────────────────
            Text(
                text = stringResource(R.string.welcome_to_studymate),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.choose_account_type),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ─── Role Cards ──────────────────────────────────────────
            roles.forEachIndexed { index, roleInfo ->
                AnimatedVisibility(
                    visible = visibilityStates[index].value,
                    enter = fadeIn(StudyMateMotion.tweenEnter(StudyMateMotion.DURATION_MEDIUM)) +
                            slideInVertically(
                                initialOffsetY = { 60 },
                                animationSpec = StudyMateMotion.tweenEnter(StudyMateMotion.DURATION_MEDIUM)
                            )
                ) {
                    PremiumRoleCard(
                        roleInfo = roleInfo,
                        isExpanded = expandedRole == roleInfo.key,
                        isSelected = savedRole == roleInfo.key,
                        onExpandToggle = {
                            expandedRole = if (expandedRole == roleInfo.key) null else roleInfo.key
                        },
                        onClick = {
                            if (!navigationInProgress) {
                                navigationInProgress = true
                                viewModel.saveRole(roleInfo.key)
                                onRoleSelected(roleInfo.key)
                            }
                        }
                    )
                }
                if (index < roles.lastIndex) Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun PremiumRoleCard(
    roleInfo: RoleInfo,
    isExpanded: Boolean,
    isSelected: Boolean,
    onExpandToggle: () -> Unit,
    onClick: () -> Unit
) {
    val extendedColors = LocalExtendedColors.current

    // ─── Animated values ─────────────────────────────────────────────
    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.0f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "scale"
    )
    val elevation by animateDpAsState(
        targetValue = if (isSelected) 6.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "elev"
    )
    val containerColor by animateColorAsState(
        targetValue = if (isSelected) roleInfo.containerColor
        else extendedColors.surfaceContainerLow,
        animationSpec = StudyMateMotion.tweenStandard(),
        label = "bg"
    )
    val iconContainerColor by animateColorAsState(
        targetValue = if (isSelected) roleInfo.accentColor.copy(alpha = 0.15f)
        else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        animationSpec = StudyMateMotion.tweenStandard(),
        label = "iconBg"
    )
    val iconTint by animateColorAsState(
        targetValue = if (isSelected) roleInfo.accentColor
        else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = StudyMateMotion.tweenStandard(),
        label = "iconClr"
    )
    val accentBarWidth by animateDpAsState(
        targetValue = if (isSelected) 4.dp else 0.dp,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "bar"
    )

    val title = stringResource(roleInfo.titleRes)

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .semantics {
                contentDescription = "$title role card"
                role = Role.Button
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // ─── Accent bar ──────────────────────────────────────
            if (accentBarWidth > 0.dp) {
                Box(
                    modifier = Modifier
                        .width(accentBarWidth)
                        .height(if (isExpanded) 120.dp else 88.dp)
                        .clip(RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
                        .background(roleInfo.accentColor)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // ─── Icon in tonal circle ────────────────────
                    Surface(
                        shape = CircleShape,
                        color = iconContainerColor,
                        modifier = Modifier.size(52.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = roleInfo.icon,
                                contentDescription = null,
                                modifier = Modifier.size(28.dp),
                                tint = iconTint
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(roleInfo.titleRes),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = stringResource(roleInfo.descRes),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // ─── Expand / Navigate ───────────────────────
                    IconButton(
                        onClick = onExpandToggle,
                        modifier = Modifier.semantics {
                            contentDescription = if (isExpanded) "Collapse details" else "Expand details"
                        }
                    ) {
                        AnimatedContent(
                            targetState = isExpanded,
                            transitionSpec = {
                                fadeIn(StudyMateMotion.tweenEnter()) togetherWith
                                        fadeOut(StudyMateMotion.tweenExit())
                            },
                            label = "expandIcon"
                        ) { expanded ->
                            Icon(
                                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ChevronRight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // ─── Expandable detail ───────────────────────────────
                AnimatedVisibility(
                    visible = isExpanded,
                    enter = expandVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    ) + fadeIn(),
                    exit = shrinkVertically(
                        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                    ) + fadeOut()
                ) {
                    Text(
                        text = stringResource(roleInfo.detailRes),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 12.dp, start = 68.dp)
                    )
                }
            }
        }
    }
}
