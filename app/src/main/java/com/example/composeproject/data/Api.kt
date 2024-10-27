package com.example.composeproject.data

import com.example.composeproject.data.model.LoginRequest
import com.example.composeproject.data.model.LoginResponse
import com.example.composeproject.data.model.SignUpRequest
import com.example.composeproject.data.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST(".")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST(".")
    suspend fun signup(@Body request: SignUpRequest): SignUpResponse

    companion object {
        const val BASE_URL = "https://biljard.catchmedia.no/api2/"
    }
}