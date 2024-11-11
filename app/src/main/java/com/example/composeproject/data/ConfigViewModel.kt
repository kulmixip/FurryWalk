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

    // Mutable state list holding all conversations
    val _conversations = mutableStateListOf<Conversation>()
    val conversations: List<Conversation> get() = _conversations

    // Function to add a new conversation
    fun addConversation(userId: String, user2Id: String, messages: List<Message>, dogId: String) {
        val newConversation = Conversation(
            conversationId = generateConversationId(),
            userId = userId,
            dogId = dogId,
            user2Id = user2Id,
            messages = messages
        )
        _conversations.add(newConversation)
    }

    // Function to add a new message to an existing conversation
    fun addMessageToConversation(conversationId: String, message: Message) {
        // Find the conversation by its ID and add the new message
        val conversation = _conversations.find { it.conversationId == conversationId }
        conversation?.let {
            it.messages.toMutableList().add(message)
        }
    }

    // Helper function to generate a conversation ID (this could be more complex in a real app)
    private fun generateConversationId(): String {
        return "conv${_conversations.size + 1}" // Example of generating a unique ID
    }

    // Example of adding some predefined conversations with messages
    init {
        addConversation(
            userId = "user1",
            user2Id = "user2",
            messages = listOf(
                Message(messageId = "msg001", senderId = "user1", content = "Hey, how are you?", dateTime = LocalDateTime.now()),
                Message(messageId = "msg002", senderId = "user2", content = "I'm doing well, thanks! How about you?", dateTime = LocalDateTime.now())
            ),
            dogId = "dog001"
        )

        addConversation(
            userId = "user1",
            user2Id = "user3",
            messages = listOf(
                Message(messageId = "msg003", senderId = "user1", content = "Project is going great, how about you?", dateTime = LocalDateTime.now())
            ),
            dogId = "dog002"
        )
    }
}
