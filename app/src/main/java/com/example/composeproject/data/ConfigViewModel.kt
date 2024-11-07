package com.example.composeproject.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ConfigViewModel: ViewModel() {
    val id by mutableStateOf("")
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    val image by mutableStateOf("")
}