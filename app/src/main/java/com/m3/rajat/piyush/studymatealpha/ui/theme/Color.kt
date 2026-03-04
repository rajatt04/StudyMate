package com.m3.rajat.piyush.studymatealpha.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ─── Material 3 Light Color Scheme ───────────────────────────────────
val md_theme_light_primary = Color(0xFF1565C0)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFD1E4FF)
val md_theme_light_onPrimaryContainer = Color(0xFF001D36)
val md_theme_light_secondary = Color(0xFF535F70)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFD7E3F7)
val md_theme_light_onSecondaryContainer = Color(0xFF101C2B)
val md_theme_light_tertiary = Color(0xFF6B5778)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFF2DAFF)
val md_theme_light_onTertiaryContainer = Color(0xFF251432)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFAFAFD)
val md_theme_light_onBackground = Color(0xFF1A1C1E)
val md_theme_light_surface = Color(0xFFFAFAFD)
val md_theme_light_onSurface = Color(0xFF1A1C1E)
val md_theme_light_surfaceVariant = Color(0xFFDFE2EB)
val md_theme_light_onSurfaceVariant = Color(0xFF43474E)
val md_theme_light_outline = Color(0xFF73777F)
val md_theme_light_inverseOnSurface = Color(0xFFF1F0F4)
val md_theme_light_inverseSurface = Color(0xFF2F3033)
val md_theme_light_inversePrimary = Color(0xFFA0CAFF)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF1565C0)
val md_theme_light_outlineVariant = Color(0xFFC3C6CF)
val md_theme_light_scrim = Color(0xFF000000)

// ─── Material 3 Dark Color Scheme (Crafted, not auto-inverted) ───────
val md_theme_dark_primary = Color(0xFFA0CAFF)
val md_theme_dark_onPrimary = Color(0xFF003258)
val md_theme_dark_primaryContainer = Color(0xFF00497D)
val md_theme_dark_onPrimaryContainer = Color(0xFFD1E4FF)
val md_theme_dark_secondary = Color(0xFFBBC7DB)
val md_theme_dark_onSecondary = Color(0xFF253140)
val md_theme_dark_secondaryContainer = Color(0xFF3B4858)
val md_theme_dark_onSecondaryContainer = Color(0xFFD7E3F7)
val md_theme_dark_tertiary = Color(0xFFD6BEE4)
val md_theme_dark_onTertiary = Color(0xFF3B2948)
val md_theme_dark_tertiaryContainer = Color(0xFF533F5F)
val md_theme_dark_onTertiaryContainer = Color(0xFFF2DAFF)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF111318)
val md_theme_dark_onBackground = Color(0xFFE2E2E6)
val md_theme_dark_surface = Color(0xFF111318)
val md_theme_dark_onSurface = Color(0xFFC6C6CA)
val md_theme_dark_surfaceVariant = Color(0xFF43474E)
val md_theme_dark_onSurfaceVariant = Color(0xFFC3C6CF)
val md_theme_dark_outline = Color(0xFF8D9199)
val md_theme_dark_inverseOnSurface = Color(0xFF111318)
val md_theme_dark_inverseSurface = Color(0xFFE2E2E6)
val md_theme_dark_inversePrimary = Color(0xFF1565C0)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFA0CAFF)
val md_theme_dark_outlineVariant = Color(0xFF43474E)
val md_theme_dark_scrim = Color(0xFF000000)

// ─── Surface Container Tonal Hierarchy ───────────────────────────────
object SurfaceTokens {
    // Light
    val containerLowestLight = Color(0xFFFFFFFF)
    val containerLowLight = Color(0xFFF5F5F8)
    val containerLight = Color(0xFFEFEFF2)
    val containerHighLight = Color(0xFFE9E9ED)
    val containerHighestLight = Color(0xFFE3E3E7)
    // Dark (crafted — warmer tones, not pure grey)
    val containerLowestDark = Color(0xFF0D0E13)
    val containerLowDark = Color(0xFF1A1C21)
    val containerDark = Color(0xFF1E2025)
    val containerHighDark = Color(0xFF282A30)
    val containerHighestDark = Color(0xFF33353B)
}

