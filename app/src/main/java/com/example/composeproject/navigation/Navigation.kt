package com.example.composeproject.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeproject.screens.DetailsScreen
import com.example.composeproject.screens.HomeScreen
import com.example.composeproject.screens.LoginScreen

@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                navController,
                onLoginSuccess = { status, message ->
                    if (status == 200) {
                        navController.navigate("home")
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
        composable("home") { HomeScreen(navController) }
        composable("details") { DetailsScreen(navController) }
    }
}
