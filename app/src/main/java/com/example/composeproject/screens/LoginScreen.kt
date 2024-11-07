package com.example.composeproject.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.composeproject.R
import com.example.composeproject.RetrofitInstance
import com.example.composeproject.components.InputField
import com.example.composeproject.data.ConfigViewModel
import com.example.composeproject.data.model.LoginRequest
import com.example.composeproject.data.model.Profile
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    onLoginSuccess: (Int, String, Profile) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Remember state for username and password input fields
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val painter = rememberAsyncImagePainter(model = R.drawable.furrywalk_logo)
        val systemUiController = rememberSystemUiController()
        val color = Color(0xFFFFFFFF)

        // Set the status bar color to match the blue box
        LaunchedEffect(Unit) {
            systemUiController.setStatusBarColor(
                color = color,
                darkIcons = false
            )
        }

        Spacer(Modifier.height(75.dp))
        Image(
            painter = painter,
            contentDescription = "App Logo",
            modifier = Modifier
                .height(120.dp)
                .width(300.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Email: demo@demo.no")
            Text(text = "Password: Demo12345")

            // Use the universal input fields
            InputField(
                label = "Email",
                value = remember { mutableStateOf(username) },
                onValueChange = { username = it },
                leadingIcon = Icons.Default.Person
            )

            Spacer(modifier = Modifier.height(16.dp))

            InputField(
                label = "Password",
                value = remember { mutableStateOf(password) },
                onValueChange = { password = it },
                leadingIcon = Icons.Default.Lock,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            val isLoginEnabled = username.isNotBlank() && password.isNotBlank()

            // Login Button
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    coroutineScope.launch {
                        val loginRequest = LoginRequest(
                            username = username,
                            password = password
                        )
                        try {
                            val response = RetrofitInstance.api.login(loginRequest)
                            val status = response.status
                            val message = response.message
                            val profile = response.profile

                            onLoginSuccess(status, message, profile)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Login Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                            Log.e("LoginError", "Login failed", e)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF2C55)
                ),
                enabled = isLoginEnabled
            ) {
                Text(text = "Login")
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val signUpText = buildAnnotatedString {
                    append("Don't have an account? ")
                    withStyle(style = SpanStyle(color = Color(0xFFFF2C55), fontWeight = FontWeight.Bold)) {
                        append("Sign up")
                    }
                }

                Text(
                    text = signUpText,
                    modifier = Modifier.clickable (onClick = { navController.navigate("signup") }) // Make text clickable
                )

                Spacer(Modifier.height(35.dp))
            }
        }

        Text(text = "This is the Login screen")
    }
}