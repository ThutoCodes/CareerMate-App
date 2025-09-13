package com.example.careermate_app.models

//What the server sends back after login
data class LoginResponse(

    val token: String,  //JWT token
    val userId: String,
    val message: String

)

