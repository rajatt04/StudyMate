package com.m3.rajat.piyush.studymatealpha.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.m3.rajat.piyush.studymatealpha.presentation.academics.AttendanceScreen
import com.m3.rajat.piyush.studymatealpha.presentation.academics.GradesScreen
import com.m3.rajat.piyush.studymatealpha.presentation.academics.TimetableScreen
import com.m3.rajat.piyush.studymatealpha.presentation.assignment.AddAssignmentScreen
import com.m3.rajat.piyush.studymatealpha.presentation.auth.LoginScreen
import com.m3.rajat.piyush.studymatealpha.presentation.auth.RegisterScreen
import com.m3.rajat.piyush.studymatealpha.presentation.auth.RoleSelectionScreen
import com.m3.rajat.piyush.studymatealpha.presentation.auth.SplashScreen
import com.m3.rajat.piyush.studymatealpha.presentation.chat.ChatScreen
import com.m3.rajat.piyush.studymatealpha.presentation.common.EmptyStateScreen
import com.m3.rajat.piyush.studymatealpha.presentation.dashboard.AdminDashboardScreen
import com.m3.rajat.piyush.studymatealpha.presentation.dashboard.FacultyDashboardScreen
import com.m3.rajat.piyush.studymatealpha.presentation.dashboard.ParentDashboardScreen
import com.m3.rajat.piyush.studymatealpha.presentation.dashboard.StudentDashboardScreen
import com.m3.rajat.piyush.studymatealpha.presentation.faculty.EnterMarksScreen
import com.m3.rajat.piyush.studymatealpha.presentation.faculty.MarkAttendanceScreen
import com.m3.rajat.piyush.studymatealpha.presentation.library.LibraryScreen
import com.m3.rajat.piyush.studymatealpha.presentation.notices.NoticesFeedScreen
import com.m3.rajat.piyush.studymatealpha.presentation.settings.ComponentShowcaseScreen
import com.m3.rajat.piyush.studymatealpha.presentation.settings.SettingsScreen
import com.m3.rajat.piyush.studymatealpha.presentation.student.AssignmentSubmissionScreen
import com.m3.rajat.piyush.studymatealpha.presentation.student.FeeStatusScreen
import com.m3.rajat.piyush.studymatealpha.presentation.user.AddFacultyScreen
import com.m3.rajat.piyush.studymatealpha.presentation.user.AddParentScreen
import com.m3.rajat.piyush.studymatealpha.presentation.user.AddStudentScreen
import com.m3.rajat.piyush.studymatealpha.presentation.user.UserDirectoryScreen

/**
 * Top-level navigation items for role-specific bottom nav
 */
data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val adminBottomNav = listOf(
    BottomNavItem(Screen.AdminDashboard.route, Icons.Default.Home, "Home"),
    BottomNavItem(Screen.NoticesFeed.route, Icons.Default.Campaign, "Notices"),
    BottomNavItem(Screen.UserDirectory.route, Icons.Default.People, "Users"),
    BottomNavItem(Screen.Settings.route, Icons.Default.Settings, "Settings")
)

val studentBottomNav = listOf(
    BottomNavItem(Screen.StudentDashboard.route, Icons.Default.Home, "Home"),
    BottomNavItem(Screen.Timetable.route, Icons.Default.School, "Timetable"),
    BottomNavItem(Screen.Grades.route, Icons.AutoMirrored.Filled.Assignment, "Marks"),
    BottomNavItem(Screen.Settings.route, Icons.Default.Person, "Profile")
)

val facultyBottomNav = listOf(
    BottomNavItem(Screen.FacultyDashboard.route, Icons.Default.Home, "Home"),
    BottomNavItem(Screen.NoticesFeed.route, Icons.Default.Campaign, "Notices"),
    BottomNavItem(Screen.AddAssignment.route, Icons.AutoMirrored.Filled.Assignment, "Assign"),
    BottomNavItem(Screen.Settings.route, Icons.Default.Settings, "Settings")
)

val parentBottomNav = listOf(
    BottomNavItem(Screen.ParentDashboard.route, Icons.Default.Home, "Home"),
    BottomNavItem(Screen.Grades.route, Icons.Default.School, "Academics"),
    BottomNavItem(Screen.FeeStatus.route, Icons.Default.AttachMoney, "Fees"),
    BottomNavItem(Screen.DirectMessage.route, Icons.AutoMirrored.Filled.Message, "Messages")
)

@Composable
fun StudyMateApp(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val activeBottomNav = getActiveBottomNav(currentRoute)
    val showBottomBar = isCompact && activeBottomNav != null
    val showNavRail = !isCompact && activeBottomNav != null

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                StudyMateBottomBar(
                    destinations = activeBottomNav!!,
                    onNavigateToDestination = { route -> navigateToTopLevelDestination(navController, route) },
                    currentRoute = currentRoute
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { _ ->
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (showNavRail) {
                StudyMateNavRail(
                    destinations = activeBottomNav!!,
                    onNavigateToDestination = { route -> navigateToTopLevelDestination(navController, route) },
                    currentRoute = currentRoute
                )
            }

            Box(modifier = Modifier.weight(1f)) {
                StudyMateNavHost(navController = navController)
            }
        }
    }
}

