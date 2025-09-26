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
import com.example.careermate_app.prefs.SessionManager
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

        // Click login button
        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

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
                loginUser(email, password, loginButton)
            }
        }

        // Navigate to RegisterActivity
        signUpLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String, loginButton: MaterialButton) {
        val request = LoginRequest(email, password)

        // Show loading state
        loginButton.text = "Logging in..."
        loginButton.isEnabled = false

        ApiClient.apiService.loginUser(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                // Reset button state
                loginButton.text = "Log In"
                loginButton.isEnabled = true

                if (response.isSuccessful) {
                    val loginResponse = response.body()

                    // ✅ Save session with userId and token
                    loginResponse?.let {
                        val sessionManager = SessionManager(this@LogInActivity)
                        sessionManager.saveUser(it.userId, it.token)
                    }

                    Toast.makeText(this@LogInActivity, "Welcome!", Toast.LENGTH_SHORT).show()

                    // Navigate to MainActivity
                    val intent = Intent(this@LogInActivity, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(
                        this@LogInActivity,
                        "Invalid credentials. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(
                        "API_LOGIN",
                        "Error code: ${response.code()}, Body: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginButton.text = "Log In"
                loginButton.isEnabled = true

                Toast.makeText(
                    this@LogInActivity,
                    "Network error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("API_LOGIN_ERROR", "Failure: ${t.message}")
            }
        })
    }
}