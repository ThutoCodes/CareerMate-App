package com.example.careermate_app

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
        val logoFade = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f).apply { duration = 1000 }
        val logoSlide = ObjectAnimator.ofFloat(logo, "translationY", -50f, 0f).apply { duration = 1000 }

        // --- App Name Animations ---
        val appNameFade = ObjectAnimator.ofFloat(appName, "alpha", 0f, 1f).apply { duration = 800 }
        val appNameSlide = ObjectAnimator.ofFloat(appName, "translationY", 50f, 0f).apply { duration = 800 }

        // --- Tagline Animations ---
        val taglineFade = ObjectAnimator.ofFloat(tagline, "alpha", 0f, 1f).apply { duration = 800 }
        val taglineSlide = ObjectAnimator.ofFloat(tagline, "translationY", 50f, 0f).apply { duration = 800 }

        // --- ProgressBar Animation ---
        val progressFade = ObjectAnimator.ofFloat(progressBar, "alpha", 0f, 1f).apply { duration = 500 }

        // --- AnimatorSet to sequence animations ---
        AnimatorSet().apply {
            play(logoFade).with(logoSlide)                 // Logo fades and slides together
            play(appNameFade).with(appNameSlide)           // App name fades and slides together
            play(taglineFade).with(taglineSlide)           // Tagline fades and slides together

            // Sequence everything: Logo -> App Name -> Tagline -> ProgressBar
            play(appNameFade).after(logoFade)
            play(taglineFade).after(appNameFade)
            play(progressFade).after(taglineFade)

            start()
        }

        // --- Navigate to MainActivity after 3 seconds ---
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000)
    }
}