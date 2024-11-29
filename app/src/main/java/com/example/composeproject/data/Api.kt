package com.example.composeproject.data

import com.example.composeproject.data.model.DeleteRequest
import com.example.composeproject.data.model.DeleteResponse
import com.example.composeproject.data.model.LoginRequest
import com.example.composeproject.data.model.LoginResponse
import com.example.composeproject.data.model.NewMessageRequest
import com.example.composeproject.data.model.NewMessageResponse
import com.example.composeproject.data.model.SignUpDetailsRequest
import com.example.composeproject.data.model.SignUpDetailsResponse
import com.example.composeproject.data.model.SignUpRequest
import com.example.composeproject.data.model.SignUpResponse
import com.example.composeproject.data.model.UpdateProfileRequest
import com.example.composeproject.data.model.UpdateProfileResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST(".")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(".")
    suspend fun signup(@Body request: SignUpRequest): SignUpResponse

    @POST(".")
    suspend fun signupDetails(@Body request: SignUpDetailsRequest): SignUpDetailsResponse

    @POST(".")
    suspend fun sendMessage(@Body request: NewMessageRequest): NewMessageResponse

    @POST(".")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): UpdateProfileResponse

    @POST(".")
    suspend fun deleteConversation(@Body request: DeleteRequest): DeleteResponse

    companion object {
        const val BASE_URL = "https://biljard.catchmedia.no/api2/"
    }
}