package com.m3.rajat.piyush.studymatealpha.presentation.navigation

/**
 * Global Navigation Routes for StudyMate
 */
sealed class Screen(val route: String) {
    // Phase 2: Auth
    object Splash : Screen("splash")
    object RoleSelection : Screen("role_selection")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
    
    // Top-Level Module Dashboards (For BottomNav/NavRail)
    object AdminDashboard : Screen("admin_dashboard")
    object StudentDashboard : Screen("student_dashboard")
    object FacultyDashboard : Screen("faculty_dashboard")
    
    // Supplementary Top-Level Nav
    object NoticesFeed : Screen("notices_feed")
    object Profile : Screen("profile")
    
    // Phase 5: User Management
    object AddStudent : Screen("add_student")
    object AddFaculty : Screen("add_faculty")
    object UserDirectory : Screen("user_directory")
    object AddAssignment : Screen("add_assignment")
    
    // Phase 6: Academics & Exams
    object Timetable : Screen("timetable")
    object Grades : Screen("grades")
    object Attendance : Screen("attendance")
    
    // Phase 7: Communications & Operations
    object Chat : Screen("chat")
    object Library : Screen("library")
    
    // Phase 8: Settings & Showcases
    object Settings : Screen("settings")
    object ComponentShowcase : Screen("component_showcase")
}
