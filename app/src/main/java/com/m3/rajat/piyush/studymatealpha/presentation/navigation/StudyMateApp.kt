package com.m3.rajat.piyush.studymatealpha.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.m3.rajat.piyush.studymatealpha.presentation.auth.LoginScreen
import com.m3.rajat.piyush.studymatealpha.presentation.auth.RoleSelectionScreen
import com.m3.rajat.piyush.studymatealpha.presentation.auth.SplashScreen
import com.m3.rajat.piyush.studymatealpha.presentation.dashboard.AdminDashboardScreen
import com.m3.rajat.piyush.studymatealpha.presentation.dashboard.FacultyDashboardScreen
import com.m3.rajat.piyush.studymatealpha.presentation.dashboard.StudentDashboardScreen

/**
 * Top-level navigation items for the application
 */
data class TopLevelDestination(
    val route: String,
    val selectedIconId: Int, // Ideally references an R.drawable or ImageVector
    val unselectedIconId: Int,
    val iconTextId: String
)

val bottomNavDestinations = listOf(
    TopLevelDestination(
        route = Screen.AdminDashboard.route,
        selectedIconId = android.R.drawable.ic_menu_today,
        unselectedIconId = android.R.drawable.ic_menu_today,
        iconTextId = "Home"
    ),
    TopLevelDestination(
        route = Screen.NoticesFeed.route,
        selectedIconId = android.R.drawable.ic_menu_info_details,
        unselectedIconId = android.R.drawable.ic_menu_info_details,
        iconTextId = "Notices"
    ),
    TopLevelDestination(
        route = Screen.Profile.route,
        selectedIconId = android.R.drawable.ic_menu_manage,
        unselectedIconId = android.R.drawable.ic_menu_manage,
        iconTextId = "Profile"
    )
)

@Composable
fun StudyMateApp(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Determine if we should show standard bottom bar or nav rail based on window size
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val showBottomBar = isCompact && isTopLevelDestination(currentRoute)
    val showNavRail = !isCompact && isTopLevelDestination(currentRoute)

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                StudyMateBottomBar(
                    destinations = bottomNavDestinations,
                    onNavigateToDestination = { route -> navigateToTopLevelDestination(navController, route) },
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
                    destinations = bottomNavDestinations,
                    onNavigateToDestination = { route -> navigateToTopLevelDestination(navController, route) },
                    currentRoute = currentRoute
                )
            }

            // Main Content Area
            Box(modifier = Modifier.weight(1f)) {
                StudyMateNavHost(navController = navController)
            }
        }
    }
}

@Composable
private fun StudyMateBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (String) -> Unit,
    currentRoute: String?
) {
    NavigationBar {
        destinations.forEach { destination ->
            val selected = currentRoute == destination.route
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = if (selected) destination.selectedIconId else destination.unselectedIconId),
                        contentDescription = destination.iconTextId
                    )
                },
                label = { Text(destination.iconTextId) }
            )
        }
    }
}

@Composable
private fun StudyMateNavRail(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (String) -> Unit,
    currentRoute: String?
) {
    NavigationRail {
        destinations.forEach { destination ->
            val selected = currentRoute == destination.route
            NavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = if (selected) destination.selectedIconId else destination.unselectedIconId),
                        contentDescription = destination.iconTextId
                    )
                },
                label = { Text(destination.iconTextId) }
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
                    // Navigate to Login passing the selected role
                    navController.navigate("${Screen.Login.route}/$role")
                }
            )
        }
        composable("${Screen.Login.route}/{role}") { backStackEntry ->
            val role = backStackEntry.arguments?.getString("role")?.uppercase() ?: "STUDENT"
            LoginScreen(
                role = role,
                onLoginSuccess = {
                    // Route to correct dashboard based on role
                    val dashboardRoute = when(role) {
                        "ADMIN" -> Screen.AdminDashboard.route
                        "FACULTY" -> Screen.FacultyDashboard.route
                        "STUDENT" -> Screen.StudentDashboard.route
                        else -> Screen.StudentDashboard.route
                    }
                    navController.navigate(dashboardRoute) {
                        popUpTo(Screen.RoleSelection.route) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen()
        }
        composable(Screen.FacultyDashboard.route) {
            FacultyDashboardScreen()
        }
        composable(Screen.StudentDashboard.route) {
            StudentDashboardScreen()
        }
    }
}

private fun isTopLevelDestination(route: String?): Boolean {
    return bottomNavDestinations.any { it.route == route }
}

private fun navigateToTopLevelDestination(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