@Composable
private fun StudyMateBottomBar(
    destinations: List<BottomNavItem>,
    onNavigateToDestination: (String) -> Unit,
    currentRoute: String?
) {
    NavigationBar {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = { onNavigateToDestination(destination.route) },
                icon = { Icon(imageVector = destination.icon, contentDescription = destination.label) },
                label = { Text(destination.label) }
            )
        }
    }
}

@Composable
private fun StudyMateNavRail(
    destinations: List<BottomNavItem>,
    onNavigateToDestination: (String) -> Unit,
    currentRoute: String?
) {
    NavigationRail {
        destinations.forEach { destination ->
            NavigationRailItem(
                selected = currentRoute == destination.route,
                onClick = { onNavigateToDestination(destination.route) },
                icon = { Icon(imageVector = destination.icon, contentDescription = destination.label) },
                label = { Text(destination.label) }
            )
        }
    }
}

@Composable
fun StudyMateNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.Splash.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        // ==================== AUTH ====================
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate(Screen.RoleSelection.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.RoleSelection.route) {
            RoleSelectionScreen(
                onRoleSelected = { role ->
                    navController.navigate("${Screen.Login.route}/$role")
                }
            )
        }
        composable("${Screen.Login.route}/{role}") { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role")?.uppercase() ?: "STUDENT"
            LoginScreen(
                role = role,
                onLoginSuccess = {
                    val dash = when (role) {
                        "ADMIN" -> Screen.AdminDashboard.route
                        "FACULTY" -> Screen.FacultyDashboard.route
                        "STUDENT" -> Screen.StudentDashboard.route
                        "PARENT" -> Screen.ParentDashboard.route
                        else -> Screen.StudentDashboard.route
                    }
                    navController.navigate(dash) {
                        popUpTo(Screen.RoleSelection.route) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() },
                onNavigateToRegister = if (role == "ADMIN") {
                    { navController.navigate(Screen.Register.route) }
                } else null
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("${Screen.Login.route}/ADMIN") {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() },
                onNavigateToLogin = {
                    navController.navigate("${Screen.Login.route}/ADMIN") {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        // ==================== ADMIN ====================
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(
                onNavigateToAddStudent = { navController.navigate(Screen.AddStudent.route) },
                onNavigateToAddFaculty = { navController.navigate(Screen.AddFaculty.route) },
                onNavigateToAddParent = { navController.navigate(Screen.AddParent.route) },
                onNavigateToNotices = { navController.navigate(Screen.NoticesFeed.route) },
                onNavigateToLibrary = { navController.navigate(Screen.Library.route) }
            )
        }
        composable(Screen.AddStudent.route) {
            AddStudentScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.AddFaculty.route) {
            AddFacultyScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.AddParent.route) {
            AddParentScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.UserDirectory.route) {
            UserDirectoryScreen()
        }

        // ==================== FACULTY ====================
        composable(Screen.FacultyDashboard.route) {
            FacultyDashboardScreen(
                onNavigateToAddAssignment = { navController.navigate(Screen.AddAssignment.route) }
            )
        }
        composable(Screen.AddAssignment.route) {
            AddAssignmentScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.MarkAttendance.route) {
            MarkAttendanceScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.EnterMarks.route) {
            EnterMarksScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== STUDENT ====================
        composable(Screen.StudentDashboard.route) {
            StudentDashboardScreen()
        }
        composable(Screen.AssignmentSubmission.route) {
            AssignmentSubmissionScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== PARENT ====================
        composable(Screen.ParentDashboard.route) {
            ParentDashboardScreen()
        }

        // ==================== SHARED ====================
        composable(Screen.Timetable.route) {
            TimetableScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Grades.route) {
            GradesScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Attendance.route) {
            AttendanceScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.FeeStatus.route) {
            FeeStatusScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.NoticesFeed.route) {
            NoticesFeedScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Chat.route) {
            ChatScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.DirectMessage.route) {
            ChatScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Library.route) {
            LibraryScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.Profile.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToComponents = { navController.navigate(Screen.ComponentShowcase.route) }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToComponents = { navController.navigate(Screen.ComponentShowcase.route) }
            )
        }
        composable(Screen.ComponentShowcase.route) {
            ComponentShowcaseScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}

private fun getActiveBottomNav(currentRoute: String?): List<BottomNavItem>? {
    if (currentRoute == null) return null
    return when {
        adminBottomNav.any { it.route == currentRoute } -> adminBottomNav
        studentBottomNav.any { it.route == currentRoute } -> studentBottomNav
        facultyBottomNav.any { it.route == currentRoute } -> facultyBottomNav
        parentBottomNav.any { it.route == currentRoute } -> parentBottomNav
        else -> null
    }
}

private fun navigateToTopLevelDestination(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
