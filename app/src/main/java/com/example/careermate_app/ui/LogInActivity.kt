package com.example.careermate_app.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
// --- FIXED THIS SECTION ---
import android.widget.TextView
import android.widget.Toast
// --------------------------
import androidx.appcompat.app.AppCompatActivity
import com.example.careermate_app.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
// 1. Import Firebase libraries
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LogInActivity : AppCompatActivity() {

    // 2. Declare Firebase Auth instance
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 3. Initialize Firebase Auth
        auth = Firebase.auth

        // --- Auto-Login Check ---
        // If a user is already logged in, go straight to the Dashboard
        if (auth.currentUser != null) {
            Log.d("FIREBASE_AUTH", "User already logged in, redirecting to Dashboard.")
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
            finish() // Prevents user from coming back to the login screen
            return // Stop the rest of the onCreate method
        }

        val emailField = findViewById<TextInputEditText>(R.id.emailField)
        val passwordField = findViewById<TextInputEditText>(R.id.passwordField)
        val emailLayout = findViewById<TextInputLayout>(R.id.emailLayout)
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordLayout)
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        val signUpLink = findViewById<TextView>(R.id.signUpLink)

        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            var isValid = true
            if (email.isEmpty()) {
                emailLayout.error = "Email is required"
                isValid = false
            } else { emailLayout.error = null }

            if (password.isEmpty()) {
                passwordLayout.error = "Password is required"
                isValid = false
            } else { passwordLayout.error = null }

            if (isValid) {
                // Call the new Firebase login function
                loginUserWithFirebase(email, password)
            }
        }

        signUpLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // 4. *** NEW FIREBASE LOGIN LOGIC ***
    private fun loginUserWithFirebase(email: String, password: String) {
        val loginButton = findViewById<MaterialButton>(R.id.loginButton)
        loginButton.text = "Logging in..."
        loginButton.isEnabled = false

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                loginButton.text = "Log In"
                loginButton.isEnabled = true

                if (task.isSuccessful) {
                    Log.d("FIREBASE_AUTH", "signInWithEmail:success")
                    Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()

                    // Navigate to DashboardActivity
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finishAffinity() // Closes all previous activities
                } else {
                    Log.w("FIREBASE_AUTH", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed: ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}
