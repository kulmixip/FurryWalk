package com.example.composeproject.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.composeproject.RetrofitInstance
import com.example.composeproject.components.InputField
import com.example.composeproject.data.SignUpViewModel
import com.example.composeproject.data.model.SignUpRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel,
    onSignUpSuccess: (Int, String) -> Unit
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val systemUiController = rememberSystemUiController()
    val color = Color(0xFFFF2C55)
    val loading = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = false
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Top Box as a header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color)
                .statusBarsPadding()
        ) {
            IconButton(
                onClick = { navController.navigate("login") },
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back to login screen",
                    tint = Color.White
                )
            }

            Text(
                text = "Create Your Account",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 16.dp)
            )
        }

        // Column content for the input fields
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                label = "Email",
                value = remember { mutableStateOf(viewModel.email) },
                onValueChange = { viewModel.email = it },
                leadingIcon = Icons.Default.Email
            )
            Spacer(modifier = Modifier.height(24.dp))
            InputField(
                label = "Password",
                value = remember { mutableStateOf(viewModel.password) },
                onValueChange = { viewModel.password = it },
                leadingIcon = Icons.Default.Lock
            )
            Spacer(modifier = Modifier.height(24.dp))
            InputField(
                label = "Repeat password",
                value = remember { mutableStateOf(viewModel.repeatPassword) },
                onValueChange = { viewModel.repeatPassword = it },
                leadingIcon = Icons.Default.Lock
            )
            Spacer(modifier = Modifier.height(24.dp))

            val isButtonEnabled = viewModel.email.isNotEmpty() &&
                    viewModel.password.isNotEmpty() &&
                    viewModel.repeatPassword.isNotEmpty()

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loading.value = true
                    if (viewModel.password != viewModel.repeatPassword) {
                        Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show()
                        loading.value = false
                    } else {
                        coroutineScope.launch {
                            val signupRequest = SignUpRequest(
                                email = viewModel.email,
                                password = viewModel.password
                            )
                            try {
                                val response = RetrofitInstance.api.signup(signupRequest)
                                val status = response.status
                                val message = response.message

                                onSignUpSuccess(status, message)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Signup Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                                loading.value = false
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF2C55)
                ),
                enabled = isButtonEnabled && !loading.value
            ) {
                if (loading.value) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp) // Adjust size if needed
                    )
                } else {
                    Text(text = "Continue")
                }
            }
        }
    }
}