package com.example.composeproject.data.model

data class Profile(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: Int,
    val age: Int,
    val rating: Float,
    val location: String,
    val image: String
)
