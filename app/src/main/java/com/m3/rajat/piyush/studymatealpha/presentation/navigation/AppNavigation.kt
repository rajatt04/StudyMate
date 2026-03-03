package com.m3.rajat.piyush.studymatealpha.presentation.navigation

/**
 * Global Navigation Routes for StudyMate
 */
sealed class Screen(val route: String) {
    // Phase 2: Auth
    object Splash : Screen("splash")
    object RoleSelection : Screen("role_selection")
    object Login : Screen("login")
    object ForgotPassword : Screen("forgot_password")
    
    // Top-Level Module Dashboards (For BottomNav/NavRail)
    object AdminDashboard : Screen("admin_dashboard")
    object StudentDashboard : Screen("student_dashboard")
    object FacultyDashboard : Screen("faculty_dashboard")
    
    // Supplementary Top-Level Nav
    object NoticesFeed : Screen("notices_feed")
    object Profile : Screen("profile")
}
