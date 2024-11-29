package com.example.composeproject.data.model

data class NewMessageRequest(
    val action: String = "newMessage",
    val userId: String,
    val ownerId: String,
    val dogId: String,
    val message: String,
    val sentby: String,
)
