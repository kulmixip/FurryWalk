package com.example.composeproject.data.model

data class SignUpRequest(
    val action: String = "signup",
    val email: String,
    val password: String
)
