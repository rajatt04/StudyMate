package com.m3.rajat.piyush.studymatealpha.presentation.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.m3.rajat.piyush.studymatealpha.ui.theme.StudyMateMotion
import com.m3.rajat.piyush.studymatealpha.presentation.academics.AttendanceScreen
import com.m3.rajat.piyush.studymatealpha.presentation.academics.GradesScreen
import com.m3.rajat.piyush.studymatealpha.presentation.academics.TimetableScreen
import com.m3.rajat.piyush.studymatealpha.presentation.assignment.AddAssignmentScreen
import com.m3.rajat.piyush.studymatealpha.presentation.auth.LoginScreen
import com.m3.rajat.piyush.studymatealpha.presentation.auth.OnboardingScreen
import com.m3.rajat.piyush.studymatealpha.presentation.auth.RegisterScreen
import com.m3.rajat.piyush.studymatealpha.presentation.auth.RoleSelectionScreen
import com.m3.rajat.piyush.studymatealpha.presentation.auth.SplashScreen
import com.m3.rajat.piyush.studymatealpha.core.datastore.UserPreferences
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
import com.m3.rajat.piyush.studymatealpha.presentation.auth.ForgotPasswordScreen
import com.m3.rajat.piyush.studymatealpha.presentation.admin.ClassManagementScreen
import com.m3.rajat.piyush.studymatealpha.presentation.admin.FeeTrackingScreen
import com.m3.rajat.piyush.studymatealpha.presentation.admin.SystemSettingsScreen
import com.m3.rajat.piyush.studymatealpha.presentation.admin.DatabaseBackupScreen
import com.m3.rajat.piyush.studymatealpha.presentation.faculty.ClassPerformanceScreen
import com.m3.rajat.piyush.studymatealpha.presentation.parent.TrackWardScreen
import com.m3.rajat.piyush.studymatealpha.presentation.parent.ParentAttendanceScreen
import com.m3.rajat.piyush.studymatealpha.presentation.parent.ParentFeesScreen
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

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

    // Track current user role to resolve shared routes (Settings, Notices, Grades)
    var currentUserRole by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            Screen.AdminDashboard.route -> currentUserRole = "ADMIN"
            Screen.StudentDashboard.route -> currentUserRole = "STUDENT"
            Screen.FacultyDashboard.route -> currentUserRole = "FACULTY"
            Screen.ParentDashboard.route -> currentUserRole = "PARENT"
        }
    }

    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val activeBottomNav = getActiveBottomNav(currentRoute, currentUserRole)
    val showBottomBar = isCompact && activeBottomNav != null
    val showNavRail = !isCompact && activeBottomNav != null

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                StudyMateBottomBar(
                    destinations = activeBottomNav!!,
                    onNavigateToDestination = { route -> navigateToTopLevelDestination(navController, currentUserRole, route) },
                    currentRoute = currentRoute
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (showNavRail) {
                StudyMateNavRail(
                    destinations = activeBottomNav!!,
                    onNavigateToDestination = { route -> navigateToTopLevelDestination(navController, currentUserRole, route) },
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .shadow(elevation = 16.dp, shape = RoundedCornerShape(32.dp), spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(32.dp)
                )
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            destinations.forEach { destination ->
                val selected = currentRoute == destination.route
                
                val iconColor by animateColorAsState(
                    targetValue = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    animationSpec = StudyMateMotion.tweenStandard(),
                    label = "iconColor"
                )
                
                val indicatorColor by animateColorAsState(
                    targetValue = if (selected) MaterialTheme.colorScheme.primary else androidx.compose.ui.graphics.Color.Transparent,
                    animationSpec = StudyMateMotion.tweenStandard(),
                    label = "indicatorColor"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onNavigateToDestination(destination.route) }
                        )
                        .background(indicatorColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.label,
                        tint = iconColor,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
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
        modifier = modifier,
        enterTransition = { StudyMateMotion.screenEnter },
        exitTransition = { StudyMateMotion.screenExit },
        popEnterTransition = { StudyMateMotion.screenPopEnter },
        popExitTransition = { StudyMateMotion.screenPopExit }
    ) {
        // ==================== AUTH ====================
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateNext = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinished = {
                    navController.navigate(Screen.RoleSelection.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
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
                } else null,
                onNavigateToForgotPassword = {
                    navController.navigate(Screen.ForgotPassword.route)
                }
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
        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== ADMIN ====================
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(
                onNavigateToAddStudent = { navController.navigate(Screen.AddStudent.route) },
                onNavigateToAddFaculty = { navController.navigate(Screen.AddFaculty.route) },
                onNavigateToAddParent = { navController.navigate(Screen.AddParent.route) },
                onNavigateToClassManagement = { navController.navigate(Screen.ClassManagement.route) },
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
        composable(Screen.ClassManagement.route) {
            ClassManagementScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.FeeTracking.route) {
            FeeTrackingScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.SystemSettings.route) {
            SystemSettingsScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.DatabaseBackup.route) {
            DatabaseBackupScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== FACULTY ====================
        composable(Screen.FacultyDashboard.route) {
            FacultyDashboardScreen(
                onNavigateToAddAssignment = { navController.navigate(Screen.AddAssignment.route) },
                onNavigateToMarkAttendance = { navController.navigate(Screen.MarkAttendance.route) },
                onNavigateToEnterMarks = { navController.navigate(Screen.EnterMarks.route) },
                onNavigateToClassPerformance = { navController.navigate(Screen.ClassPerformance.route) },
                onNavigateToNotices = { navController.navigate(Screen.NoticesFeed.route) }
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
        composable(Screen.ClassPerformance.route) {
            ClassPerformanceScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== STUDENT ====================
        composable(Screen.StudentDashboard.route) {
            StudentDashboardScreen(
                onNavigateToFeeStatus = { navController.navigate(Screen.FeeStatus.route) },
                onNavigateToAssignmentSubmission = { navController.navigate(Screen.AssignmentSubmission.route) },
                onNavigateToNotices = { navController.navigate(Screen.NoticesFeed.route) }
            )
        }
        composable(Screen.AssignmentSubmission.route) {
            AssignmentSubmissionScreen(onNavigateBack = { navController.popBackStack() })
        }

        // ==================== PARENT ====================
        composable(Screen.ParentDashboard.route) {
            ParentDashboardScreen(
                onNavigateToTrackWard = { navController.navigate(Screen.TrackWard.route) },
                onNavigateToAttendance = { navController.navigate(Screen.ParentAttendance.route) },
                onNavigateToMarks = { navController.navigate(Screen.Grades.route) },
                onNavigateToFees = { navController.navigate(Screen.ParentFees.route) },
                onNavigateToMessages = { navController.navigate(Screen.DirectMessage.route) }
            )
        }
        composable(Screen.TrackWard.route) {
            TrackWardScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.ParentAttendance.route) {
            ParentAttendanceScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.ParentFees.route) {
            ParentFeesScreen(onNavigateBack = { navController.popBackStack() })
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
                onNavigateToComponents = { navController.navigate(Screen.ComponentShowcase.route) },
                onLogout = {
                    navController.navigate(Screen.RoleSelection.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToComponents = { navController.navigate(Screen.ComponentShowcase.route) },
                onLogout = {
                    navController.navigate(Screen.RoleSelection.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.ComponentShowcase.route) {
            ComponentShowcaseScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}

private fun getActiveBottomNav(currentRoute: String?, userRole: String?): List<BottomNavItem>? {
    if (currentRoute == null || userRole == null) return null
    // Auth and pre-auth routes should never show bottom nav
    val noNavRoutes = setOf(
        Screen.Splash.route, Screen.Onboarding.route, Screen.RoleSelection.route,
        Screen.Login.route, Screen.Register.route,
        Screen.ForgotPassword.route, Screen.ComponentShowcase.route
    )
    if (currentRoute in noNavRoutes || currentRoute.startsWith(Screen.Login.route)) return null
    // Return the nav bar for the current user's role
    return when (userRole) {
        "ADMIN" -> adminBottomNav
        "STUDENT" -> studentBottomNav
        "FACULTY" -> facultyBottomNav
        "PARENT" -> parentBottomNav
        else -> null
    }
}

private fun getDashboardRoute(userRole: String?): String {
    return when (userRole) {
        "ADMIN" -> Screen.AdminDashboard.route
        "STUDENT" -> Screen.StudentDashboard.route
        "FACULTY" -> Screen.FacultyDashboard.route
        "PARENT" -> Screen.ParentDashboard.route
        else -> Screen.StudentDashboard.route
    }
}

private fun navigateToTopLevelDestination(navController: NavHostController, userRole: String?, route: String) {
    val dashboardRoute = getDashboardRoute(userRole)
    navController.navigate(route) {
        popUpTo(dashboardRoute) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
