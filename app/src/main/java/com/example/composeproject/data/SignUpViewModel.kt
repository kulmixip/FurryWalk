package com.example.composeproject.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SignUpViewModel: ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var repeatPassword by mutableStateOf("")
}