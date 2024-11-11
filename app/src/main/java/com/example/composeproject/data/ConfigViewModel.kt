package com.example.composeproject.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.composeproject.data.model.Conversation

class ConfigViewModel: ViewModel() {
    var id by mutableIntStateOf(1)
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var email by mutableStateOf("")
    var image by mutableStateOf("")
    var phone by mutableIntStateOf(0)
    var age by mutableIntStateOf(0)
    var location by mutableStateOf("")
    var rating by mutableFloatStateOf(0F)
    var description by mutableStateOf("")
    var conversation = mutableStateListOf<Conversation>()
        private set
}