package com.example.composeproject.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ConfigViewModel: ViewModel() {
    var id by mutableIntStateOf(0)
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var image by mutableStateOf("")
}