package com.example.composeproject.data.model

data class NewMessageRequest(
    val action: String = "newMessage",
    val userId: Int,
    val ownerId: Int,
    val dogId: Int,
    val message: String,
    val sentby: Int,
)
