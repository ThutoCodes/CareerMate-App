package com.example.careermate_app.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.careermate_app.R
import com.example.careermate_app.prefs.SessionManager

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Initialize views
        val logo = findViewById<ImageView>(R.id.logo)
        val appName = findViewById<TextView>(R.id.appName)
        val tagline = findViewById<TextView>(R.id.tagline)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        // --- Logo Animations ---
        val logoFade = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f).apply { duration = 800 }
        val logoSlide = ObjectAnimator.ofFloat(logo, "translationY", -50f, 0f).apply { duration = 800 }

        // --- App Name Animations ---
        val appNameFade = ObjectAnimator.ofFloat(appName, "alpha", 0f, 1f).apply { duration = 600 }
        val appNameSlide = ObjectAnimator.ofFloat(appName, "translationY", 50f, 0f).apply { duration = 600 }

        // --- Tagline Animations ---
        val taglineFade = ObjectAnimator.ofFloat(tagline, "alpha", 0f, 1f).apply { duration = 600 }
        val taglineSlide = ObjectAnimator.ofFloat(tagline, "translationY", 50f, 0f).apply { duration = 600 }

        // --- ProgressBar Animation (fade-in only) ---
        val progressFade = ObjectAnimator.ofFloat(progressBar, "alpha", 0f, 1f).apply { duration = 400 }

        // --- AnimatorSet to sequence animations ---
        AnimatorSet().apply {
            play(logoFade).with(logoSlide)
            play(appNameFade).with(appNameSlide).after(logoFade)
            play(taglineFade).with(taglineSlide).after(appNameFade)
            play(progressFade).after(taglineFade)
            start()
        }

        // --- Navigate after 3.5s ---
        Handler(Looper.getMainLooper()).postDelayed({
            val sessionManager = SessionManager(this)

            if (sessionManager.fetchAuthToken() != null) {
                // User is logged in → go to MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            } else {
                // User not logged in → go to RegisterActivity
                startActivity(Intent(this, RegisterActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }

            finish()
        }, 3500)
    }
}