package com.example.careermate_app.models

// What the server sends back after registration
class RegisterResponse (

    val success: Boolean,
    val userId: String?,
    val message: String
)