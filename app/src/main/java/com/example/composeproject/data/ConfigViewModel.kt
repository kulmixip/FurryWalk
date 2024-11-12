package com.example.composeproject.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.composeproject.data.model.Conversation
import com.example.composeproject.data.model.Message
import com.example.composeproject.data.model.addMessages
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class ConfigViewModel : ViewModel() {

    var id by mutableIntStateOf(1)
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var email by mutableStateOf("")
    var image by mutableStateOf("")
    var phone by mutableIntStateOf(0)
    var age by mutableIntStateOf(0)
    var location by mutableStateOf("")
    var rating by mutableFloatStateOf(0F)
    var description by mutableStateOf("")
    var conversations = mutableStateListOf<Conversation>()

    // Creates conversation and empty Message list
    fun addConversation(userId: String, user2Id: String, messages: MutableList<Message>, dogId: String, user2IdImage: String) {
        val newConversation = Conversation(
            conversationId = generateConversationId(),
            userId = userId,
            dogId = dogId,
            user2Id = user2Id,
            user2IdImage = user2IdImage, // Added user2IdImage field
            messages = messages
        )
        conversations.add(newConversation)
        addMessages()
    }

    // Add Message to list depending on conversationId
    fun addMessageToConversation(conversationId: String, message: Message) {
        val conversation = conversations.find { it.conversationId == conversationId }
        conversation?.messages?.add(message)
    }

    // Create unique conversationId
    private fun generateConversationId(): String {
        return "conv${conversations.size + 1}" // Example of generating a unique ID
    }

    // Constructor to add some filler data
    init {
        addConversation(
            userId = "user1",
            user2Id = "user2",
            user2IdImage = "https://example.com/user2image.jpg",  // Added image URL
            messages = mutableStateListOf(
                Message(messageId = "msg001", senderId = "user1", content = "Hey, how are you?", dateTime = LocalDateTime.now()),
                Message(messageId = "msg002", senderId = "user2", content = "I'm doing well, thanks! How about you?", dateTime = LocalDateTime.now())
            ),
            dogId = "dog001"
        )

        addConversation(
            userId = "user1",
            user2Id = "user3",
            user2IdImage = "https://example.com/user3image.jpg",  // Added image URL
            messages = mutableStateListOf(
                Message(messageId = "msg003", senderId = "user1", content = "Project is going great, how about you?", dateTime = LocalDateTime.now())
            ),
            dogId = "dog002"
        )
    }
}

