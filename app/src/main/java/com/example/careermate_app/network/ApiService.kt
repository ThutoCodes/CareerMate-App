package com.example.careermate_app.network

import com.example.careermate_app.models.LoginRequest
import com.example.careermate_app.models.LoginResponse
import com.example.careermate_app.models.RegisterRequest
import com.example.careermate_app.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

        // LOGIN ENDPOINT
        @POST("users/login")
        fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

        // REGISTER ENDPOINT
        @POST("users/register")
        fun registerUser(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}