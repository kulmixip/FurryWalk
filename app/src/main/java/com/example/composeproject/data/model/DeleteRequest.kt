package com.example.composeproject.data.model

data class DeleteRequest(
    val action: String = "deleteConversation",
    val id: Int
)
