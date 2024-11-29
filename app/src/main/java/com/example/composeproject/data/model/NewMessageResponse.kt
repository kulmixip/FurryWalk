package com.example.composeproject.data.model

data class NewMessageResponse(
    val status: Int,
    val message: String,
    val owner_name: String,
    val dog_name: String,
    val dog_image: String,
    val conversation_id: Int
)
