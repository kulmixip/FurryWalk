package com.example.composeproject.data.model

// A conversation between two users
data class Conversation(
    val conversationId: String,
    val userId: String,
    val dogId: String,
    val user2Id: String, // Who the conversation is with
    val user2IdImage: String, // Image of who the conversation is with
    val messages: MutableList<Message> // All the messages between
)

fun addMessages() {

}
