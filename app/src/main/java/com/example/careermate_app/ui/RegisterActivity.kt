package com.example.careermate_app.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.careermate_app.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
// 1. Import Firebase libraries
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterActivity: AppCompatActivity() {

    // 2. Declare Firebase Auth and Firestore instances
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 3. Initialize Firebase instances
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()

        val nameField = findViewById<TextInputEditText>(R.id.nameField)
        val emailField = findViewById<TextInputEditText>(R.id.emailField)
        val passwordField = findViewById<TextInputEditText>(R.id.passwordField)
        val nameLayout = findViewById<TextInputLayout>(R.id.nameLayout)
        val emailLayout = findViewById<TextInputLayout>(R.id.emailLayout)
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordLayout)
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)
        val signInLink = findViewById<TextView>(R.id.signInLink)
        val backButton = findViewById<ImageButton>(R.id.backButton)

        backButton.setOnClickListener {
            onBackPressed()
        }

        registerButton.setOnClickListener {
            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            var isValid = true
            if (name.isEmpty()) {
                nameLayout.error = "Name is required"
                isValid = false
            } else { nameLayout.error = null }

            if (email.isEmpty()) {
                emailLayout.error = "Email is required"
                isValid = false
            } else { emailLayout.error = null }

            if (password.isEmpty()) {
                passwordLayout.error = "Password is required"
                isValid = false
            } else if (password.length < 8) {
                passwordLayout.error = "Password must be at least 8 characters"
                isValid = false
            } else { passwordLayout.error = null }

            if (isValid) {
                // Call the new Firebase registration function
                registerUserWithFirebase(name, email, password)
            }
        }

        signInLink.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }
    }

    // 4. *** NEW FIREBASE REGISTRATION LOGIC ***
    private fun registerUserWithFirebase(name: String, email: String, password: String) {
        val registerButton = findViewById<MaterialButton>(R.id.registerButton)
        registerButton.text = "Creating account..."
        registerButton.isEnabled = false

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("FIREBASE_AUTH", "createUserWithEmail:success")
                    // Authentication was successful, now save the username to Firestore.
                    val firebaseUser = auth.currentUser
                    val userProfile = hashMapOf(
                        "username" to name,
                        "email" to email
                    )

                    // Use the user's unique ID (uid) from Firebase Auth as the document ID in Firestore
                    db.collection("users").document(firebaseUser!!.uid)
                        .set(userProfile)
                        .addOnSuccessListener {
                            Log.d("FIRESTORE", "User profile created for ${firebaseUser.uid}")
                            Toast.makeText(this, "Registered Successfully!", Toast.LENGTH_SHORT).show()

                            // Redirect to Dashboard
                            val intent = Intent(this, DashboardActivity::class.java)
                            startActivity(intent)
                            finishAffinity() // Closes all previous activities
                        }
                        .addOnFailureListener { e ->
                            Log.w("FIRESTORE", "Error adding document", e)
                            registerButton.text = "Create Account"
                            registerButton.isEnabled = true
                            Toast.makeText(this, "Error saving user profile.", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FIREBASE_AUTH", "createUserWithEmail:failure", task.exception)
                    registerButton.text = "Create Account"
                    registerButton.isEnabled = true
                    Toast.makeText(baseContext, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
