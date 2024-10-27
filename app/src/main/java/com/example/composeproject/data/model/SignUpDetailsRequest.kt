package com.example.composeproject.data.model

data class SignUpDetailsRequest(
    val action: String = "createUser",
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val age: String,
    val location: String
)
