package com.example.careermate_app.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.careermate_app.R
import com.example.careermate_app.models.RegisterRequest
import com.example.careermate_app.models.RegisterResponse
import com.example.careermate_app.network.ApiClient
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity: AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameField = findViewById<TextInputEditText>(R.id.nameField)
        val emailField = findViewById<TextInputEditText>(R.id.emailField)
        val passwordField = findViewById<TextInputEditText>(R.id.passwordField)
        val nameLayout = findViewById<TextInputLayout>(R.id.nameLayout)
        val emailLayout = findViewById<TextInputLayout>(R.id.emailLayout)
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordLayout)
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)
        val signInLink = findViewById<TextView>(R.id.signInLink)
        val backButton = findViewById<ImageButton>(R.id.backButton)

        // Set up click listeners
        backButton.setOnClickListener {
            onBackPressed()
        }

        registerButton.setOnClickListener {
            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            // Input validation
            var isValid = true

            if (name.isEmpty()) {
                nameLayout.error = "Name is required"
                isValid = false
            } else {
                nameLayout.error = null
            }

            if (email.isEmpty()) {
                emailLayout.error = "Email is required"
                isValid = false
            } else {
                emailLayout.error = null
            }

            if (password.isEmpty()) {
                passwordLayout.error = "Password is required"
                isValid = false
            } else if (password.length < 8) {
                passwordLayout.error = "Password must be at least 8 characters"
                isValid = false
            } else {
                passwordLayout.error = null
            }

            if (isValid) {
                registerUser(name, email, password)
            }
        }

        // Navigate to login
        signInLink.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }
    }

    private fun registerUser(name: String, email: String, password: String) {
        // Use 'username' to match backend
        val request = RegisterRequest(username = name, email = email, password = password)

        // Show loading state
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)
        registerButton.text = "Creating account..."
        registerButton.isEnabled = false

        val call = ApiClient.apiService.registerUser(request)
        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                // Reset button state
                registerButton.text = "Create Account"
                registerButton.isEnabled = true

                if (response.isSuccessful) {
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
                // Reset button state
                registerButton.text = "Create Account"
                registerButton.isEnabled = true

                Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                Log.e("API_REGISTER_ERROR", t.toString())
            }
        })
    }
}