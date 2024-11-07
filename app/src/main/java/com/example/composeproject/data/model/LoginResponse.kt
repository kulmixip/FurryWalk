package com.example.composeproject.data.model

data class LoginResponse(
    val status: Int,
    val message: String,
    //val profileId: Int
    val profile: Profile
)
