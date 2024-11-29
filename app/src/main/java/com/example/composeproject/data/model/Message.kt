package com.example.composeproject.data.model

import java.time.LocalDateTime

// A single message in a conversation
data class Message(
    val senderId: String,
    val owner_name: String,
    val dog_name: String,
    val dog_image: String,
    val content: String,
    val dateTime: LocalDateTime
)