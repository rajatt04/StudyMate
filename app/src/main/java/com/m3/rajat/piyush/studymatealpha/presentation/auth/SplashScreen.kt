package com.m3.rajat.piyush.studymatealpha.presentation.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.m3.rajat.piyush.studymatealpha.ui.theme.StudyMateMotion
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigateNext: () -> Unit) {
    // ─── Staggered reveal state ──────────────────────────────────────
    var showIcon by remember { mutableStateOf(false) }
    var showTitle by remember { mutableStateOf(false) }
    var showTagline by remember { mutableStateOf(false) }

    val iconScale = remember { Animatable(0.3f) }

    LaunchedEffect(Unit) {
        delay(200)
        showIcon = true
        iconScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = 0.55f,
                stiffness = 300f
            )
        )
        delay(200)
        showTitle = true
        delay(250)
        showTagline = true
        delay(800)
        onNavigateNext()
    }

    // ─── Pulsing glow ring ───────────────────────────────────────────
    val infiniteTransition = rememberInfiniteTransition(label = "glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowAlpha"
    )
    val glowScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glowScale"
    )

    val primaryColor = MaterialTheme.colorScheme.primary
    val primaryContainerColor = MaterialTheme.colorScheme.primaryContainer
    val surfaceColor = MaterialTheme.colorScheme.surface
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        primaryColor,
                        primaryColor.copy(alpha = 0.85f),
                        primaryContainerColor.copy(alpha = 0.6f)
                    )
                )
            )
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ─── Icon with glow ring ─────────────────────────────────
            AnimatedVisibility(
                visible = showIcon,
                enter = fadeIn(animationSpec = StudyMateMotion.tweenEnter(StudyMateMotion.DURATION_MEDIUM))
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(140.dp)
                        .scale(iconScale.value)
                        .drawBehind {
                            // Outer pulsing glow ring
                            drawCircle(
                                color = onPrimaryColor.copy(alpha = glowAlpha * 0.4f),
                                radius = size.minDimension / 2 * glowScale,
                                style = Stroke(width = 3.dp.toPx())
                            )
                            // Inner glow ring
                            drawCircle(
                                color = onPrimaryColor.copy(alpha = glowAlpha * 0.2f),
                                radius = size.minDimension / 2 * (glowScale * 1.08f),
                                style = Stroke(width = 1.5.dp.toPx())
                            )
                        }
                ) {
                    // Icon background circle
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(
                                color = onPrimaryColor.copy(alpha = 0.15f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "StudyMate",
                            tint = onPrimaryColor,
                            modifier = Modifier.size(56.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ─── Title ───────────────────────────────────────────────
            AnimatedVisibility(
                visible = showTitle,
                enter = fadeIn(StudyMateMotion.tweenEnter(StudyMateMotion.DURATION_LONG)) +
                        slideInVertically(
                            initialOffsetY = { it / 3 },
                            animationSpec = StudyMateMotion.tweenEnter(StudyMateMotion.DURATION_LONG)
                        )
            ) {
                Text(
                    text = "StudyMate",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = (-1).sp
                    ),
                    color = onPrimaryColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ─── Tagline ─────────────────────────────────────────────
            AnimatedVisibility(
                visible = showTagline,
                enter = fadeIn(StudyMateMotion.tweenEnter(StudyMateMotion.DURATION_LONG)) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = StudyMateMotion.tweenEnter(StudyMateMotion.DURATION_LONG)
                        )
            ) {
                Text(
                    text = "Learn. Manage. Grow.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = onPrimaryColor.copy(alpha = 0.7f),
                    modifier = Modifier.alpha(0.85f)
                )
            }
        }
    }
}

// Needed for letter spacing literal
private val Int.sp get() = androidx.compose.ui.unit.TextUnit(this.toFloat(), androidx.compose.ui.unit.TextUnitType.Sp)
