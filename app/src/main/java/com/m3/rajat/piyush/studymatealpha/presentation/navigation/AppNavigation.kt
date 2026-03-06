package com.m3.rajat.piyush.studymatealpha.presentation.navigation

/**
 * Global Navigation Routes for StudyMate.
 * Organized by feature area for scalability.
 */
sealed class Screen(val route: String) {
    // Auth
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object RoleSelection : Screen("role_selection")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")

    // Dashboards
    object AdminDashboard : Screen("admin_dashboard")
    object StudentDashboard : Screen("student_dashboard")
    object FacultyDashboard : Screen("faculty_dashboard")
    object ParentDashboard : Screen("parent_dashboard")

    // Supplementary Top-Level Nav
    object NoticesFeed : Screen("notices_feed")
    object Profile : Screen("profile")

    // User Management (Admin)
    object AddStudent : Screen("add_student")
    object AddFaculty : Screen("add_faculty")
    object AddParent : Screen("add_parent")
    object UserDirectory : Screen("user_directory")

    // Faculty Features
    object AddAssignment : Screen("add_assignment")
    object MarkAttendance : Screen("mark_attendance")
    object EnterMarks : Screen("enter_marks")
    object ClassPerformance : Screen("class_performance")

    // Academics
    object Timetable : Screen("timetable")
    object Grades : Screen("grades")
    object Attendance : Screen("attendance")

    // Student Features
    object AssignmentSubmission : Screen("assignment_submission")
    object FeeStatus : Screen("fee_status")

    // Admin Features
    object ClassManagement : Screen("class_management")
    object FeeTracking : Screen("fee_tracking")
    object SystemSettings : Screen("system_settings")
    object DatabaseBackup : Screen("database_backup")

    // Parent Features
    object TrackWard : Screen("track_ward")
    object ParentAttendance : Screen("parent_attendance")
    object ParentFees : Screen("parent_fees")
    object DirectMessage : Screen("direct_message")

    // Communications & Operations
    object Chat : Screen("chat")
    object Library : Screen("library")

    // Settings & Showcases
    object Settings : Screen("settings")
    object ComponentShowcase : Screen("component_showcase")
}
