package com.example.careermate_app.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.careermate_app.R
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.careermate_app.models.LoginRequest
import com.example.careermate_app.models.LoginResponse
import com.example.careermate_app.network.ApiClient
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogInActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Grab references to UI elements
        val emailField = findViewById<TextInputEditText>(R.id.emailField)
        val passwordField = findViewById<TextInputEditText>(R.id.passwordField)
        val emailLayout = findViewById<TextInputLayout>(R.id.emailLayout)
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordLayout)
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        val signUpLink = findViewById<TextView>(R.id.signUpLink)

        // Set up click listeners
        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            // Input validation
            var isValid = true

            if (email.isEmpty()) {
                emailLayout.error = "Email is required"
                isValid = false
            } else {
                emailLayout.error = null
            }

            if (password.isEmpty()) {
                passwordLayout.error = "Password is required"
                isValid = false
            } else {
                passwordLayout.error = null
            }

            if (isValid) {
                loginUser(email, password)
            }
        }

        // Navigate to registration
        signUpLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        val request = LoginRequest(email, password)

        // Show loading state
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        loginButton.text = "Logging in..."
        loginButton.isEnabled = false

        // Call API
        val call = ApiClient.apiService.loginUser(request)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                // Reset button state
                loginButton.text = "Log In"
                loginButton.isEnabled = true

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Toast.makeText(this@LogInActivity, "Welcome!", Toast.LENGTH_SHORT).show()
                    Log.d("API LOGIN", "Message: ${loginResponse?.message}")

                    // Navigate to MainActivity
                    val intent = Intent(this@LogInActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LogInActivity, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show()
                    Log.e("API_LOGIN", "Error code: ${response.code()} Body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Reset button state
                loginButton.text = "Log In"
                loginButton.isEnabled = true

                Toast.makeText(this@LogInActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                Log.e("API_LOGIN_ERROR", t.toString())
            }
        })
    }
}