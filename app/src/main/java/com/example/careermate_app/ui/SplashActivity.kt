package com.example.careermate_app.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
// --- FIXED THIS SECTION ---
import android.os.Looper
import android.widget.ImageView
// --------------------------
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.careermate_app.R
// 1. Import the Firebase Auth library
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    // 2. Declare Firebase Auth instance
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 3. Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize views
        val logo = findViewById<ImageView>(R.id.logo)
        val appName = findViewById<TextView>(R.id.appName)
        val tagline = findViewById<TextView>(R.id.tagline)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // --- Animations (This part is correct and remains unchanged) ---
        val logoFade = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f).apply { duration = 800 }
        val logoSlide = ObjectAnimator.ofFloat(logo, "translationY", -50f, 0f).apply { duration = 800 }
        val appNameFade = ObjectAnimator.ofFloat(appName, "alpha", 0f, 1f).apply { duration = 600 }
        val appNameSlide = ObjectAnimator.ofFloat(appName, "translationY", 50f, 0f).apply { duration = 600 }
        val taglineFade = ObjectAnimator.ofFloat(tagline, "alpha", 0f, 1f).apply { duration = 600 }
        val taglineSlide = ObjectAnimator.ofFloat(tagline, "translationY", 50f, 0f).apply { duration = 600 }
        val progressFade = ObjectAnimator.ofFloat(progressBar, "alpha", 0f, 1f).apply { duration = 400 }

        AnimatorSet().apply {
            play(logoFade).with(logoSlide)
            play(appNameFade).with(appNameSlide).after(logoFade)
            play(taglineFade).with(taglineSlide).after(appNameFade)
            play(progressFade).after(taglineFade)
            start()
        }

        // 4. *** NEW NAVIGATION LOGIC ***
        Handler(Looper.getMainLooper()).postDelayed({
            // Check if a user is currently signed in to Firebase
            if (auth.currentUser != null) {
                // User is logged in → go to DashboardActivity
                startActivity(Intent(this, DashboardActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            } else {
                // User not logged in → go to LogInActivity
                startActivity(Intent(this, LogInActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            finish() // Finish SplashActivity so the user can't go back to it
        }, 3500)
    }
}
