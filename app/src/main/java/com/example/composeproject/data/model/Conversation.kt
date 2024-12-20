package com.example.composeproject.data.model

// A conversation between two users
data class Conversation(
    val id: String,
    val owner_id: String,
    val dog_id: String,
    val user2Id: String, // Who the conversation is with
    var messages: List<Message> // All the messages between
)