package com.m3.rajat.piyush.studymatealpha.ui.theme

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

/**
 * Centralized motion specs for the entire app.
 *
 * Design principles:
 * - Emphasized (hero transitions): 500ms
 * - Standard (screen transitions): 300ms
 * - De-emphasized (micro-interactions): 150ms
 *
 * All specs follow Material 3 motion guidelines:
 * https://m3.material.io/styles/motion/overview
 */
object StudyMateMotion {

    // ─── Duration Tokens ─────────────────────────────────────────────
    const val DURATION_SHORT = 150
    const val DURATION_MEDIUM = 300
    const val DURATION_LONG = 500
    const val DURATION_EXTRA_LONG = 700

    // ─── Stagger Delay ───────────────────────────────────────────────
    const val STAGGER_DELAY = 60

    // ─── Spring Specs ────────────────────────────────────────────────

    /** Snappy spring for press feedback, icon toggles */
    val springSnappy = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium
    )

    /** Standard spring for card lifts, layout shifts */
    val springStandard = spring<Float>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow
    )

    /** Gentle spring for expansions, content size changes */
    val springGentle = spring<Float>(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow
    )

    /** Expressive spring for hero moments (splash, role select) */
    val springExpressive = spring<Float>(
        dampingRatio = 0.6f,
        stiffness = 200f
    )

    // ─── Tween Specs ─────────────────────────────────────────────────

    /** Standard eased tween for color/alpha transitions */
    fun <T> tweenStandard(durationMillis: Int = DURATION_MEDIUM) = tween<T>(
        durationMillis = durationMillis,
        easing = FastOutSlowInEasing
    )

    /** Emphasized decelerate for enter transitions */
    fun <T> tweenEnter(durationMillis: Int = DURATION_MEDIUM) = tween<T>(
        durationMillis = durationMillis,
        easing = LinearOutSlowInEasing
    )

    /** Accelerate for exit transitions */
    fun <T> tweenExit(durationMillis: Int = DURATION_SHORT) = tween<T>(
        durationMillis = durationMillis,
        easing = FastOutLinearInEasing
    )

    // ─── Screen Transitions (Fade Through — M3 recommended) ─────────

    val screenEnter: EnterTransition =
        fadeIn(animationSpec = tweenEnter(DURATION_MEDIUM)) +
        scaleIn(
            initialScale = 0.92f,
            animationSpec = tweenEnter(DURATION_MEDIUM)
        )

    val screenExit: ExitTransition =
        fadeOut(animationSpec = tweenExit(DURATION_SHORT)) +
        scaleOut(
            targetScale = 1.04f,
            animationSpec = tweenExit(DURATION_SHORT)
        )

    val screenPopEnter: EnterTransition =
        fadeIn(animationSpec = tweenEnter(DURATION_MEDIUM)) +
        scaleIn(
            initialScale = 1.04f,
            animationSpec = tweenEnter(DURATION_MEDIUM)
        )

    val screenPopExit: ExitTransition =
        fadeOut(animationSpec = tweenExit(DURATION_SHORT)) +
        scaleOut(
            targetScale = 0.92f,
            animationSpec = tweenExit(DURATION_SHORT)
        )

    // ─── Shared-Axis Horizontal ──────────────────────────────────────

    val sharedAxisEnterForward: EnterTransition =
        fadeIn(animationSpec = tweenEnter(DURATION_MEDIUM)) +
        slideInHorizontally(
            initialOffsetX = { fullWidth -> (fullWidth * 0.15f).toInt() },
            animationSpec = tweenEnter(DURATION_MEDIUM)
        )

    val sharedAxisExitForward: ExitTransition =
        fadeOut(animationSpec = tweenExit(DURATION_SHORT)) +
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> -(fullWidth * 0.15f).toInt() },
            animationSpec = tweenExit(DURATION_SHORT)
        )

    val sharedAxisEnterBackward: EnterTransition =
        fadeIn(animationSpec = tweenEnter(DURATION_MEDIUM)) +
        slideInHorizontally(
            initialOffsetX = { fullWidth -> -(fullWidth * 0.15f).toInt() },
            animationSpec = tweenEnter(DURATION_MEDIUM)
        )

    val sharedAxisExitBackward: ExitTransition =
        fadeOut(animationSpec = tweenExit(DURATION_SHORT)) +
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> (fullWidth * 0.15f).toInt() },
            animationSpec = tweenExit(DURATION_SHORT)
        )
}
