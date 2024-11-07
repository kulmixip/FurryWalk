package com.example.composeproject.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ConfigViewModel: ViewModel() {
    var id by mutableIntStateOf(1)
    var firstName by mutableStateOf("Dummy")
    var lastName by mutableStateOf("User")
    var email by mutableStateOf("dummy@user.no")
    var image by mutableStateOf("https://biljard.catchmedia.no/files/furrywalk/images.jpg")
    var phone by mutableIntStateOf(97764756)
    var age by mutableIntStateOf(23)
    var location by mutableStateOf("Porsgrunn")
    var rating by mutableFloatStateOf(4.5F)
    var description by mutableStateOf("Dette er en beskrivelse")
}