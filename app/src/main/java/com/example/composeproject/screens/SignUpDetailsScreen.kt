package com.example.composeproject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.composeproject.data.SignUpViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SignUpDetailsScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel
) {
    val systemUiController = rememberSystemUiController()
    val color = Color(0xFFFF2C55)

    LaunchedEffect(Unit) {
        systemUiController.setStatusBarColor(
            color = color,
            darkIcons = false
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
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
    }
}