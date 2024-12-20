package com.example.composeproject.navigation

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
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
import com.example.composeproject.data.ConfigViewModel
import com.example.composeproject.data.SignUpViewModel
import com.example.composeproject.screens.AllMessagesScreen
import com.example.composeproject.screens.DogProfile
import com.example.composeproject.screens.HomeScreen
import com.example.composeproject.screens.LoginScreen
import com.example.composeproject.screens.MessagesScreen
import com.example.composeproject.screens.ProfileScreen
import com.example.composeproject.screens.SignUpDetailsScreen
import com.example.composeproject.screens.SignUpScreen

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    val signUpViewModel: SignUpViewModel = viewModel()
    val configViewModel: ConfigViewModel = viewModel()
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
            startDestination = "login",
            Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(
                    navController,
                    onLoginSuccess = { status, message, profile, dogs, conversations ->
                        if (status == 200) {
                            configViewModel.id = profile.id
                            configViewModel.firstName = profile.firstName
                            configViewModel.lastName = profile.lastName
                            configViewModel.email = profile.email
                            configViewModel.phone = profile.phone
                            configViewModel.age = profile.age
                            configViewModel.rating = profile.rating
                            configViewModel.location = profile.location
                            configViewModel.image = profile.image

                            configViewModel.updateDogs(dogs)
                            configViewModel.updateConversations(conversations)

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
            composable("signupdetails") {
                SignUpDetailsScreen(
                    navController,
                    signUpViewModel,
                    onSignUpDetailsSuccess = {status, message, profile, dogs, conversations ->
                        if(status == 200){
                            configViewModel.id = profile.id
                            configViewModel.firstName = profile.firstName
                            configViewModel.lastName = profile.lastName
                            configViewModel.email = profile.email
                            configViewModel.phone = profile.phone
                            configViewModel.age = profile.age
                            configViewModel.rating = profile.rating
                            configViewModel.location = profile.location
                            configViewModel.image = profile.image

                            configViewModel.updateDogs(dogs)
                            configViewModel.updateConversations(conversations)

                            navController.navigate("home")
                        } else {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
            composable("home") { HomeScreen(navController, configViewModel) }
            composable("messages/{conversationId}") { backStackEntry ->
                val conversationId = backStackEntry.arguments?.getString("conversationId")
                MessagesScreen(navController, configViewModel, conversationId)
            }


            composable("allMessages") {
                AllMessagesScreen(
                    navController,
                    configViewModel
                ) }
            composable("profile") {
                ProfileScreen(
                    navController,
                    configViewModel
                )
            }
            composable("dogProfile") {
                DogProfile(
                    navController,
                    configViewModel
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val items = listOf(
            BottomNavItem("home", "Home", Icons.Default.Home),
            BottomNavItem("allMessages", "Messages", Icons.Default.MailOutline),
            BottomNavItem("profile", "Profile", Icons.Default.Person)
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