package com.example.careermate_app.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.careermate_app.R
import com.example.careermate_app.models.RegisterRequest
import com.example.careermate_app.models.RegisterResponse
import com.example.careermate_app.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity: AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameField = findViewById<EditText>(R.id.nameField)
        val emailField = findViewById<EditText>(R.id.emailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {

            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                registerUser(name, email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        // Use 'username' to match backend
        val request = RegisterRequest(username = name, email = email, password = password)

        val call = ApiClient.apiService.registerUser(request)
        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if(response.isSuccessful){
                    val registerResponse = response.body()
                    Toast.makeText(this@RegisterActivity, "Registered Successfully!", Toast.LENGTH_SHORT).show()
                    Log.d("API_REGISTER", "Message: ${registerResponse?.message}")

                    // Redirect to LoginActivity
                    val intent = Intent(this@RegisterActivity, LogInActivity::class.java)
                    startActivity(intent)
                    finish() // close RegisterActivity so back button won't return here

                } else {
                    Toast.makeText(this@RegisterActivity, "Registration Failed", Toast.LENGTH_SHORT).show()
                    Log.e("API_REGISTER", "Error code: ${response.code()} Body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                Log.e("API_REGISTER_ERROR", t.toString())
            }
        })
    }
}