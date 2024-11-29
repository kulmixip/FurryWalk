package com.example.composeproject.data.model

data class SignUpRequest(
    val action: String = "signup",
    val username: String,
    val email: String,
    val password: String
)
