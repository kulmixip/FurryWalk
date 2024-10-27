package com.example.composeproject.navigation

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeproject.data.SignUpViewModel
import com.example.composeproject.screens.DetailsScreen
import com.example.composeproject.screens.HomeScreen
import com.example.composeproject.screens.LoginScreen
import com.example.composeproject.screens.MessagesScreen
import com.example.composeproject.screens.SignUpDetailsScreen
import com.example.composeproject.screens.SignUpScreen

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    val signUpViewModel: SignUpViewModel = viewModel()
    val context = LocalContext.current
    var currentRoute by remember { mutableStateOf("login") }

    LaunchedEffect(navController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentRoute = destination.route ?: "login"
        }
    }

    Scaffold(
        bottomBar = {
            Log.d("NavigationApp", "Current route: $currentRoute")
            if (currentRoute != "login" && currentRoute != "signup" && currentRoute != "signupdetails") {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    navController,
                    onLoginSuccess = { status, message ->
                        if (status == 200) {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
            composable("signup") {
                SignUpScreen(
                    navController,
                    signUpViewModel,
                    onSignUpSuccess = {status, message ->
                        if (status == 200) {
                            navController.navigate("signupdetails")
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
            composable("signupdetails") { SignUpDetailsScreen(navController, signUpViewModel) }
            composable("home") { HomeScreen(navController) }
            composable("details") { DetailsScreen(navController) }
            composable("messages") { MessagesScreen(navController) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val items = listOf(
            BottomNavItem("home", "Home", Icons.Default.Home),
            BottomNavItem("details", "Details", Icons.Default.Info)
        )
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = navController.currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}