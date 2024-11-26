package com.example.composeproject.data.model

data class Dog(
    val ownerId: Int,
    val name: String,
    val description: String,
    val breed: String,
    val age: Int,
    val weight: Float,
    val sex: String,
    val color: String,
    val activity: String,
    val image: String,
    val available: Boolean,
    val messages: String
)
