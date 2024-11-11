package com.example.composeproject.data.model

// A conversation between two users
data class Conversation(
    val conversationId: String,
    val userId: String,
    val dogId: String,
    val messages: List<Message>
)