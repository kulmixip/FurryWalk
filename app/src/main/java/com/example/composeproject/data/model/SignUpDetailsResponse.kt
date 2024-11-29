package com.example.composeproject.data.model

data class SignUpDetailsResponse(
    val status: Int,
    val message: String,
    val profile: Profile,
    val dogs: List<Dog>,
    val conversations: List<Conversation>
)
