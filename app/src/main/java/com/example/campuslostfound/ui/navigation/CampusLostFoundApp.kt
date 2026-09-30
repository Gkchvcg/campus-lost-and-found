package com.example.campuslostfound.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.campuslostfound.ui.screens.browse.BrowseScreen
import com.example.campuslostfound.ui.screens.home.HomeScreen
import com.example.campuslostfound.ui.screens.profile.ProfileScreen

@Composable
fun CampusLostFoundApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    
    val items = listOf(
        Screen.Home,
        Screen.Browse,
        Screen.Profile
    )
    
    Scaffold(
        modifier = modifier,
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val isBottomBarVisible = items.any { it.route == currentDestination?.route }
            
            if (isBottomBarVisible) {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                when(screen.route) {
                                    Screen.Home.route -> Icon(Icons.Filled.Home, contentDescription = "Home")
                                    Screen.Browse.route -> Icon(Icons.Filled.Search, contentDescription = "Browse")
                                    Screen.Profile.route -> Icon(Icons.Filled.Person, contentDescription = "Profile")
                                }
                            },
                            label = { Text(screen.title) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToReport = { type ->
                        navController.navigate("report/$type")
                    },
                    onNavigateToDetails = { id ->
                        navController.navigate("details/$id")
                    }
                )
            }
            composable(Screen.Browse.route) {
                BrowseScreen(
                    onNavigateToDetails = { id ->
                        navController.navigate("details/$id")
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onNavigateToDetails = { id ->
                        navController.navigate("details/$id")
                    }
                )
            }
            composable("report/{type}") { backStackEntry ->
                val type = backStackEntry.arguments?.getString("type") ?: "LOST"
                com.example.campuslostfound.ui.screens.report.ReportScreen(type = type, onBack = { navController.popBackStack() })
            }
            composable("details/{id}") { backStackEntry ->
                val idStr = backStackEntry.arguments?.getString("id") ?: "0"
                val id = idStr.toIntOrNull() ?: 0
                com.example.campuslostfound.ui.screens.details.DetailsScreen(id = id, onBack = { navController.popBackStack() })
            }
        }
    }
}

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Browse : Screen("browse", "Browse")
    object Profile : Screen("profile", "Profile")
}
