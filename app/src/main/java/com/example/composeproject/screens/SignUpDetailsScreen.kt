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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.composeproject.components.NumberField
import com.example.composeproject.data.SignUpViewModel
import com.example.composeproject.data.model.SignUpDetailsRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@Composable
fun SignUpDetailsScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel,
    onSignUpDetailsSuccess: (Int, String) -> Unit
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    val coroutineScope = rememberCoroutineScope()
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color)
                .statusBarsPadding()
        ) {
            IconButton(
                onClick = {navController.navigate("signup")},
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back to signup screen",
                    tint = Color.White
                )
            }

            Text(
                text = "Enter Your Details",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(vertical = 14.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(
                label = "First name",
                value = remember { mutableStateOf(viewModel.firstName) },
                onValueChange = {viewModel.firstName = it},
                leadingIcon = Icons.Default.AccountCircle
            )
            Spacer(modifier = Modifier.height(24.dp))
            InputField(
                label = "Last name",
                value = remember { mutableStateOf(viewModel.lastName) },
                onValueChange = {viewModel.lastName = it},
                leadingIcon = Icons.Default.AccountCircle
            )
            Spacer(modifier = Modifier.height(24.dp))
            NumberField(
                label = "Phone number",
                value = remember { mutableStateOf(viewModel.phone) },
                onValueChange = {viewModel.phone = it},
                leadingIcon = Icons.Default.Phone
            )
            Spacer(modifier = Modifier.height(24.dp))
            NumberField(
                label = "Age",
                value = remember { mutableStateOf(viewModel.age) },
                onValueChange = {viewModel.age = it},
                leadingIcon = Icons.Default.DateRange
            )
            Spacer(modifier = Modifier.height(24.dp))
            InputField(
                label = "Location",
                value = remember { mutableStateOf(viewModel.location) },
                onValueChange = {viewModel.location = it},
                leadingIcon = Icons.Default.LocationOn
            )
            Spacer(modifier = Modifier.height(24.dp))

            val isButtonEnabled = viewModel.firstName.isNotEmpty() &&
                    viewModel.lastName.isNotEmpty() &&
                    viewModel.phone.isNotEmpty() &&
                    viewModel.age.isNotEmpty() &&
                    viewModel.location.isNotEmpty()

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loading.value = true
                    coroutineScope.launch {
                        val request = SignUpDetailsRequest(
                            firstName = viewModel.firstName,
                            lastName = viewModel.lastName,
                            email = viewModel.email,
                            password = viewModel.password,
                            phone = viewModel.phone,
                            age = viewModel.age,
                            location = viewModel.location
                        )
                        try {
                            val response = RetrofitInstance.api.signupDetails(request)
                            val status = response.status
                            val message = response.message

                            onSignUpDetailsSuccess(status, message)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Signup details failed", Toast.LENGTH_SHORT).show()
                        } finally {
                            loading.value = false
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
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(text = "Continue")
                }
            }
        }
    }
}