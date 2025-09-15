package com.example.careermate_app.models

data class ProfileResponse(val user: UserDto)
data class UserDto(val _id: String?, val username: String?, val email: String?)
