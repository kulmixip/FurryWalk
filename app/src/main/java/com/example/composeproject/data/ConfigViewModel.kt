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
import androidx.lifecycle.viewModelScope
import com.example.composeproject.RetrofitInstance
import com.example.composeproject.data.model.Conversation
import com.example.composeproject.data.model.Dog
import com.example.composeproject.data.model.Message
import retrofit2.http.Query
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
        private set

    var dogs = mutableStateListOf<Dog>()
        private set
    var selectedDog: Dog? = null

    fun updateDogs(dogList: List<Dog>) {
        dogs.clear() // Clear existing data
        dogs.addAll(dogList) // Add new data
    }

    fun updateConversations(conversationList: List<Conversation>) {
        conversations.clear()
        conversations.addAll(conversationList)
    }

    // Function to filter dogs based on search query
    fun searchDogs(query: String): List<Dog> {
        return dogs.filter { dog ->
            dog.name.contains(query, ignoreCase = true) ||
            dog.description.contains(query, ignoreCase = true) ||
            dog.breed.contains(query, ignoreCase = true) ||
            dog.color.contains(query, ignoreCase = true)
        }
    }
}

