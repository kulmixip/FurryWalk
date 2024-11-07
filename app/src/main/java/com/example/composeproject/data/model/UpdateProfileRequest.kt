package com.example.composeproject.data.model

data class UpdateProfileRequest(
    val action: String = "updateProfile",
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val location: String
)
