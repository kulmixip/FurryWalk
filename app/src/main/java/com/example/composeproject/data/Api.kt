package com.example.composeproject.data

import com.example.composeproject.data.model.LoginRequest
import com.example.composeproject.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST(".")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    companion object {
        const val BASE_URL = "https://biljard.catchmedia.no/api2/"
    }
}