// ─── Role-based Accent Colors ────────────────────────────────────────
object RoleColors {
    // Light mode
    val studentPrimary = Color(0xFF1B6EF3)
    val studentContainer = Color(0xFFD5E3FF)
    val onStudentContainer = Color(0xFF001B3D)
    val facultyPrimary = Color(0xFF006B6A)
    val facultyContainer = Color(0xFF70F7F6)
    val onFacultyContainer = Color(0xFF002020)
    val adminPrimary = Color(0xFF8B5000)
    val adminContainer = Color(0xFFFFDDB3)
    val onAdminContainer = Color(0xFF2C1600)
    val parentPrimary = Color(0xFF6750A4)
    val parentContainer = Color(0xFFE8DEF8)
    val onParentContainer = Color(0xFF21005E)
    // Dark mode variants
    val studentPrimaryDark = Color(0xFFA8C8FF)
    val studentContainerDark = Color(0xFF004A87)
    val facultyPrimaryDark = Color(0xFF4DDADA)
    val facultyContainerDark = Color(0xFF004F4F)
    val adminPrimaryDark = Color(0xFFFFB960)
    val adminContainerDark = Color(0xFF6A3B00)
    val parentPrimaryDark = Color(0xFFCFBCFF)
    val parentContainerDark = Color(0xFF4F378B)
}

// ─── Semantic Colors ─────────────────────────────────────────────────
object SemanticColors {
    val success = Color(0xFF1B8A3C)
    val successContainer = Color(0xFFBAF5C9)
    val onSuccessContainer = Color(0xFF002108)
    val warning = Color(0xFFC5920E)
    val warningContainer = Color(0xFFFFE08B)
    val onWarningContainer = Color(0xFF221B00)
    val info = Color(0xFF0061A4)
    val infoContainer = Color(0xFFD1E4FF)
    val onInfoContainer = Color(0xFF001D36)
    // Dark variants
    val successDark = Color(0xFF7EDB8F)
    val successContainerDark = Color(0xFF005314)
    val warningDark = Color(0xFFE4C54A)
    val warningContainerDark = Color(0xFF534600)
    val infoDark = Color(0xFF9ECAFF)
    val infoContainerDark = Color(0xFF00497D)
}

// ─── Extended Color System (provided via CompositionLocal) ───────────

@Immutable
data class ExtendedColors(
    // Surface containers
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    // Semantic
    val success: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,
    val info: Color,
    val infoContainer: Color,
    val onInfoContainer: Color
)

val LightExtendedColors = ExtendedColors(
    surfaceContainerLowest = SurfaceTokens.containerLowestLight,
    surfaceContainerLow = SurfaceTokens.containerLowLight,
    surfaceContainer = SurfaceTokens.containerLight,
    surfaceContainerHigh = SurfaceTokens.containerHighLight,
    surfaceContainerHighest = SurfaceTokens.containerHighestLight,
    success = SemanticColors.success,
    successContainer = SemanticColors.successContainer,
    onSuccessContainer = SemanticColors.onSuccessContainer,
    warning = SemanticColors.warning,
    warningContainer = SemanticColors.warningContainer,
    onWarningContainer = SemanticColors.onWarningContainer,
    info = SemanticColors.info,
    infoContainer = SemanticColors.infoContainer,
    onInfoContainer = SemanticColors.onInfoContainer
)

val DarkExtendedColors = ExtendedColors(
    surfaceContainerLowest = SurfaceTokens.containerLowestDark,
    surfaceContainerLow = SurfaceTokens.containerLowDark,
    surfaceContainer = SurfaceTokens.containerDark,
    surfaceContainerHigh = SurfaceTokens.containerHighDark,
    surfaceContainerHighest = SurfaceTokens.containerHighestDark,
    success = SemanticColors.successDark,
    successContainer = SemanticColors.successContainerDark,
    onSuccessContainer = Color(0xFFBAF5C9),
    warning = SemanticColors.warningDark,
    warningContainer = SemanticColors.warningContainerDark,
    onWarningContainer = Color(0xFFFFE08B),
    info = SemanticColors.infoDark,
    infoContainer = SemanticColors.infoContainerDark,
    onInfoContainer = Color(0xFFD1E4FF)
)

val LocalExtendedColors = staticCompositionLocalOf { LightExtendedColors }
